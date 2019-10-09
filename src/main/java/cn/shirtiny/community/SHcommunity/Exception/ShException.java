package cn.shirtiny.community.SHcommunity.Exception;

//sh的基本异常
public class ShException extends RuntimeException {

    private int errorCode;

    public ShException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {

        return super.getMessage();
    }


    public int getErrorCode(){
        return this.errorCode;
    }

    public ShException(String message) {
        super(message);
    }


    public ShException(String message, Throwable cause) {
        super(message, cause);
    }



    public ShException(Throwable cause) {
        super(cause);
    }

    protected ShException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
