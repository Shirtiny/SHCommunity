package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class SectionDTO {

    //版块唯一标识
    long sectionId;
    //版块标题
    private String sectionTitle;
    //版块描述
    String sectionDescription;
    //版块头像
    String sectionAvatarImage;
    //帖子总数
    long invitationTotalNum;
    //版块糖数
    long sectionTotalCandy;
    //版主id
    long sectionOwnerId;
    //版块评分
    double sectionRate;
    //最新的帖子
    InvitationDTO latestInvitation;


}
