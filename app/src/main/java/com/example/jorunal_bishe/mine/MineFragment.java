package com.example.jorunal_bishe.mine;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.widgets.SettingItemView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:37
 * desc   :
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends FragmentBase implements View.OnClickListener, PopupWindow.OnDismissListener {

    @BindView(R.id.item_update_pwd)
    SettingItemView item_update_pwd;
    @BindView(R.id.item_update)
    SettingItemView item_update;
    @BindView(R.id.item_about)
    SettingItemView item_about;
    @BindView(R.id.rootView)
    LinearLayout rootView;

    private PopupWindow mPop;

    @Override
    protected void initWidgets() {
        ButterKnife.bind(this, view);

        item_update_pwd.setOnClickListener(this);
        item_update.setOnClickListener(this);
        item_about.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_update_pwd:
                startActivity(new Intent(getContext(), ModifyClassifyActivity.class));
                break;
            case R.id.item_update:
                Toast.makeText(getContext(), "版本更新", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_about:
                backgroundAlpha(0.5f);
                showPop();
                break;
            default:
        }
    }

    /**
     * 设置添加屏幕的背景透明度 * * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    private void showPop() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_aboput, null);
        mPop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPop.setAnimationStyle(R.style.popupwindow_anim_style);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setOutsideTouchable(true);
        mPop.setOnDismissListener(this);
        mPop.showAtLocation(rootView, Gravity.CENTER, 0, 0);

    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);

    }
}
