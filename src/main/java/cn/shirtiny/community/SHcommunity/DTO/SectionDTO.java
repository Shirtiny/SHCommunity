package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class SectionDTO {

    //版块唯一标识
    private long sectionId;
    //版块标题
    private String sectionTitle;
    //版块描述
    private String sectionDescription;
    //版块头像
    private String sectionAvatarImage;
    //帖子总数
    private long invitationTotalNum;
    //版块糖数
    private long sectionTotalCandy;
    //版主id
    private long sectionOwnerId;
    //版块评分
    private double sectionRate;
    //最新的帖子
    private InvitationDTO latestInvitation;


}
