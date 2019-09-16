package cn.shirtiny.community.SHcommunity.Service;

import java.io.IOException;

public interface IGithubService {

    String getAccessToken(String code,String state) throws IOException;
    String getUserInfoJson(String access_token) throws IOException;

}
