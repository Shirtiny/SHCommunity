package cn.shirtiny.community.SHcommunity.Model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

//聊天记录、频道
@Data
@TableName("chat_history")
public class ChatHistory {
    //主键
    @TableId(value = "chat_history_id",type = IdType.AUTO)
    Long chatHistoryId;
    //聊天记录的名称，唯一
    @TableField(value = "chat_history_name",insertStrategy = FieldStrategy.NOT_EMPTY)
    String chatHistoryName;
    //聊天创建时间
    @TableField(value = "gmt_created",insertStrategy = FieldStrategy.NOT_NULL)
    Long gmtCreated;
    //更新时间
    @TableField(value = "gmt_modified",insertStrategy = FieldStrategy.NOT_NULL)
    Long gmtModified;
    //消息条数
    @TableField(value = "message_num",insertStrategy = FieldStrategy.DEFAULT)
    Long messageNum;
}