package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

//sh的基本异常
public class ShException extends RuntimeException {

    private int errorCode;

    //枚举类
    private ShErrorCode shErrorCode;

    public ShException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public ShErrorCode getShErrorCode() {
        return shErrorCode;
    }

    public void setShErrorCode(ShErrorCode shErrorCode) {
        this.shErrorCode = shErrorCode;
    }

    public ShException(ShErrorCode shErrorCode ) {
        super();
        this.shErrorCode = shErrorCode;
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
