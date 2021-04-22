package com.example.jorunal_bishe.net;

/**
 * @author : 徐无敌
 * date   : 2021/4/2214:52
 * desc   :
 */
public interface Callback<T> {
    void onSuccess(T t);

    void onFailure(int code, String message);
}
