package cn.shirtiny.community.SHcommunity.Advice;

import cn.shirtiny.community.SHcommunity.DTO.Md_ImageUpResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Exception.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice//结合@ExceptionHandler用于全局异常的处理
public class myControllerAdvice {


    @ExceptionHandler(NoLoginException.class)
    @ResponseBody
    public ShResultDTO noLoginHandler(){
        System.out.println("未登录异常");
        return new ShResultDTO(4001,"未登录，请先登录再进行此操作");
    }

    @ExceptionHandler(CreateInvitationErrException.class)
    @ResponseBody
    public ShResultDTO createInvitationErr(Throwable e){
        System.out.println("帖子提交失败，数据库的异常，在应该是InvitationService里抛出");
        return new ShResultDTO(4502,e.getMessage());
    }

    @ExceptionHandler(Md_ImageUploadFailedException.class)
    @ResponseBody
    public Md_ImageUpResultDTO uploadFileErr(Throwable e){
        System.out.println("文件上传失败");
        //返回图片上传的失败结果，以及错误信息
        return new Md_ImageUpResultDTO(0,e.getMessage(),null);
    }


//    @ExceptionHandler(Exception.class)//捕获所有异常
//    @ResponseStatus
//    public String handler(Model model, Throwable e){
//        System.out.println("异常");
//        model.addAttribute("message",e.getMessage());
//        if (e instanceof ShException){
//            int code = ((ShException) e).getErrorCode();
//            model.addAttribute("code",code);
//        }
//        return  "myError";
//    }
}
