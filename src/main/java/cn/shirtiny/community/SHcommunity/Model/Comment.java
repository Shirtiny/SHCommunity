package cn.shirtiny.community.SHcommunity.Model;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@TableName("comment")
@Data
public class Comment {
    //评论的主键id
    @TableId(value = "comment_id",type = IdType.AUTO)
    long commentId;
    //评论者id
    @TableField(value = "reviewer_id",insertStrategy = FieldStrategy.NOT_NULL)
    long reviewerId;
    //被评论的对象id
    @TableField(value = "target_id",insertStrategy = FieldStrategy.NOT_NULL)
    long targetId;
    //评论内容
    @TableField(value = "comment_content",insertStrategy = FieldStrategy.NOT_NULL)
    String commentContent;
    //创建时间
    @TableField(value = "created_time",insertStrategy = FieldStrategy.NOT_NULL)
    long createdTime;
    //评论者DTO信息
    @TableField(exist = false)
    UserDTO userDTO;





}
