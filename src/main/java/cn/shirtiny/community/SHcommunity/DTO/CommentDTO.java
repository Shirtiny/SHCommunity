package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class CommentDTO {
    //评论的主键id
    Long commentId;
    //评论者id
    Long reviewerId;
    //被评论的帖子对象id
    Long targetId;
    //评论内容
    String commentContent;
    //创建时间
    Long createdTime;
    //被此评论引用的评论id
    Long citedCommentId;
    //评论者DTO信息
    UserDTO reviewer;
    //被此评论引用的评论
    CommentDTO citedComment;
}
