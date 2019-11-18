package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;
//创建用户失败
public class CreateUserFailedException extends ShException{
    public CreateUserFailedException(String message, int errorCode) {
        super(message, errorCode);
    }

    public CreateUserFailedException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    public CreateUserFailedException(String message) {
        super(message);
    }

    public CreateUserFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateUserFailedException(Throwable cause) {
        super(cause);
    }

    protected CreateUserFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
