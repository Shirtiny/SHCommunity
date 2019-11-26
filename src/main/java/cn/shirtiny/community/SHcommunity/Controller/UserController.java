package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private IuserService userService;

    @GetMapping("/shApi/getUserByUserId")
    @ResponseBody
    public ShResultDTO<String,Object> getUserDtoByUid(Long userId){
        UserDTO userDTO = userService.selectOneUserDtoByUid(userId);
        if (userDTO == null){
            return new ShResultDTO<>(404,"未找到该用户");
        }
        Map<String,Object> data =new HashMap<>();
        data.put("user",userDTO);
        return new ShResultDTO<>(200,"已返回用户信息",data,null);
    }

    @GetMapping("/Test6")
    public String Test6(HttpServletRequest request){
        return "Test6";
    }


}
