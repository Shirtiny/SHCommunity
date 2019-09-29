package cn.shirtiny.community.SHcommunity.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class InvitationDTO {
    private Long invitationId;//主键id
    private String title;//标题
    private String content;//内容
    //回复类
    private int replyNum;//回复数
    private long gmtCreated;//创建时间
    private long gmtModified;//更新时间
//  private String createdDate;//格式化的创建时间，不在数据库中，在页面里格式化了
//  private String modifiedDate;//格式化的更新时间，不在数据库中
    private Long authorId; //作者id
    private String avatarImage;//作者头像
    private String nickName;//作者昵称
    private String description;//作者的签名、描述、自我介绍
    private String gmtCreate;//作者的注册时间

}
