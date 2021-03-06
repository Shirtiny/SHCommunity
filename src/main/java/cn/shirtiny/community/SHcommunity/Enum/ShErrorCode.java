package cn.shirtiny.community.SHcommunity.Enum;

import cn.shirtiny.community.SHcommunity.Exception.ShException;

public enum ShErrorCode {

    Github_Connect_Error(4503, "github连接失败"),

    NoLogin_Error(4001, "未登录，请先登录再进行此操作"),

    Login_Failed_Error(4002, "登陆失败"),

    Jwt_Invalid_Error(4003, "格式无效的Jwt或解析失败"),

    User_Already_Exsit_Error(5004, "用户名已存在"),

    User_Info_NotAllowed_Error(5005, "用户信息不符合规范"),

    Create_User_Failed_Error(5006, "用户数据入库失败"),

    NotFound_Error(4004, "找不到请求的对应资源"),

    BannerShape_Args_Error(4501, "bannerShape参数错误，必须是lg/md/sm/cr之一"),

    Create_Invitation_Failed_Error(4502, "帖子创建失败，数据库插入失败"),

    Mail_Send_Failed_Error(4503, "邮件发送失败"),

    Md_ImageUpload_Failed_Error(5002, "md图片上传失败"),

    File_Upload_Failed_Error(5001, "文件上传失败");

    private Integer code;
    private String message;

    ShErrorCode() {

    }

    ShErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
