package cn.shirtiny.community.SHcommunity.Advice;

import cn.shirtiny.community.SHcommunity.DTO.Md_ImageUpResultDTO;
import cn.shirtiny.community.SHcommunity.DTO.ShResultDTO;
import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;
import cn.shirtiny.community.SHcommunity.Exception.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice//结合@ExceptionHandler用于全局异常的处理
//注入日志
@Slf4j
public class myControllerAdvice {


    @ExceptionHandler(GithubConnectedTimeOutException.class)
    @ResponseBody
    public ShResultDTO GithubConnectedTimeOutHandler(@NotNull GithubConnectedTimeOutException e) {
        e.printStackTrace();
        //github连接超时
        log.error(ShErrorCode.Github_Connect_Error.getMessage()+",{}", ShErrorCode.Github_Connect_Error.getCode(),e);
        e.printStackTrace();
        return new ShResultDTO(ShErrorCode.Github_Connect_Error.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoLoginException.class)
    @ResponseBody
    public ShResultDTO noLoginHandler(@NotNull NoLoginException e) {
        e.printStackTrace();
        //用户未登录
        log.error(ShErrorCode.NoLogin_Error.getMessage()+",{}", e.getShErrorCode(),e);
        return new ShResultDTO(ShErrorCode.NoLogin_Error.getCode(), "未登录，请先登录再进行此操作");
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseBody
    public ShResultDTO loginFailedError(@NotNull LoginFailedException e){
        e.printStackTrace();
        //登录失败
        log.error(ShErrorCode.Login_Failed_Error.getMessage()+",{}",ShErrorCode.Login_Failed_Error.getCode(),e);
        return new ShResultDTO(ShErrorCode.Login_Failed_Error.getCode(),e.getMessage());
    }

    @ExceptionHandler(JwtInvalidException.class)
    @ResponseBody
    public ShResultDTO loginFailedError(@NotNull JwtInvalidException e){
        e.printStackTrace();
        //Jwt格式无效
        log.error(ShErrorCode.Jwt_Invalid_Error.getMessage()+",{}",ShErrorCode.Jwt_Invalid_Error.getCode(),e);
        //data里放一个空的user
        Map<String,Object> data = new HashMap<>();
        data.put("user",null);
        return new ShResultDTO<>(ShErrorCode.Jwt_Invalid_Error.getCode(),e.getMessage(),data,e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ShResultDTO NotFoundHandler(@NotNull NotFoundException e) {
        e.printStackTrace();
        //找不到请求的资源
        log.error(ShErrorCode.NotFound_Error.getMessage()+",{}",ShErrorCode.NotFound_Error.getCode(),e);
        return new ShResultDTO(ShErrorCode.NotFound_Error.getCode(), e.getMessage());
    }

    @ExceptionHandler(CreateInvitationErrException.class)
    @ResponseBody
    public ShResultDTO createInvitationErr(@NotNull CreateInvitationErrException e) {
        e.printStackTrace();
        //帖子创建失败
        log.error(ShErrorCode.Create_Invitation_Failed_Error.getMessage(),e);
        return new ShResultDTO(ShErrorCode.Create_Invitation_Failed_Error.getCode(), e.getMessage());
    }

    @ExceptionHandler(Md_ImageUploadFailedException.class)
    @ResponseBody
    public Md_ImageUpResultDTO uploadFileErr(@NotNull Throwable e) {
        e.printStackTrace();
        log.error("文件上传失败", e);
        //返回图片上传的失败结果，以及错误信息
        return new Md_ImageUpResultDTO(0, e.getMessage(), null);
    }

    @ExceptionHandler(UserAlreadyExsitException.class)
    @ResponseBody
    public ShResultDTO userAlreadExsitErr(@NotNull UserAlreadyExsitException e) {
        e.printStackTrace();
        //要注册的用户名已经存在
        return new ShResultDTO(ShErrorCode.User_Already_Exsit_Error.getCode(), e.getMessage());
    }

    @ExceptionHandler(UserInfoNotAllowException.class)
    @ResponseBody
    public ShResultDTO userInfoNotAllowedErr(@NotNull UserInfoNotAllowException e) {
        e.printStackTrace();
        //用户信息不规范
        return new ShResultDTO(ShErrorCode.User_Info_NotAllowed_Error.getCode(), e.getMessage());
    }

    @ExceptionHandler(CreateUserFailedException.class)
    @ResponseBody
    public ShResultDTO createUserFailedErr(@NotNull CreateUserFailedException e) {
        e.printStackTrace();
        //用户数据入库失败
        log.error(ShErrorCode.Create_User_Failed_Error.getMessage(), e);
        return new ShResultDTO(ShErrorCode.Create_User_Failed_Error.getCode(), e.getMessage());
    }

    @ExceptionHandler(MailSendFailedException.class)
    @ResponseBody
    public ShResultDTO mailSendFailedErr(@NotNull MailSendFailedException e) {
        e.printStackTrace();
        //邮件发送失败
        log.error(ShErrorCode.Mail_Send_Failed_Error.getMessage(), e);
        return new ShResultDTO(ShErrorCode.Mail_Send_Failed_Error.getCode(), e.getMessage());
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
