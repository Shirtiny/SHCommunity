package cn.shirtiny.community.SHcommunity.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IloginService {

    String getRedirectFromCookie(HttpServletRequest request, HttpServletResponse response);

}
