package cn.shirtiny.community.SHcommunity.DTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubOauthDTO {

    @Value("${Github_Oauth_Client_Id}")
    private String client_id;//客户端id

    @Value("${Github_Oauth_Client_Secret}")
    private String client_secret;//客户端密钥

    @Value("${Github_Oauth_Scope}")
    private String scope;//获取信息列表

    @Value("${Github_Oauth_State}")
    private String state;//自定义字符串，用于验证

    private String code;//github返回的，验证用

    private String redirect_uri;//回调地址

    private String login;//特定用户登录

    private String allow_signup;//允许在登录时注册，默认true

    private String access_token;//返回的登录令牌


    public GithubOauthDTO() {
    }

    @Override
    public String toString() {
        return "GithubOauth{" +
                "client_id='" + client_id + '\'' +
                ", client_secret='" + client_secret + '\'' +
                ", scope='" + scope + '\'' +
                ", state='" + state + '\'' +
                ", code='" + code + '\'' +
                ", redirect_uri='" + redirect_uri + '\'' +
                ", login='" + login + '\'' +
                ", allow_signup='" + allow_signup + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAllow_signup() {
        return allow_signup;
    }

    public void setAllow_signup(String allow_signup) {
        this.allow_signup = allow_signup;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
