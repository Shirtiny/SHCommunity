package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatHistory;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IchatHistoryService;
import cn.shirtiny.community.SHcommunity.Service.IchatMessageService;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import cn.shirtiny.community.SHcommunity.Utils.Encryption.ShaEncryptor;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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


    //接收消息的接口路径，聊天室频道
    @MessageMapping("/sendToRoom")
    //发送到 /room/chat 频道
    @SendTo("/room/chat")
    public ShResultDTO<String, Object> retString(@RequestBody ChatMessageDTO message) {
        //聊天室记录的固定id
        String historyId = "0";

        //从session获取用户信息
        UserDTO user = null;
//        user =(UserDTO)request.getSession().getAttribute("user");
        //发送者id
        Long senderId = null;
        if (user != null) {
            senderId = user.getUserId();
        } else {
            //为空则获取游客id，由前端手动调用后端接口，生成游客id
            senderId = message.getSenderId();
        }
        //接收者id
        //无
        //将消息存入数据库
        chatMessageService.addChatMessage(message.getChatMessageContent(), historyId, senderId, null);

        //从数据库中查询此聊天室的消息，广播给该频道
        return tolistChatRoomMessages();
    }

    //创建聊天室的聊天记录表
    @PostMapping(value = "/shApi/createChatRoomTable")
    @ResponseBody
    public ShResultDTO<String, Object> toCreateChatRoomTable() {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setChatHistoryId("0");
        chatHistory.setChatHistoryName("shChatRoom");
        chatHistory.setGmtCreated(System.currentTimeMillis());
        chatHistory.setGmtModified(chatHistory.getGmtCreated());
        boolean flag = chatHistoryService.addOneChatHistory(chatHistory);

        return flag ? new ShResultDTO<>(200, "聊天室创建成功") : new ShResultDTO<>(501, "聊天室创建失败，该聊天室已存在");
    }

    //查询聊天室的聊天记录
    @GetMapping(value = "/shApi/listChatRoomMessages")
    @ResponseBody
    public ShResultDTO<String, Object> tolistChatRoomMessages() {

        List<ChatMessageDTO> chatMessageDTOs = chatMessageService.selectMessagesByHistoryId(0L);
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

    //生成一个聊天室游客id
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
    public void sendToOneUser(Message message) {
        //mysql UNION
        //接收发送来的消息 然后转为消息对象
        String payload = new String((byte[]) message.getPayload());
        ChatMessage chatMessage = JSONObject.parseObject(payload, ChatMessage.class);
        //取出消息头中session里的当前用户
        MessageHeaders headers = message.getHeaders();
        Map sessionAttributes = (Map) headers.get("simpSessionAttributes");
        //断言
        assert sessionAttributes != null;
        //从websocketSession取出发送者
        UserDTO sender = (UserDTO)sessionAttributes.get("user");
        chatMessage.setSender(sender);
        //根据双方id得出历史记录id
        String historyId = chatHistoryService.createHistoryId(sender.getUserId(), chatMessage.getRecipientId());
        //会自动在频道路径前加上/user/'historyId' 比如此频道会被拼接为/user/historyId/121chat
        messagingTemplate.convertAndSendToUser(historyId, "/121chat", message);
    }

    //为客户端返回应该订阅的1对1频道
    @GetMapping(value = "/shApi/ReSubscribeChannel")
    @ResponseBody
    public ShResultDTO<String, Object> ReSubscribeChannel(Long senderId,Long recipientId){
        String historyId = chatHistoryService.createHistoryId(senderId,recipientId);
        if (historyId==null){
            return new ShResultDTO<>(400,"参数不合法");
        }
        Map<String,Object> data =new HashMap<>();
        data.put("subscribeChannel",historyId);
        return new ShResultDTO<>(200,"已返回应该订阅的频道",data,null);
    }
}

