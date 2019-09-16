package cn.shirtiny.community.SHcommunity.DTO;

import lombok.Data;

@Data
public class GithubUserInfoDTO {

    private String login;//github登录用的用户名
    private String id;//github的用户id
    private String avatar_url;//github的头像
    private String html_url;//github地址
    private String subscriptions_url;//github签名地址
    private String name;//姓名
    private String email;//邮箱
    private String location;//位置

}
