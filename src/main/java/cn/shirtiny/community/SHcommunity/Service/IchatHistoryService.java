package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.Model.ChatHistory;

public interface IchatHistoryService {

    //增加一份聊天记录
    boolean addOneChatHistory(ChatHistory chatHistory);

    //根据发送方的id和接收方的id，得出一个值作为消息记录的id和双方的频道
    String createHistoryId(Long senderId, Long recipientId);
}
