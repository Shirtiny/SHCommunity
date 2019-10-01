package cn.shirtiny.community.SHcommunity.Advice;

import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Exception.NoLoginException;
import cn.shirtiny.community.SHcommunity.Exception.ShException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice//结合@ExceptionHandler用于全局异常的处理
public class myControllerAdvice {

    @ExceptionHandler(Exception.class)//捕获所有异常
    @ResponseStatus
    public String handler(Model model, Throwable e){
        System.out.println("异常");
        model.addAttribute("message",e.getMessage());
        if (e instanceof ShException){
            int code = ((ShException) e).getErrorCode();
            model.addAttribute("code",code);
        }
        return  "myError";
    }

    @ExceptionHandler(NoLoginException.class)
    @ResponseBody
    public ShResultDTO noLoginHandler(){
        System.out.println("未登录异常");
        return new ShResultDTO(4001,"未登录，请先登录再进行此操作");
    }


}
