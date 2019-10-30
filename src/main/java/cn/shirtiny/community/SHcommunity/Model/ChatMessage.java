package cn.shirtiny.community.SHcommunity.Model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

//聊天消息
@Data
@TableName("chat_message")
public class ChatMessage {
    //主键
    @TableId(value = "chat_message_id",type = IdType.AUTO)
    Long chatMessageId;
    //记录此消息的 聊天记录的id
    @TableField(value = "chat_history_id",insertStrategy = FieldStrategy.NOT_NULL)
    Long chatHistoryId;
    //消息内容
    @TableField(value = "chat_message_content",insertStrategy = FieldStrategy.NOT_EMPTY)
    String chatMessageContent;
    //创建时间
    @TableField(value = "gmt_created",insertStrategy = FieldStrategy.NOT_NULL)
    Long gmtCreated;
    //发送者id，可以为空
    @TableField(value = "sender_id")
    Long senderId;
    //接收者id，可以为空
    @TableField(value = "recipient_id")
    Long recipientId;
}
