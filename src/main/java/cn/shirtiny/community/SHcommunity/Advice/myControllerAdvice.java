package cn.shirtiny.community.SHcommunity.Advice;

import cn.shirtiny.community.SHcommunity.Exception.InvitationNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice//结合@ExceptionHandler用于全局异常的处理
public class myControllerAdvice {

    @ExceptionHandler(Exception.class)//捕获所有异常
    public String handler(Model model, Throwable e){

        return  "forward:/error?message="+e.getMessage();
    }


}
