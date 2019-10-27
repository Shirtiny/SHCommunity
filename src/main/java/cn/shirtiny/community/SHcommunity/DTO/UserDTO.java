package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    //账户创建时间
    long gmtCreate;
    //昵称
    private String nickName;
    //头像
    private String avatarImage;
    //签名、描述、自我介绍
    private String description;
}
