package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class ChatMessageDTO {
    //主键
    Long chatMessageId;
    //记录此消息的 聊天记录的id
    Long chatHistoryId;
    //消息内容
    String chatMessageContent;
    //创建时间
    Long gmtCreated;
    //发送者id
    Long senderId;
    //接收者id
    Long recipientId;

    //发送者
    UserDTO sender;
    //接收者
    UserDTO recipient;
}
