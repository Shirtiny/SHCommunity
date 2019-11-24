package cn.shirtiny.community.SHcommunity.Model;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@TableName("comment")
@Data
public class Comment {
    //评论的主键id
    @TableId(value = "comment_id",type = IdType.AUTO)
    private Long commentId;
    //评论者id
    @TableField(value = "reviewer_id",insertStrategy = FieldStrategy.NOT_NULL)
    private Long reviewerId;
    //被评论的帖子对象id
    @TableField(value = "target_id",insertStrategy = FieldStrategy.NOT_NULL)
    private Long targetId;
    //评论内容
    @TableField(value = "comment_content",insertStrategy = FieldStrategy.NOT_NULL)
    private String commentContent;
    //创建时间
    @TableField(value = "created_time",insertStrategy = FieldStrategy.NOT_NULL)
    private Long createdTime;
    //被此评论引用的评论id
    @TableField(value = "cited_comment_id")
    private Long citedCommentId;


}
