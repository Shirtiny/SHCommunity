package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.Mapper.ChatMessageMapper;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import cn.shirtiny.community.SHcommunity.Service.IchatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.nio.cs.ext.MacArabic;

import java.util.List;

@Service
@Transactional
public class ChatMessageServiceImpl implements IchatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Value("${CHATMESSAGE_MAX_LENGTH}")
    private Integer CHATMESSAGE_MAX_LENGTH;

    //增加一条消息
    @Override
    public boolean addChatMessage(String messageContent, String chatHistoryId, Long senderId, Long recipientId) {
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
            chatMessage.setChatHistoryId(chatHistoryId);
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
    public List<ChatMessageDTO> selectMessagesByHistoryId(Long historyId) {
        return chatMessageMapper.selectAllDTOByhistoryId(historyId);
    }

    @Override
    public void deleteMessagesByhistoryId(Long chatHistoryId) {
        chatMessageMapper.deleteMessagesByhistoryId(chatHistoryId);
    }

}
