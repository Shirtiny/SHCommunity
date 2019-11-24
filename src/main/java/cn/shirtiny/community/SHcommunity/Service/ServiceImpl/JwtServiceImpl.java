package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Utils.JWT.JwtRsaHelper;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements IjwtService {

    @Autowired
    private JwtRsaHelper jwtRsaHelper;

    //从request请求头中解析token
    @Override
    public Map<String, Object> parseJwtByRequest(@NotNull HttpServletRequest request) {
        Claims claims=null;
        String jwt = request.getHeader("Authorization");
        return parseJwt(jwt);
    }

    //从cookie中解析token
    @Override
    public Map<String, Object> parseJwtByCookie(Cookie shJwt) {
        if (shJwt==null){
            return null;
        }else {
            return parseJwt(shJwt.getValue());
        }
    }

    //解析传入的token字符串
    @Override
    public Map<String, Object> parseJwt(String jwt) {
        Claims claims=null;
        if (jwt==null){
            return null;
        }
        try {
            claims = jwtRsaHelper.parseJwtBody(jwt);
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }

    //把token字符串解析后返回一个userDto对象
    @Override
    public UserDTO parseJwtToUser(String jwt) {
        Map<String, Object> calims = parseJwt(jwt);
        LinkedHashMap userMap=null;
        if (calims!=null){
            userMap=(LinkedHashMap)calims.get("user");
        }
        UserDTO user = null;
        if (userMap!=null){
            user=JSONObject.parseObject(JSONObject.toJSONString(userMap),UserDTO.class);
        }
        return user;
    }
}
