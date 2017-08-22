package edu.dubbo.common.exception;

/**
 * @author cody
 * @version V1.0
 * @create 2017/7/8 19:18
 */
public class BaseException extends RuntimeException{

    /**
     * 异常码
     */
    private int code;

    /**
     * 异常信息
     */
    private String message;

    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
