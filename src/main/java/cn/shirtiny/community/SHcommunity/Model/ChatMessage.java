package cn.shirtiny.community.SHcommunity.Model;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

//聊天消息
@Data
@TableName("chat_message")
public class ChatMessage {
    //主键
    @TableId(value = "chat_message_id",type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chatMessageId;
    //记录此消息的 聊天记录的id
    @TableField(value = "chat_history_id",insertStrategy = FieldStrategy.NOT_NULL)
    private String chatHistoryId;
    //消息内容
    @TableField(value = "chat_message_content",insertStrategy = FieldStrategy.NOT_EMPTY)
    private String chatMessageContent;
    //创建时间
    @TableField(value = "gmt_created",insertStrategy = FieldStrategy.NOT_NULL)
    private Long gmtCreated;
    //发送者id，可以为空
    @TableField(value = "sender_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;
    //接收者id，可以为空
    @TableField(value = "recipient_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long recipientId;

    //发送者
    @TableField(exist = false)
    private UserDTO sender;
    //接收者
    @TableField(exist = false)
    private UserDTO recipient;
    //标识为系统通知
    @TableField(exist = false)
    private boolean system;

}
