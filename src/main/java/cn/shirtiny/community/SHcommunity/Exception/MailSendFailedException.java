package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

//邮件发送失败异常
public class MailSendFailedException extends ShException {
    public MailSendFailedException(String message, int errorCode) {
        super(message, errorCode);
    }

    public MailSendFailedException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    public MailSendFailedException(String message) {
        super(message);
    }

    public MailSendFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSendFailedException(Throwable cause) {
        super(cause);
    }

    protected MailSendFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
