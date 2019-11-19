package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

//jwt无效异常
public class JwtInvalidException extends ShException {
    public JwtInvalidException(String message, int errorCode) {
        super(message, errorCode);
    }

    public JwtInvalidException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    public JwtInvalidException(String message) {
        super(message);
    }

    public JwtInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtInvalidException(Throwable cause) {
        super(cause);
    }

    protected JwtInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
