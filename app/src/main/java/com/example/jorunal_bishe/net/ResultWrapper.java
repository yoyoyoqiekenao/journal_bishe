package com.example.jorunal_bishe.net;

/**
 * @author : 徐无敌
 * date   : 2021/4/2215:03
 * desc   :
 */
public class ResultWrapper <T> extends StatusResult {
    T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
