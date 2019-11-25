package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.ChatMessageDTO;
import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IchatMessageService {

    //增加一条消息
    boolean addChatMessage(String messageContent ,String chatHistoryId,Long senderId,Long recipientId);

    //查询某个聊天记录的消息 ,比如查询聊天室的消息（chatHistoryId为0的消息）
    List<ChatMessageDTO> selectMessagesByHistoryId(String historyId);

    //根据id清空一个聊天记录的所有数据
    void deleteMessagesByhistoryId(Long chatHistoryId);
}
