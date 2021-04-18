package com.example.jorunal_bishe.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jorunal_bishe.eventbus.Event;
import com.example.jorunal_bishe.eventbus.EventBusUtil;
import com.example.jorunal_bishe.util.ActivityStackManager;
import com.example.jorunal_bishe.util.AppPref;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;

public  abstract class ActivityBase  extends AppCompatActivity {
    protected Context context;
    protected AppPref appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.getInstance().pushActivity(this);
        setContentView(getLayoutId());
        x.view().inject(this);
        context = this;
        appPref = AppPref.getInstance();
        initParams(savedInstanceState);
        EventBusUtil.register(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initParams(Bundle savedInstanceState);

    /**
     * 接收消息函数在主线程，且为粘性事件
     * From FramgmentOne
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event response) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.getInstance().popActivity(this);
        EventBusUtil.unregister(this);
    }

    protected void startActivity(Class<?> cls, Intent intent) {
        if (intent == null) {
            intent = new Intent(context, cls);
        } else {
            intent.setClass(context, cls);
        }
        startActivity(intent);
    }

    protected void startActivityForResult(Class<?> cls, Intent intent, int request) {
        if (intent == null) {
            intent = new Intent(context, cls);
        } else {
            intent.setClass(context, cls);
        }
        startActivityForResult(intent, request);
    }
}
