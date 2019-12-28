package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.GithubUserInfoDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Exception.GithubConnectedTimeOutException;
import cn.shirtiny.community.SHcommunity.Exception.UserInfoNotAllowException;
import cn.shirtiny.community.SHcommunity.Mapper.Pre_UserMapper;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IGithubService;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import cn.shirtiny.community.SHcommunity.Service.IloginService;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import com.alibaba.fastjson.JSON;
import com.baidu.fsg.uid.service.UidGenerateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class LoginWithGithubController {


    @Autowired
    private IGithubService githubService;
    @Value("${Github_Oauth_Authorize_FullUrl}")
    private String Authorize_URL;
    @Autowired
    public Pre_UserMapper preUserMapper;
    @Autowired
    private IuserService userService;
    @Autowired
    private IloginService loginService;
    @Autowired
    private UidGenerateService uidGenerateService;


    //请求Github登录授权
    @GetMapping(value = "/github/loginWithGithub")
    public String loginWithGithub() {
        //发送请求
        return "redirect:" + Authorize_URL;
    }

    //github那边处理完登录、注册、授权，之后会回调设置的url，我设置的回调地址是：http://localhost:8888/github/codeCallback
    //接收github返回的参数值，样例：http://callbackurl?code=...&state=...
    @GetMapping(value = "/github/codeCallback")
    public String githubCodeCallback(String code, String state
            , HttpServletRequest httpServletRequest, HttpServletResponse response) throws IOException {

        //tokenAndType样例access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
        String tokenAndType = githubService.getAccessToken(code, state);
        //把tokenAndType根据&号分割，取第一个，再根据=号分割，取第二个，得到token
        String accessToken = tokenAndType.split("&")[0].split("=")[1];
        String userInfoJson = githubService.getUserInfoJson(accessToken);
        GithubUserInfoDTO userInfo = JSON.parseObject(userInfoJson, GithubUserInfoDTO.class);
        //根据github的userid查一下是不是已经注册了论坛
        boolean exist = userService.userIsExistByGitId(userInfo.getId());
        if (exist) {
            //若已经存在
            //把对应的用户查出来
            User user = userService.selectOneUserByGitId(userInfo.getId());
            //为用户颁发jwt 设置到cookie中
            loginService.userLogin(user, response);
            //转到从cookie中找到的回调地址（没找到的话会返回"/ "）
            return "redirect:" + loginService.getRedirectFromCookie(httpServletRequest, response);
        }
        //若不存在，使用github的用户信息新建一个本地用户
        User user = githubService.localisationGithubUser(userInfo);
        //添加进数据库
        //邮箱适配 若无邮箱的临时处理
        if (user.getEmail() == null) {
            user.setEmail("null@shirtinynull.cn");
        }
        //用户名适配 临时处理
        if (user.getUserName().length() > 10) {
            user.setUserName(user.getUserName().substring(0, 10));
        } else if (user.getUserName().length() < 2) {
            user.setUserName(user.getUserName() + "0");
        }
        //密码适配 临时处理
        if (user.getPassWord() == null) {
            long uid = uidGenerateService.generateUid();
            user.setPassWord(uid+"");
        }
        //昵称适配 临时处理
        if(user.getNickName().trim().length()<2){
            user.setNickName(user.getNickName().trim()+"0");
        }else if (user.getNickName().trim().length()>10){
            user.setNickName(user.getNickName().trim().substring(0,10));
        }
        userService.addUser(user);
        System.out.println("github上获得的用户信息：" + userInfo);
        System.out.println("存储的用户信息：" + user);
        //设置jwt
        loginService.userLogin(user, response);
        //转到从cookie中找到的回调地址（没找到的话会返回"/ "）
        return "redirect:" + loginService.getRedirectFromCookie(httpServletRequest, response);
    }

}

