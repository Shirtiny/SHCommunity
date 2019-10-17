package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

//数据库帖子插入失败
public class CreateInvitationErrException extends ShException {
    public CreateInvitationErrException(String message, int errorCode) {
        super(message, errorCode);
    }

    public CreateInvitationErrException(String message) {
        super(message);
    }

    public CreateInvitationErrException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateInvitationErrException(Throwable cause) {
        super(cause);
    }

    public CreateInvitationErrException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    protected CreateInvitationErrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
