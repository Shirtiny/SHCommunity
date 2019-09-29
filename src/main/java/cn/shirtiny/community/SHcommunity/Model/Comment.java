package cn.shirtiny.community.SHcommunity.Model;

import lombok.Data;

@Data
public class Comment {

    long commentId;//评论的主键id
    long reviewerId;//评论者id
    String content;//评论内容
    long createdTime;//创建时间




}
