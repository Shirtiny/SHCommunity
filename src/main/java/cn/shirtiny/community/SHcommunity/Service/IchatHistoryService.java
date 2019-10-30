package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.Model.ChatHistory;

public interface IchatHistoryService {

    //增加一份聊天记录
    boolean addOneChatHistory(ChatHistory chatHistory);
}
