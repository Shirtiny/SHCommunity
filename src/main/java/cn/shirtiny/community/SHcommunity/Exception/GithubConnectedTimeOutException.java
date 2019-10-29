package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

//github连接超时异常
public class GithubConnectedTimeOutException extends ShException {
    public GithubConnectedTimeOutException(String message, int errorCode) {
        super(message, errorCode);
    }

    public GithubConnectedTimeOutException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    public GithubConnectedTimeOutException(String message) {
        super(message);
    }

    public GithubConnectedTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public GithubConnectedTimeOutException(Throwable cause) {
        super(cause);
    }

    protected GithubConnectedTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
