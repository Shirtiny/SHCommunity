package cn.shirtiny.community.SHcommunity.Service.ServiceImpl;

import cn.shirtiny.community.SHcommunity.JWT.JwtRsaHelper;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class JwtServiceImpl implements IjwtService {

    @Autowired
    private JwtRsaHelper jwtRsaHelper;

    @Override
    public Map<String, Object> parseJwtByRequest(HttpServletRequest request) {
        Claims claims=null;
        String jwt = request.getHeader("Authorization");
        if (jwt==null){
            return null;
        }
        try {
            claims = jwtRsaHelper.parseJwt(jwt);
        }catch (MalformedJwtException e){
            e.printStackTrace();
        }
        return claims;
    }
}
