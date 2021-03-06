package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Model.User;
import cn.shirtiny.community.SHcommunity.Service.IcookieService;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements IjwtService {

    @Autowired
    private JwtRsaHelper jwtRsaHelper;
    @Autowired
    private IcookieService cookieService;

    //从request请求头中解析token
    @Override
    public Map<String, Object> parseJwtByRequest(@NotNull HttpServletRequest request) {
        Claims claims = null;
        String jwt = request.getHeader("Authorization");
        return parseJwt(jwt);
    }

    //从cookie中解析token
    @Override
    public Map<String, Object> parseJwtByCookie(HttpServletRequest request) {
        if (request == null) {
            return null;
        } else {
            String shJwt = cookieService.getShJwtFromCookie(request.getCookies());
            return parseJwt(shJwt);
        }
    }

    //解析传入的token字符串
    @Override
    public Map<String, Object> parseJwt(String jwt) {
        if (jwt == null || "".equals(jwt.trim())) {
            return null;
        }
        Claims claims = null;
        try {
            claims = jwtRsaHelper.parseJwtBody(jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    //把token字符串解析后返回一个userDto对象
    @Override
    public UserDTO parseJwtToUser(String jwt) {
        Map<String, Object> calims = parseJwt(jwt);
        LinkedHashMap userMap = null;
        if (calims != null) {
            userMap = (LinkedHashMap) calims.get("user");
        }
        UserDTO user = null;
        if (userMap != null) {
            user = JSONObject.parseObject(JSONObject.toJSONString(userMap), UserDTO.class);
        }
        return user;
    }

    //将用户信息封装成jwt
    @Override
    public String userToJwt(User user) {
        return jwtRsaHelper.createJwt(user);
    }

    //将用户名、所填邮箱 封装为jwt 返回base64URL编码的字符串
    @Override
    public String userSignMailToJwtBase64Url(String userName, String mailBox) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName",userName);
        claims.put("subject","主题：绑定邮箱");
        claims.put("mailBox",mailBox);
        String jwt = jwtRsaHelper.createHsJwt(claims);
        return Base64.getUrlEncoder().encodeToString(jwt.getBytes());
    }
}
