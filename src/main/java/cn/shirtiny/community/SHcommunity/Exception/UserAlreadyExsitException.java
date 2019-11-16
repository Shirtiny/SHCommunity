package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

public class UserAlreadyExsitException extends ShException {
    public UserAlreadyExsitException(String message, int errorCode) {
        super(message, errorCode);
    }

    public UserAlreadyExsitException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    public UserAlreadyExsitException(String message) {
        super(message);
    }

    public UserAlreadyExsitException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExsitException(Throwable cause) {
        super(cause);
    }

    protected UserAlreadyExsitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
