package cn.shirtiny.community.SHcommunity.Model;

import lombok.Data;

@Data
public class User {//论坛用户
//    private long id;//用户唯一主键，自增长
    private String nickName;//昵称
    private String passWord;//密码
    private String email;//邮箱
    private String avatarImage;//头像
    private String githubId;//github的用户编号
    private long gmtCreate;//创建账户时的时间戳
    private long  gmtModified;//修改用户时的时间戳
    private String token;//cookie里的登录令牌

}
