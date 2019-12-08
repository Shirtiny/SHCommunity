package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.ChatHistoryDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Mapper.ChatHistoryMapper;
import cn.shirtiny.community.SHcommunity.Mapper.UserMapper;
import cn.shirtiny.community.SHcommunity.Model.ChatHistory;
import cn.shirtiny.community.SHcommunity.Service.IchatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChatHistoryServiceImpl implements IchatHistoryService {

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;
    @Autowired
    private UserMapper userMapper;

    //根据id判断历史记录是否已经存在
    @Override
    public boolean selectIsExist(String historyId) {
        int exist = chatHistoryMapper.isExist(historyId);
        return exist > 0;
    }

    //增加一份聊天记录
    @Override
    public boolean addOneChatHistory(ChatHistory chatHistory) {
        //先根据id查询
        if (chatHistory != null && chatHistory.getChatHistoryId() != null) {
            boolean isExist = selectIsExist(chatHistory.getChatHistoryId());
            if (!isExist) {
                try {
                    //创建时间 修改时间
                    chatHistory.setGmtCreated(System.currentTimeMillis());
                    chatHistory.setGmtModified(chatHistory.getGmtCreated());
                    //插入数据库
                    chatHistoryMapper.insert(chatHistory);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    //按双方id来创建聊天记录
    @Override
    public boolean addOneChatHistoryBy2Id(Long senderId, Long recipientId) {
        String historyId = createHistoryId(senderId, recipientId);
        if (selectIsExist(historyId)) {
            return false;
        }
        ChatHistory chatHistory = new ChatHistory();
        UserDTO sender = userMapper.selectUserDtoByid(senderId);
        UserDTO recipient = userMapper.selectUserDtoByid(recipientId);
        if (sender != null && recipient != null) {
            //记录名 sender.Name+"_"+recipient.Name
            chatHistory.setChatHistoryName(sender.getUserName() + "_" + recipient.getUserName());
            //发送人id
            chatHistory.setSenderId(senderId);
            //收信人id
            chatHistory.setRecipientId(recipientId);
            //主键id
            chatHistory.setChatHistoryId(historyId);
            //频道
            chatHistory.setChannel("/user/" + historyId + "/121chat");
            //消息数
            chatHistory.setMessageNum(1L);
        }
        return addOneChatHistory(chatHistory);
    }

    //按收信者id来创建系统消息记录
    @Override
    public boolean addOneSystemChatHistory(Long recipientId) {
        String historyId = createHistoryId(0L, recipientId);
        if (historyId == null || selectIsExist(historyId)) {
            return false;
        }
        ChatHistory chatHistory = new ChatHistory();
        UserDTO recipient = userMapper.selectUserDtoByid(recipientId);
        if (recipient != null) {
            //记录名 sender.Name+"_"+recipient.Name
            chatHistory.setChatHistoryName("看板娘_" + recipient.getUserName());
            //发送人id
            chatHistory.setSenderId(0L);
            //收信人id
            chatHistory.setRecipientId(recipientId);
            //主键id
            chatHistory.setChatHistoryId(historyId);
            //频道
            chatHistory.setChannel("/userId/" + recipientId);
            //消息数
            chatHistory.setMessageNum(1L);
            //系统通信标识
            chatHistory.setSystemSign(true);
        }
        return addOneChatHistory(chatHistory);
    }

    //消息数自增，更新聊天记录的修改时间
    @Override
    public void incrMessageNumAndgmtModified(String historyId) {
        boolean isExist = selectIsExist(historyId);
        if (isExist) {
            //消息数自增
            chatHistoryMapper.incrMessageNum(historyId);
            //修改时间更新
            chatHistoryMapper.updateGmtModified(historyId, System.currentTimeMillis());
        }
    }

    @Override
    public String createHistoryId(Long senderId, Long recipientId) {
        if (senderId == null || recipientId == null) {
            return null;
        }
        long min = Math.min(senderId, recipientId);
        long max = Math.max(senderId, recipientId);
        String retStr = min + "_" + max;
        System.out.println("历史记录id为：" + min + "_" + max);
        return retStr;
    }

    //查询某个用户的全部的非系统消息记录及其内容 并设置targetUser的值
    @Override
    public List<ChatHistoryDTO> selectAllchatHistoryMessageByUid(Long userId) {
        List<ChatHistoryDTO> chatHistories = chatHistoryMapper.selectAllChatHistoryByUid(userId, false);
        List<ChatHistoryDTO> tempList = new ArrayList<>();
        for (ChatHistoryDTO chatHistory : chatHistories) {
            long senderId = chatHistory.getSenderId();
            long recipientId = chatHistory.getRecipientId();
            UserDTO targetUser = null;
            if (userId == senderId) {
                targetUser = userMapper.selectUserDtoByid(recipientId);
            } else {
                targetUser = userMapper.selectUserDtoByid(senderId);
            }
            chatHistory.setTargetUser(targetUser);
            tempList.add(chatHistory);
        }
        return tempList;
    }

    //查询某个用户的全部系统消息记录及其内容
    @Override
    public List<ChatHistoryDTO> selectAllsystemHistoryMessageByUid(Long userId) {
        return chatHistoryMapper.selectAllChatHistoryByUid(userId, true);
    }

    //查询出单个用户的所有聊天记录 的所有未读消息数
    @Override
    public int selectUnReadMessagesCountByUid(Long userId) {
        return chatHistoryMapper.selectReadCountByUid(userId, false);
    }
}
