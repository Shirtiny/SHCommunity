package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.messaging.Message;

import java.util.List;

public interface IchatMessageService {

    //增加一条消息
    boolean addChatMessage(String messageContent ,Long senderId,Long recipientId);

    //增加一条消息
    boolean addChatMessage(ChatMessage chatMessage);

    //查询某个聊天记录的消息 ,比如查询聊天室的消息（chatHistoryId为0的消息）
    List<ChatMessageDTO> selectMessagesByHistoryId(String historyId);

    //根据id清空一个聊天记录的所有数据
    void deleteMessagesByhistoryId(Long chatHistoryId);

    //将Message对象转为ChatMessage，当然Message的载荷要为ChatMessage chatMessage只含有sender，没有recipient
    ChatMessage parseMessageToChatMessage(Message message);
}
