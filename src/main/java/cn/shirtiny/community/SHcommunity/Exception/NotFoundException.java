package cn.shirtiny.community.SHcommunity.Exception;

import cn.shirtiny.community.SHcommunity.Enum.ShErrorCode;

import java.io.PrintStream;
import java.io.PrintWriter;

//找不到资源
public class NotFoundException extends ShException {
    public NotFoundException(String message, int errorCode) {
        super(message, errorCode);
    }

    @Override
    public void setErrorCode(int errorCode) {
        super.setErrorCode(errorCode);
    }

    @Override
    public ShErrorCode getShErrorCode() {
        return super.getShErrorCode();
    }

    @Override
    public void setShErrorCode(ShErrorCode shErrorCode) {
        super.setShErrorCode(shErrorCode);
    }

    public NotFoundException(ShErrorCode shErrorCode) {
        super(shErrorCode);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        return super.initCause(cause);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        super.setStackTrace(stackTrace);
    }

    @Override
    public int getErrorCode() {
        return super.getErrorCode();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    protected NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
