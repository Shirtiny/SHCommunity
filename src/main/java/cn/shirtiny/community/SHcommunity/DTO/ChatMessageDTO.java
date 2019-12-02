package cn.shirtiny.community.SHcommunity.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
@Data
public class ChatMessageDTO{

    //主键
    @JsonSerialize(using = ToStringSerializer.class)
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
    //标识是否已读，不为空 默认false
    private boolean readed;
    //标识为系统通知
    private boolean systems;

    //发送者
    private UserDTO sender;
    //接收者
    private UserDTO recipient;
}
