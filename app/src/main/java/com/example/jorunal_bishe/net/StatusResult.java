package com.example.jorunal_bishe.net;

/**
 * @author : 徐无敌
 * date   : 2021/4/2215:03
 * desc   :
 */
public class StatusResult {
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
