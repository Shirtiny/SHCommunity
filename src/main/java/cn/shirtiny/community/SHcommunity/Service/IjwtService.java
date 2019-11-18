package cn.shirtiny.community.SHcommunity.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IjwtService {
    Map<String,Object> parseJwtByRequest(HttpServletRequest request);
}
