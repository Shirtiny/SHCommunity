package cn.shirtiny.community.SHcommunity.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CommentDTO {
    //评论的主键id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;
    //评论者id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long reviewerId;
    //被评论的帖子对象id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long targetId;
    //评论内容
    private String commentContent;
    //创建时间
    private Long createdTime;
    //被此评论引用的评论id
    private Long citedCommentId;
    //评论者DTO信息
    private UserDTO reviewer;
    //被此评论引用的评论
    private CommentDTO citedComment;
}
