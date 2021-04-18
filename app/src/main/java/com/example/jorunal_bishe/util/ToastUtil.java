package com.example.jorunal_bishe.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
    private static final String TAG = "ToastUtil";
    private static ToastUtil instance;
    private Handler handler = new Handler();
    private Toast toast = null;
    private Object synObj = new byte[0];
    private Context context;

    private ToastUtil() {
    }

    public static ToastUtil getInstance() {
        if (instance == null) {
            instance = new ToastUtil();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public void showMessage(final int resId) {
        String msg = context.getString(resId);
        showMessage(msg, Toast.LENGTH_LONG);
    }

    public void showMessage(final String msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    public void showMessage(final String msg,
                            final int len) {
        new Thread(new Runnable() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (synObj) {
                            if (toast == null) {
                                toast = Toast.makeText(context, msg, len);
                            }
                            toast.setText(msg);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.setDuration(len);
                            toast.show();
                        }
                    }
                });
            }
        }).start();
    }

    public void cancelCurrentToast() {
        if (toast != null) {
            Log.i(TAG, "[ToastUtils]: toast is Hided");
            toast.cancel();
        } else {
            Log.i(TAG, "[ToastUtils]: toast is null");
        }
    }
}
