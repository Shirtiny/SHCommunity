package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Service.IchatHistoryService;
import cn.shirtiny.community.SHcommunity.Service.IuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private IuserService userService;
    @Autowired
    private IchatHistoryService chatHistoryService;

    //返回第一个uid的用户信息，然后根据第二个uid生成uid_uid用来聊天的频道
    @GetMapping("/shApi/getUserByUserId")
    @ResponseBody
    public ShResultDTO<String, Object> getUserDtoByUid(Long userId, @RequestParam(value = "userId2", required = false) Long userId2) {
        UserDTO userDTO = userService.selectOneUserDtoByUid(userId);
        if (userDTO == null) {
            return new ShResultDTO<>(404, "未找到该用户");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("user", userDTO);
        String historyId =null;
        if (userId2 != null) {
            //生成消息记录id
            historyId = chatHistoryService.createHistoryId(userId, userId2);
        }
        data.put("chatHistoryId", historyId);
        return new ShResultDTO<>(200, "已返回用户信息和消息记录id", data, null);
    }

    @GetMapping("/Test6")
    public String Test6(HttpServletRequest request) {
        return "Test6";
    }


}
