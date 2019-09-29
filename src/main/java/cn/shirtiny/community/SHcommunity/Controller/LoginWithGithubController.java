package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.GithubUserInfoDTO;
import cn.shirtiny.community.SHcommunity.Mapper.Pre_UserMapper;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IGithubService;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
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


    //请求Github登录授权
    @GetMapping(value = "/github/loginWithGithub")
    public String loginWithGithub() throws IOException {
        //发送请求
        return "redirect:" + Authorize_URL;
    }

    //github那边处理完登录、注册、授权，之后会回调设置的url，我设置的回调地址是：http://localhost:8888/github/codeCallback
    @GetMapping(value = "/github/codeCallback")//接收github返回的参数值，样例：http://callbackurl?code=...&state=...
    public String githubCodeCallback(String code, String state
            , HttpServletRequest httpServletRequest) throws IOException {
        String tokenAndType = githubService.getAccessToken(code, state);//tokenAndType样例access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
        //把tokenAndType根据&号分割，取第一个，再根据=号分割，取第二个，得到token
        String accessToken = tokenAndType.split("&")[0].split("=")[1];
        String userInfoJson = githubService.getUserInfoJson(accessToken);
        GithubUserInfoDTO userInfo = JSON.parseObject(userInfoJson, GithubUserInfoDTO.class);
        //根据github的userid查一下是不是已经注册了论坛
        List<User> users = userService.selectOneUserByGithubId(Long.parseLong(userInfo.getId()));
        if (users.size()>0){
            //若已经存在
            //存入session
            httpServletRequest.getSession().setAttribute("user", users.get(0));
            //返回主页
            return "redirect:/";
        }
        //若不存在
        User user=new User();
        //存到本地数据库的user对象
        user.setNickName(userInfo.getLogin());
        user.setEmail(userInfo.getEmail());
        user.setGithubId(userInfo.getId());
        user.setAvatarImage(userInfo.getAvatar_url());
        //获取当前系统的毫秒数
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        //添加进数据库
        //似乎添加后自动返回了user的自增id
        userService.addUser(user);
        System.out.println("github上获得的用户信息："+userInfo);
        System.out.println("存储的用户信息："+user);
        //存入session
        httpServletRequest.getSession().setAttribute("user", user);

        return "redirect:/";

    }


}
