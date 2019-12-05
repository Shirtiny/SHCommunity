package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.messaging.Message;

import java.util.List;

public interface IchatMessageService {

    //增加一条消息
    boolean addChatMessage(Long chatMessageId,String messageContent ,Long senderId,Long recipientId);

    //增加一条消息
    boolean addChatMessage(ChatMessage chatMessage);

    //查询某个聊天记录的消息 ,比如查询聊天室的消息（chatHistoryId为0的消息）
    List<ChatMessageDTO> selectMessagesByHistoryId(String historyId);

    //根据id清空一个聊天记录的所有数据
    void deleteMessagesByhistoryId(Long chatHistoryId);

    //将Message对象转为ChatMessage，当然Message的载荷要为ChatMessage chatMessage只含有sender，没有recipient
    ChatMessage parseMessageToChatMessage(Message message);

    //根据chatHistoryId和chatMessageContent查询一个消息 待改 无法唯一确定一个消息
//    Long selectMessageId(String chatHistoryId,String chatMessageContent);

    //根据消息id 更新消息的已读状态，false未读 true已读
    void updateMessageRead(String chatMessageId,boolean readed);

    //根据消息id集合 批量的更新消息的已读状态
    void updateMessageReadByList(List<String> chatMessageIds,boolean readed);

    //把某个消息记录 的所有消息 的已读状态更新
    void updateMessageReadByHistoryId(String chatHistoryId,boolean readed);

    //查出某个消息记录的所有未读消息

}
