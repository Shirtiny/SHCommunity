package cn.shirtiny.community.SHcommunity.DTO;

import cn.shirtiny.community.SHcommunity.Model.ChatMessage;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;
@Data
public class ChatHistoryDTO {
    //主键
    private String chatHistoryId;
    //聊天记录的名称
    private String chatHistoryName;
    //聊天创建时间
    private Long gmtCreated;
    //更新时间
    private Long gmtModified;
    //消息条数
    private Long messageNum;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long recipientId;
    //频道
    private String channel;
    //记录的消息列表,不在数据库中
    private List<ChatMessage> chatMessages;
}
