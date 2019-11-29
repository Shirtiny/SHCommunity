package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Mapper.ChatMessageMapper;
import cn.shirtiny.community.SHcommunity.Model.ChatHistory;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import cn.shirtiny.community.SHcommunity.Service.IchatHistoryService;
import cn.shirtiny.community.SHcommunity.Service.IchatMessageService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.nio.cs.ext.MacArabic;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChatMessageServiceImpl implements IchatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private IchatHistoryService chatHistoryService;

    @Value("${CHATMESSAGE_MAX_LENGTH}")
    private Integer CHATMESSAGE_MAX_LENGTH;

    //增加一条消息
    @Override
    public boolean addChatMessage(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return false;
        }
        return addChatMessage(chatMessage.getChatMessageContent(), chatMessage.getSenderId(), chatMessage.getRecipientId());
    }

    //增加一条消息
    @Override
    public boolean addChatMessage(String messageContent, Long senderId, Long recipientId) {
        //检查消息内容是否为空
        if (messageContent == null || messageContent.trim().isEmpty()) {
            System.out.println("消息为空");
            return false;
        }
        //字符长度是否超限
        if (messageContent.length() > CHATMESSAGE_MAX_LENGTH) {
            System.out.println("消息内容长度超限");
            return false;
        }
        //数据库
        try {
            ChatMessage chatMessage = new ChatMessage();
            //保存此消息的记录id
            String historyId = chatHistoryService.createHistoryId(senderId, recipientId);
            chatMessage.setChatHistoryId(historyId);
            //创建时间
            chatMessage.setGmtCreated(System.currentTimeMillis());
            //消息内容
            chatMessage.setChatMessageContent(messageContent);
            //发送者id
            chatMessage.setSenderId(senderId);
            //接收者id
            chatMessage.setRecipientId(recipientId);
            //插入数据库
            chatMessageMapper.insert(chatMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //查询某个聊天记录的消息 ,比如查询聊天室的消息（chatHistoryId为0的消息）
    @Override
    public List<ChatMessageDTO> selectMessagesByHistoryId(String historyId) {
        return chatMessageMapper.selectAllDTOByhistoryId(historyId);
    }

    @Override
    public void deleteMessagesByhistoryId(Long chatHistoryId) {
        chatMessageMapper.deleteMessagesByhistoryId(chatHistoryId);
    }


    //将Message对象转为ChatMessage，当然Message的载荷要为ChatMessage chatMessage只含有sender，没有recipient
    @Override
    public ChatMessage parseMessageToChatMessage(Message message) {
        String payload = new String((byte[]) message.getPayload());
        ChatMessage chatMessage = JSONObject.parseObject(payload, ChatMessage.class);
        //取出消息头中session里的当前用户
        MessageHeaders headers = message.getHeaders();
        Map sessionAttributes = (Map) headers.get("simpSessionAttributes");
        //断言
        assert sessionAttributes != null;
        //从websocketSession取出发送者
        UserDTO sender = (UserDTO) sessionAttributes.get("user");
        chatMessage.setSender(sender);
        chatMessage.setSenderId(sender.getUserId());
        //根据双方id得出历史记录id
        String historyId = chatHistoryService.createHistoryId(sender.getUserId(), chatMessage.getRecipientId());
        chatMessage.setChatHistoryId(historyId);
        return chatMessage;
    }
}
