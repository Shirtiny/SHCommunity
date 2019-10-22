package cn.shirtiny.community.SHcommunity.Model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("section")
public class Section {
    //版块唯一标识
    @TableId(value = "section_id",type = IdType.AUTO)
    long sectionId;
    //版块标题
    @TableField(value = "section_title", insertStrategy = FieldStrategy.NOT_EMPTY)
    private String sectionTitle;
    //版块描述
    @TableField(value = "section_description", insertStrategy = FieldStrategy.NOT_EMPTY)
    String sectionDescription;
    //版块头像
    @TableField(value = "section_avatar_image", insertStrategy = FieldStrategy.NOT_EMPTY)
    String sectionAvatarImage;
    //帖子总数
    @TableField(value = "invitation_total_num", insertStrategy = FieldStrategy.DEFAULT)
    long invitationTotalNum;
    //精帖数
    @TableField(value = "invitation_star_num", insertStrategy = FieldStrategy.DEFAULT)
    long invitationStarNum;
    //版主id
    @TableField(value = "section_owner_id", insertStrategy = FieldStrategy.DEFAULT)
    long sectionOwnerId;
    //版块评分
    @TableField(value = "section_rate", insertStrategy = FieldStrategy.DEFAULT)
    double sectionRate;
}
