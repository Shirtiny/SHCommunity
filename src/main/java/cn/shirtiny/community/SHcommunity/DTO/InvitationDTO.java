package cn.shirtiny.community.SHcommunity.DTO;

import cn.shirtiny.community.SHcommunity.Model.Comment;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class InvitationDTO {
    //long类型传到前端时，会有精度损失，需要在后端转为字符串，可以直接将dto属性设为字符串类型，也可以用注解
    @JsonSerialize(using = ToStringSerializer.class)//作用于属性的get方法
    private long invitationId;//主键id
    private String title;//标题
    private String content;//内容
    //回复类
    private int replyNum;//回复数
    //浏览量views
    private long views;
    //可以直接设为字符串，赋值时会自动转换
    @JsonSerialize(using = ToStringSerializer.class)
    private long gmtCreated;//创建时间
    @JsonSerialize(using = ToStringSerializer.class)
    private long gmtModified;//更新时间
//  private String createdDate;//格式化的创建时间，不在数据库中，在页面里格式化了
//  private String modifiedDate;//格式化的更新时间，不在数据库中
    @JsonSerialize(using = ToStringSerializer.class)
    private long authorId; //作者id
    private String avatarImage;//作者头像
    private String nickName;//作者昵称
    private String description;//作者的签名、描述、自我介绍
    @JsonSerialize(using = ToStringSerializer.class)
    private long gmtCreate;//作者的注册时间
    //评论集合
    private List<Comment> comments;
}
