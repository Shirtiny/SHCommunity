package cn.shirtiny.community.SHcommunity.Exception;

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

    protected CreateInvitationErrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
