package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.GithubUserInfoDTO;
import cn.shirtiny.community.SHcommunity.Model.User;

import java.io.IOException;

public interface IGithubService {

    //github获取通行令牌
    String getAccessToken(String code,String state) throws IOException;

    //获取github用户信息
    String getUserInfoJson(String access_token) throws IOException;

    //本地化github用户（把github用户信息转化为本地用户信息，以存储用户，注册本站）
    User localisationGithubUser(GithubUserInfoDTO githubUser);

}
