package com.example.jorunal_bishe.base;

import android.app.Dialog;
import android.os.Bundle;

import com.example.jorunal_bishe.util.DialogMaker;
import com.example.jorunal_bishe.util.JLogUtils;

/**
 * @author : 徐无敌
 * date   : 2021/4/209:50
 * desc   :
 */
public abstract class ActivityFrame extends ActivityBase implements DialogMaker.DialogCallBack {
    protected Dialog dialog;

    @Override
    public void onBtnClicked(Dialog dialog, int position, Object tag) {
        JLogUtils.getInstance().i("position = " + position);

    }

    @Override
    public void onCancelDialog(Dialog dialog, Object tag) {

    }

    /**
     * Pop-up dialog
     *
     * @param title                  标题
     * @param msg                    内容
     * @param btns                   按钮数组
     * @param isCanCancel            是否可以取消
     * @param isDismissAfterClickBtn 点击后是否关闭提示框
     * @return
     */
    public Dialog showAlertDialog(String title, String msg, String[] btns,
                                  boolean isCanCancel, final boolean isDismissAfterClickBtn,
                                  Object tag) {
        if (null == dialog || !dialog.isShowing()) {
            dialog = DialogMaker.showCommonAlertDialog(this, title, msg, btns, this,
                    isCanCancel, isDismissAfterClickBtn, tag);
        }
        return dialog;
    }

    public Dialog showWaitDialog(String msg, boolean isCanCancel, Object tag) {
        if (null == dialog || !dialog.isShowing()) {
            dialog = DialogMaker.showCommonWaitDialog(this, msg, this,
                    isCanCancel, tag);
        }
        return dialog;
    }

    /**
     * close dialog
     */
    protected void dismissDialog() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissDialog();
        super.onDestroy();
    }
}
