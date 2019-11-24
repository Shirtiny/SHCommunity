package cn.shirtiny.community.SHcommunity.Controller;

import cn.shirtiny.community.SHcommunity.Utils.JWT.JwtRsaHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JwtController {

    @Autowired
    private JwtRsaHelper jwtRsaHelper;

    //访问即可获得jwt 需要传入key和value
    @PostMapping("/shApi/jwtLogin")
    public String loginReJwt(@RequestParam(value = "key",required = false,defaultValue = "login") String key
            ,@RequestParam(value = "value",required = false,defaultValue = "jwt登录完成") String value){
        Map<String,Object> calims = new HashMap<>();
        calims.put(key,value);
        return jwtRsaHelper.createJwt(calims);
    }

    //传入jwt 返回jwt全部解析结果
    @GetMapping("/shApi/jwtParse")
    public Jws<Claims> jwtParser(String jwt){
        if (StringUtils.isEmpty(jwt)){
            return null;
        }
        return jwtRsaHelper.parseJwt(jwt);
    }

}
