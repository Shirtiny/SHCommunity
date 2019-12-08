package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.ChatHistoryDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatHistory;

import java.util.List;

public interface IchatHistoryService {

    //根据id判断历史记录是否已经存在
    boolean selectIsExist(String historyId);

    //增加一份聊天记录
    boolean addOneChatHistory(ChatHistory chatHistory);

    //按双方id来创建聊天记录
    boolean addOneChatHistoryBy2Id(Long senderId,Long recipientId);

    //按收信者id来创建系统消息记录
    boolean addOneSystemChatHistory(Long recipientId);

    //消息数自增，更新聊天记录的修改时间
    void incrMessageNumAndgmtModified(String historyId);

    //根据发送方的id和接收方的id，得出一个值作为消息记录的id和双方的频道
    String createHistoryId(Long senderId, Long recipientId);

    //查询某个用户的全部非系统消息记录及其内容 并设置targetUser的值
    List<ChatHistoryDTO> selectAllchatHistoryMessageByUid(Long userId);

    //查询某个用户的全部系统消息记录及其内容
    List<ChatHistoryDTO> selectAllsystemHistoryMessageByUid(Long userId);

    //查询出单个用户的所有聊天记录 的所有未读消息数
    int selectUnReadMessagesCountByUid(Long userId);
}
