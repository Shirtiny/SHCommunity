package cn.shirtiny.community.SHcommunity.Model;

import lombok.Data;

@Data
public class Invitation {//论坛帖子
//    private long id;//主键id，因为数据库内是自增的，用Integer类型，默认为空
    private String title;//标题
    private String content;//内容
    //回复类
    private int replyNum;//回复数
    private long gmtCreated;//创建时间
    private long gmtModified;//更新时间
    private long authorId;//发帖者的id

}
