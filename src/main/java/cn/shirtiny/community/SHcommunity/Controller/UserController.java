package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    //注销
    @GetMapping("/logout")
    @ResponseBody
    public ShResultDTO logout(HttpServletRequest request){
        request.getSession().invalidate();
        return new ShResultDTO(200,"用户注销");
    }

    //登录状态检查
    @GetMapping("/shApi/loginCheck")
    @ResponseBody
    public ShResultDTO loginChecker(HttpServletRequest request){
        if (request.getSession().getAttribute("user")!=null){
            return new ShResultDTO(200,"已登录");
        }else {
            return new ShResultDTO(4001,"未登录，请先登录再进行此操作");
        }
    }

    @GetMapping("/Test6")
    public String Test6(HttpServletRequest request){
        return "Test6";
    }


}
