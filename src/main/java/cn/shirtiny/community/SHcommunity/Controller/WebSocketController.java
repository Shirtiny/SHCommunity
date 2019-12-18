package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ChatHistoryDTO;
import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatHistory;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import cn.shirtiny.community.SHcommunity.Service.IchatHistoryService;
import cn.shirtiny.community.SHcommunity.Service.IchatMessageService;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import cn.shirtiny.community.SHcommunity.Utils.Encryption.ShaEncryptor;
import com.baidu.fsg.uid.service.UidGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class WebSocketController {

    @Autowired
    private IchatHistoryService chatHistoryService;
    @Autowired
    private IchatMessageService chatMessageService;
    @Autowired
    private IuserService userService;
    @Autowired
    private ShaEncryptor shaEncryptor;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private UidGenerateService uidGenService;


    //接收消息的接口路径，聊天室频道
    @MessageMapping("/sendToRoom")
    //发送到 /room/chat 频道
    @SendTo("/room/chat")
    public ChatMessage retString(Message message) {
        //聊天室记录的固定id
        String historyId = "0";
        //接收发送来的消息 然后转为消息对象
        ChatMessage chatMessage = chatMessageService.parseMessageToChatMessage(message,"0");
        //发送者id
        Long senderId = chatMessage.getSenderId();
        if (senderId != null) {
            //将消息存入数据库
            chatMessageService.addChatMessageByChatHistoryId("0",chatMessage);
        }
        //从数据库中查询此聊天室的消息，广播给该频道
        return chatMessage;
    }

    //创建聊天室的聊天记录表
    @PostMapping(value = "/shApi/createChatRoomTable")
    @ResponseBody
    public ShResultDTO<String, Object> toCreateChatRoomTable() {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setChatHistoryId("0");
        chatHistory.setChatHistoryName("shChatRoom");
        chatHistory.setChannel("/room/chat");
        chatHistory.setSystemSign(true);
        boolean flag = chatHistoryService.addOneChatHistory(chatHistory);

        return flag ? new ShResultDTO<>(200, "聊天室创建成功") : new ShResultDTO<>(501, "聊天室创建失败，该聊天室已存在");
    }

    //查询聊天室的聊天记录 返回倒数20个
    @GetMapping(value = "/shApi/listChatRoomMessages")
    @ResponseBody
    public ShResultDTO<String, Object> tolistChatRoomMessages() {

        List<ChatMessageDTO> chatMessageDTOs = chatMessageService.selectMessagesByHistoryId("0",20);
        Map<String, Object> map = new HashMap<>();
        map.put("historyMessages", chatMessageDTOs);

        return new ShResultDTO<>(200, "聊天室记录查询完成", map, null);
    }

    //清空聊天室的聊天记录
    @GetMapping(value = "/shApi/cleanChatRoomMessages")
    @ResponseBody
    public ShResultDTO<String, Object> toCleanChatRoomMessages() {

        chatMessageService.deleteMessagesByhistoryId(0L);

        return new ShResultDTO<>(200, "聊天室记录已清空");
    }

    //生成一个聊天室游客id 已不用 由js生成
    @GetMapping(value = "/shApi/newChatRoomSenderId")
    @ResponseBody
    public ShResultDTO<String, Object> toNewChatRoomTouristId(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Random random = new Random();
        int touristIDNumber = random.nextInt(999);
        //转成字符串，加上时间戳
        String touristID = "" + System.currentTimeMillis() + touristIDNumber;
        System.out.println("生成的游客id为：" + touristID);
        map.put("touristID", touristID);
        //存入session
        request.getSession().setAttribute("touristID", touristID);
        return new ShResultDTO<>(200, "生成一个游客id", map, null);
    }

    //一对一聊天 message需要携带消息内容、接收者的id
    @MessageMapping("/sendToUser")
    public void sendToUser(Message message) {
        //mysql UNION
        //接收发送来的消息 然后转为消息对象
        ChatMessage chatMessage = chatMessageService.parseMessageToChatMessage(message);
        //设置消息id
        long uid = uidGenService.generateUid();
        chatMessage.setChatMessageId(uid);
        //将消息存入数据库
        boolean isMessageAdded = chatMessageService.addChatMessage(chatMessage);
        //创建历史记录 已存在则不会创建
        chatHistoryService.addOneChatHistoryBy2Id(chatMessage.getSender().getUserId(), chatMessage.getRecipientId());
        //消息数自增,更新消息记录的修改时间 发送消息 只有消息入库成功才会发送
        if (isMessageAdded) {
            chatHistoryService.incrMessageNumAndgmtModified(chatMessage.getChatHistoryId());
            //会自动在频道路径前加上/user/'historyId' 比如此频道会被拼接为/user/historyId/121chat
            messagingTemplate.convertAndSendToUser(chatMessage.getChatHistoryId(), "/121chat", chatMessage);
        }
    }

    //为客户端返回应该订阅的1对1频道
    @GetMapping(value = "/shApi/ReSubscribeChannel")
    @ResponseBody
    public ShResultDTO<String, Object> ReSubscribeChannel(Long senderId, Long recipientId) {
        String historyId = chatHistoryService.createHistoryId(senderId, recipientId);
        if (historyId == null) {
            return new ShResultDTO<>(400, "参数不合法");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("subscribeChannel", historyId);
        return new ShResultDTO<>(200, "已返回应该订阅的频道", data, null);
    }

    //查询某个用户的全部 系统/非系统 消息记录及其内容
    @GetMapping("/shApi/allHistoryMessage")
    @ResponseBody
    public ShResultDTO<String, Object> retAllHistoryMessage(@RequestParam(value = "userId", required = true) Long userId,
                                                            @RequestParam(value = "isSystem", required = false) Boolean isSystem) {
        if (userId == null) {
            return new ShResultDTO<>(400, "参数不合法");
        }
        List<ChatHistoryDTO> chatHistoryDTOS ;
        if (isSystem == null || !isSystem) {
            chatHistoryDTOS = chatHistoryService.selectAllchatHistoryMessageByUid(userId);
        } else {
            chatHistoryDTOS = chatHistoryService.selectAllsystemHistoryMessageByUid(userId);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("chatHistories", chatHistoryDTOS);
        return new ShResultDTO<>(200, "返回的是否是系统消息"+(isSystem!=null)+"，已返回对应的该用户的历史记录及其内容", data, null);
    }

    //用于通知某个用户 message需要携带消息内容、接收者的id
    @MessageMapping("/sendToOneUser")
    public void sendToOneUser(Message message) {
        ChatMessage chatMessage = chatMessageService.parseMessageToChatMessage(message);
        //收信人
        Long recipientId = chatMessage.getRecipientId();
        //标识为系统通知
        chatMessage.setSystems(true);
        messagingTemplate.convertAndSend("/uid/" + recipientId, chatMessage);
    }

    //把某个消息记录的所有消息更新为已读
    @GetMapping("/shApi/updateChatHistoryRead")
    @ResponseBody
    public ShResultDTO<String, Object> updateChatHistoryRead(String chatHistoryId) {
        chatMessageService.updateMessageReadByHistoryId(chatHistoryId, true);
        return new ShResultDTO<>(200, "已更新");
    }

    //返回某个用户的未读消息数
    @GetMapping("/shApi/userUnReadMessagesCount")
    @ResponseBody
    public ShResultDTO<String, Object> retUnReadMessagesCount(Long userId) {
        if (userId == null) {
            return new ShResultDTO<>(400, "参数不合法");
        }
        int unReadCount = chatHistoryService.selectUnReadMessagesCountByUid(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("unReadCount", unReadCount);
        return new ShResultDTO<>(200, "已经返回该用户的未读消息数", data, null);
    }
}

