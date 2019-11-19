package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.Utils.JWT.JwtRsaHelper;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class JwtServiceImpl implements IjwtService {

    @Autowired
    private JwtRsaHelper jwtRsaHelper;

    @Override
    public Map<String, Object> parseJwtByRequest(@NotNull HttpServletRequest request) {
        Claims claims=null;
        String jwt = request.getHeader("Authorization");
        if (jwt==null){
            return null;
        }
        try {
            claims = jwtRsaHelper.parseJwt(jwt);
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }
}
