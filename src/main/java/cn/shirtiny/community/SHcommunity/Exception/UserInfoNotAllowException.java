package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

public class UserInfoNotAllowException extends ShException {
    public UserInfoNotAllowException(String message, int errorCode) {
        super(message, errorCode);
    }

    public UserInfoNotAllowException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    public UserInfoNotAllowException(String message) {
        super(message);
    }

    public UserInfoNotAllowException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInfoNotAllowException(Throwable cause) {
        super(cause);
    }

    protected UserInfoNotAllowException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
