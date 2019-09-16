package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.GithubOauthDTO;
import cn.shirtiny.community.SHcommunity.Service.IGithubService;
import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GithubServiceImpl implements IGithubService {

    @Value("${Github_Oauth_Authorize_FullUrl}")
    private String fullUrl;
    @Value("${Github_Oauth_AccessToken_Url}")
    private String url_AccessToken;
    @Value("${Github_Oauth_User_Url}")
    private String url_User;
    @Autowired
    private GithubOauthDTO githubOauthDTO;//保存所有github授权信息的对象



    @Override
    public String getAccessToken(String code, String state) throws IOException {

        OkHttpClient httpClient = new OkHttpClient();
        //json MediaType
        final MediaType MediaType_JSON= MediaType.get("application/json; charset=utf-8");
        //需要实体类对象 然后转成json字符串
        githubOauthDTO.setCode(code);
        githubOauthDTO.setState(state);
//        githubOauth.setRedirect_uri("http://localhost:8888/github/codeCallback");
        String json = JSON.toJSONString(githubOauthDTO);
        //创建请求体
        RequestBody requestBody = RequestBody.create(MediaType_JSON, json);
        //建立请求，post方式调用
        Request request= new Request.Builder().url(url_AccessToken).post(requestBody).build();
        //执行请求
        Response response = httpClient.newCall(request).execute();
        //拿到响应结果
        String tokenAndType = response.body().string();
        System.out.println("得到通关令牌和令牌类型："+tokenAndType);
        return tokenAndType;
    }

    @Override
    public String getUserInfoJson(String access_token) throws IOException {
        //根据github回调code取用户信息，需要post请求
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url_User+"?access_token="+access_token).build();
        Response response = httpClient.newCall(request).execute();
        //得到响应的json字符串
        String userInfoJson = response.body().string();
        System.out.println("用令牌调用github的user_api，得到github用户信息："+userInfoJson);
        return userInfoJson;
    }
}
