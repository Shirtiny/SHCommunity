package cn.shirtiny.community.SHcommunity.Exception;

//未登录
public class NoLoginException extends ShException {


    public NoLoginException() {
        super("未登录，请登录",4001);
    }

    public NoLoginException(String message, int errorCode) {
        super(message, errorCode);
    }

    public NoLoginException(String message) {
        super(message);
    }

    public NoLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoLoginException(Throwable cause) {
        super(cause);
    }

    protected NoLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
