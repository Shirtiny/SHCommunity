package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.GithubUserInfoDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Mapper.Pre_UserMapper;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IGithubService;
import cn.shirtiny.community.SHcommunity.Service.IloginService;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import com.alibaba.fastjson.JSON;
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


    //请求Github登录授权
    @GetMapping(value = "/github/loginWithGithub")
    public String loginWithGithub() throws IOException {
        //发送请求
        return "redirect:" + Authorize_URL;
    }

    //github那边处理完登录、注册、授权，之后会回调设置的url，我设置的回调地址是：http://localhost:8888/github/codeCallback
    //接收github返回的参数值，样例：http://callbackurl?code=...&state=...
    @GetMapping(value = "/github/codeCallback")
    public String githubCodeCallback(String code, String state
            , HttpServletRequest httpServletRequest,HttpServletResponse response) throws IOException {

        //tokenAndType样例access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
        String tokenAndType = githubService.getAccessToken(code, state);
        //把tokenAndType根据&号分割，取第一个，再根据=号分割，取第二个，得到token
        String accessToken = tokenAndType.split("&")[0].split("=")[1];
        String userInfoJson = githubService.getUserInfoJson(accessToken);
        GithubUserInfoDTO userInfo = JSON.parseObject(userInfoJson, GithubUserInfoDTO.class);
        //根据github的userid查一下是不是已经注册了论坛
        List<User> users = userService.selectOneUserByGithubId(Long.parseLong(userInfo.getId()));
        if (users.size()>0){
            //若已经存在
            User user = users.get(0);
            UserDTO userDTO=new UserDTO();
            BeanUtils.copyProperties(user,userDTO);
            //存入session
            httpServletRequest.getSession().setAttribute("user", userDTO);
            //转到从cookie中找到的回调地址（没找到的话会返回"/ "）
            return "redirect:"+loginService.getRedirectFromCookie(httpServletRequest,response);
//            return "redirect:/";
        }
        //若不存在，使用github的用户信息新建一个本地用户
        User user = githubService.localisationGithubUser(userInfo);
        //添加进数据库
        //似乎添加后自动返回了user的自增id
        userService.addUser(user);
        System.out.println("github上获得的用户信息："+userInfo);
        System.out.println("存储的用户信息："+user);
        //存入session
        //存入dto，避免隐私信息
        UserDTO userDTO=new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        httpServletRequest.getSession().setAttribute("user", userDTO);
        //转到从cookie中找到的回调地址（没找到的话会返回"/ "）
        return "redirect:"+loginService.getRedirectFromCookie(httpServletRequest,response);
    }


}
