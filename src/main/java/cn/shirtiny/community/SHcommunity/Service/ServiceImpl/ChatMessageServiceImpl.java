package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Mapper.ChatHistoryMapper;
import cn.shirtiny.community.SHcommunity.Mapper.ChatMessageMapper;
import cn.shirtiny.community.SHcommunity.Mapper.UserMapper;
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

import java.util.*;

@Service
@Transactional
public class ChatMessageServiceImpl implements IchatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private IchatHistoryService chatHistoryService;
    @Autowired
    private ChatHistoryMapper chatHistoryMapper;
    @Autowired
    private UserMapper userMapper;

    @Value("${CHATMESSAGE_MAX_LENGTH}")
    private Integer CHATMESSAGE_MAX_LENGTH;

    //指定消息记录 添加一条消息
    @Override
    public boolean addChatMessageByChatHistoryId(String chatHistoryId, ChatMessage chatMessage) {
        chatMessage.setChatHistoryId(chatHistoryId);
        return insertChatMessage(chatMessage);
    }

    //增加一条消息
    @Override
    public boolean addChatMessage(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return false;
        }
        return addChatMessage(chatMessage.getChatMessageId(), chatMessage.getChatMessageContent(), chatMessage.getSenderId(), chatMessage.getRecipientId());
    }

    //增加一条消息
    @Override
    public boolean addChatMessage(Long chatMessageId, String messageContent, Long senderId, Long recipientId) {
        return insertChatMessage(chatMessageId, messageContent, senderId, recipientId, null, false);
    }

    @Override
    public boolean addChatMessage(Long chatMessageId, String messageContent, Long senderId, Long recipientId, boolean systems) {
        return insertChatMessage(chatMessageId, messageContent, senderId, recipientId, null, systems);
    }

    //消息入库
    @Override
    public boolean insertChatMessage(ChatMessage chatMessage) {
        Long chatMessageId = chatMessage.getChatMessageId();
        String chatMessageContent = chatMessage.getChatMessageContent();
        Long senderId = chatMessage.getSenderId();
        Long recipientId = chatMessage.getRecipientId();
        String chatHistoryId = chatMessage.getChatHistoryId();
        boolean systems = chatMessage.isSystems();
        return insertChatMessage(chatMessageId, chatMessageContent, senderId, recipientId, chatHistoryId, systems);
    }

    //消息入库
    @Override
    public boolean insertChatMessage(Long chatMessageId, String messageContent, Long senderId, Long recipientId, String chatHistoryId, boolean systems) {
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
            //主键id
            if (chatMessageId != null) {
                chatMessage.setChatMessageId(chatMessageId);
            }
            //保存此消息的记录id
            if (chatHistoryId == null || chatHistoryId.trim().length() == 0) {
                String historyId = chatHistoryService.createHistoryId(senderId, recipientId);
                chatMessage.setChatHistoryId(historyId);
            } else {
                chatMessage.setChatHistoryId(chatHistoryId);
            }
            //创建时间
            chatMessage.setGmtCreated(System.currentTimeMillis());
            //消息内容
            chatMessage.setChatMessageContent(messageContent);
            //发送者id
            chatMessage.setSenderId(senderId);
            //接收者id
            chatMessage.setRecipientId(recipientId);
            //系统标识
            chatMessage.setSystems(systems);
            //插入数据库
            chatMessageMapper.insert(chatMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //查询某个聊天记录的消息及其全部内容，显示倒数size条 ,比如查询聊天室的消息（chatHistoryId为0的消息）
    @Override
    public List<ChatMessageDTO> selectMessagesByHistoryId(String historyId, int size) {
        assert size > 0;
        int totalCount = chatHistoryMapper.selectMessageCountById(historyId);
        int offset = 0;
        if (totalCount > size) {
            offset = totalCount - size;
        }
        List<ChatMessageDTO> chatMessageDTOS = chatMessageMapper.selectAllDTOByhistoryId(historyId, offset, size);

        return supplementMessageSender(chatMessageDTOS);
    }

    //重载 第curPage页的消息及其全部内容
    @Override
    public List<ChatMessageDTO> selectMessagesByHistoryId(String historyId, int curPage, int size) {
        assert size > 0;
        int offset = 0;
        int totalCount = chatHistoryMapper.selectMessageCountById(historyId);
        int totalPageNum = (totalCount + size - 1) / size;
        if (curPage > 0) {
            offset = (curPage - 1) * size;
        } else if (curPage > totalPageNum && totalCount > size) {
            offset = totalCount - size;
        }
        List<ChatMessageDTO> chatMessageDTOS = chatMessageMapper.selectAllDTOByhistoryId(historyId, offset, size);
        return supplementMessageSender(chatMessageDTOS);
    }

    //给定一个消息列表，返回带有sender的消息列表
    private List<ChatMessageDTO> supplementMessageSender(List<ChatMessageDTO> chatMessageDTOS) {
        List<ChatMessageDTO> tempList = new LinkedList<>();
        for (ChatMessageDTO chatMessage : chatMessageDTOS) {
            UserDTO sender = userMapper.selectUserDtoByid(chatMessage.getSenderId());
            chatMessage.setSender(sender);
            tempList.add(chatMessage);
        }
        return tempList;
    }

    @Override
    public void deleteMessagesByhistoryId(Long chatHistoryId) {
        chatMessageMapper.deleteMessagesByhistoryId(chatHistoryId);
    }

    //将Message对象转为ChatMessage，自动生成chatHistoryId，当然Message的载荷要为ChatMessage chatMessage只含有sender，没有recipient
    @Override
    public ChatMessage parseMessageToChatMessage(Message message) {
        return parseMessageToChatMessage(message,null);
    }

    //将Message对象转为ChatMessage，当然Message的载荷要为ChatMessage chatMessage只含有sender，没有recipient
    @Override
    public ChatMessage parseMessageToChatMessage(Message message, String chatHistoryId) {
        String payload = new String((byte[]) message.getPayload());
        ChatMessage chatMessage = JSONObject.parseObject(payload, ChatMessage.class);
        //取出消息头中session里的当前用户
        MessageHeaders headers = message.getHeaders();
        Map sessionAttributes = (Map) headers.get("simpSessionAttributes");
        //断言
        assert sessionAttributes != null;
        //从websocketSession取出发送者
        UserDTO sender = (UserDTO) sessionAttributes.get("user");
        if (sender != null) {
            chatMessage.setSender(sender);
            chatMessage.setSenderId(sender.getUserId());
            if (chatHistoryId == null){
                //根据双方id得出历史记录id
                String historyId = chatHistoryService.createHistoryId(sender.getUserId(), chatMessage.getRecipientId());
                chatMessage.setChatHistoryId(historyId);
            }else {
                chatMessage.setChatHistoryId(chatHistoryId);
            }
        }
        //若sender == null 表示无发送者，则为游客 客户端将游客id作为senderId传输
        return chatMessage;
    }

    //根据chatHistoryId和chatMessageContent查询一个消息 待改 无法唯一确定一个消息
//    @Override
//    public Long selectMessageId(String chatHistoryId,String chatMessageContent) {
//
//        return chatMessageMapper.selectByHidAndContent(chatHistoryId,chatMessageContent);
//    }

    //根据消息id 更新消息的已读状态，false未读 true已读
    @Override
    public void updateMessageRead(String chatMessageId, boolean readed) {
        chatMessageMapper.updateReadByid(chatMessageId, readed);
    }

    //根据消息id集合 批量的更新消息的已读状态
    @Override
    public void updateMessageReadByList(List<String> chatMessageIds, boolean readed) {
        for (String chatMessageId : chatMessageIds) {
            chatMessageMapper.updateReadByid(chatMessageId, readed);
        }
    }

    //把某个消息记录 的所有消息 的已读状态更新
    @Override
    public void updateMessageReadByHistoryId(String chatHistoryId, boolean readed) {
        List<String> chatMessageIds = chatMessageMapper.selectMessageIdsByHistoryId(chatHistoryId);
        updateMessageReadByList(chatMessageIds, readed);
    }
}
