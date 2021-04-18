package com.example.jorunal_bishe.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.jorunal_bishe.eventbus.Event;
import com.example.jorunal_bishe.eventbus.EventBusUtil;
import com.example.jorunal_bishe.util.AppPref;
import com.example.jorunal_bishe.util.DialogMaker;
import com.example.jorunal_bishe.util.JLogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;

public abstract class FragmentBase extends Fragment {

    protected Activity context;
    protected Dialog dialog;
    protected View view;
    protected AppPref appPref;
    private boolean injected = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        appPref = AppPref.getInstance();
        initParams(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        injected = true;
        view = x.view().inject(this, inflater, container);
        initWidgets();
        EventBusUtil.register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    /**
     * 接收消息函数在主线程
     *
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event response) {

    }

    protected void initParams(Bundle savedInstanceState) {

    }

    protected abstract void initWidgets();

    /**
     * pop dialog
     *
     * @param title
     * @param msg
     * @param btns
     * @param isCanCancelabel
     * @param isDismissAfterClickBtn
     * @return
     */
    public Dialog showAlertDialog(String title, String msg, String[] btns,
                                  boolean isCanCancelabel, final boolean isDismissAfterClickBtn,
                                  Object tag) {
        if (null == dialog || !dialog.isShowing()) {
            JLogUtils.getInstance().i("show alert dialog");
            DialogMaker.showCommonAlertDialog(context, title, msg, btns, null,
                    isCanCancelabel, isDismissAfterClickBtn, tag);
        }
        return dialog;
    }

    public Dialog showWaitDialog(String msg, boolean isCanCancelabel, Object tag) {
        if (null == dialog || !dialog.isShowing()) {
            JLogUtils.getInstance().i("show wait dialog");
            dialog = DialogMaker.showCommonWaitDialog(context, msg, null,
                    isCanCancelabel, tag);
        }
        return dialog;
    }

    /**
     * close dialog
     */
    public void dismissDialog() {
        if (null != dialog && dialog.isShowing()) {
            JLogUtils.getInstance().i("dismiss dialog");
            dialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissDialog();
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
