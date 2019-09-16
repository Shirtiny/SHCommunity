package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class InvitationDTO {
    private long id;//主键id
    private String title;//标题
    private String content;//内容
    //回复类
    private int replyNum;//回复数
    private long gmtCreated;//创建时间
    private long gmtModified;//更新时间
    private String createdDate;//格式化的创建时间，不在数据库中
    private String modifiedDate;//格式化的更新时间，不在数据库中
}
