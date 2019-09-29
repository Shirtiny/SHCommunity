package cn.shirtiny.community.SHcommunity.Exception;

public class InvitationNotFoundException extends RuntimeException {

    private int errorCode;

    public InvitationNotFoundException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public InvitationNotFoundException(String message) {
        super(message);
    }

    public InvitationNotFoundException() {
        super();
    }

    public InvitationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public InvitationNotFoundException(Throwable cause) {
        super(cause);
    }

    protected InvitationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
