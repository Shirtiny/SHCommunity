package cn.shirtiny.community.SHcommunity.Service;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IloginService {

    String getRedirectFromCookie(HttpServletRequest request, HttpServletResponse response);

    ShResultDTO<String,Object> userLoginByPWD(User user, HttpServletResponse response);

    //将用户封装为jwt并设置cookie
    String userLogin(User user,HttpServletResponse response);
}
