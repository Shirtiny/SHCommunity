package cn.shirtiny.community.SHcommunity.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UserDTO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    //账户创建时间
    private long gmtCreate;
    //昵称
    private String nickName;
    //用户名
    private String userName;
    //头像
    private String avatarImage;
    //签名、描述、自我介绍
    private String description;
    private String createdTime;//创建日期字符串
}
