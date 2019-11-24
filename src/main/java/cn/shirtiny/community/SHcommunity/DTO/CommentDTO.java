package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class CommentDTO {
    //评论的主键id
    private Long commentId;
    //评论者id
    private Long reviewerId;
    //被评论的帖子对象id
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
