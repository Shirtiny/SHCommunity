package cn.shirtiny.community.SHcommunity.Model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

//聊天记录、频道
@Data
@TableName("chat_history")
public class ChatHistory {
    //主键
    @TableId(value = "chat_history_id")
    private String chatHistoryId;
    //聊天记录的名称，唯一
    @TableField(value = "chat_history_name",insertStrategy = FieldStrategy.NOT_EMPTY)
    private String chatHistoryName;
    //聊天创建时间
    @TableField(value = "gmt_created",insertStrategy = FieldStrategy.NOT_NULL)
    private Long gmtCreated;
    //更新时间
    @TableField(value = "gmt_modified",insertStrategy = FieldStrategy.NOT_NULL)
    private Long gmtModified;
    //消息条数
    @TableField(value = "message_num",insertStrategy = FieldStrategy.DEFAULT)
    private Long messageNum;
    //发送者id
    @TableField(value = "sender_id")
    private Long senderId;
    //接收者id
    @TableField(value = "recipient_id")
    private Long recipientId;
    //频道
    @TableField(value = "channel")
    private String channel;
    //系统标记 标识此消息记录为用户与系统的通信历史 默认false
    @TableField(value = "system_sign",insertStrategy = FieldStrategy.DEFAULT)
    private boolean systemSign;
}
