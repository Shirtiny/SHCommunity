package cn.shirtiny.community.SHcommunity.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
@Data
public class ChatMessageDTO{

    //主键
    private Long chatMessageId;
    //记录此消息的 聊天记录的id
    private String chatHistoryId;
    //消息内容
    private String chatMessageContent;
    //创建时间
    private Long gmtCreated;
    //发送者id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;
    //接收者id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long recipientId;

    //发送者
    private UserDTO sender;
    //接收者
    private UserDTO recipient;
    //标识为系统通知
    private boolean system;

    public ChatMessageDTO() {
    }


}
