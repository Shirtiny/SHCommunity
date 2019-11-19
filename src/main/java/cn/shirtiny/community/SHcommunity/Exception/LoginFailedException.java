package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

//登录失败
public class LoginFailedException extends ShException {
    public LoginFailedException(String message, int errorCode) {
        super(message, errorCode);
    }

    public LoginFailedException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    protected LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
