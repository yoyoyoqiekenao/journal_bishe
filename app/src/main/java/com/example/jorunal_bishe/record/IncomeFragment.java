package com.example.jorunal_bishe.record;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.eventbus.EventBusUtil;
import com.example.jorunal_bishe.eventbus.UpdateEvent;
import com.example.jorunal_bishe.util.JDateKit;
import com.example.jorunal_bishe.util.JSystemKit;
import com.example.jorunal_bishe.util.ToastUtil;
import com.example.jorunal_bishe.widgets.BasePopupWindow;
import com.example.jorunal_bishe.widgets.ClearEditText;

import net.tsz.afinal.FinalBitmap;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2010:33
 * desc   :
 */
@ContentView(R.layout.fragment_income)
public class IncomeFragment extends FragmentBase implements RecordContract.View {
    /**
     * 调用相机拍照
     */
    public static final int CALL_CAMERA = 1;
    /**
     * 选择图片（单选）标识数据
     */
    public static final int SELECTIMAGE_ONE = 2;
    @ViewInject(R.id.cet_money)
    private ClearEditText cetMoney;
    @ViewInject(R.id.tv_type)
    private TextView tvType;
    @ViewInject(R.id.tv_date_value)
    private TextView tvDate;
    @ViewInject(R.id.cet_description)
    private ClearEditText cetDescription;
    @ViewInject(R.id.ib_camera)
    private ImageView ivCamera;
    public RecordContract.Presenter presenter;
    private BasePopupWindow popupWindow;
    private FinalBitmap finalBitmap;
    private String rootType;
    private String current_datetime; // 当前时间
    private String photoName; // 图片文件名
    private String photoSavePath; // 图片存储位置
    private boolean isSelectImg = false;


    @Override
    protected void initParams(Bundle savedInstanceState) {
        presenter = new IncomePresenter(context, this);
        presenter.initDataBase(context);
        finalBitmap = FinalBitmap.create(context);
    }

    @Override
    protected void initWidgets() {
        String date = JDateKit.dateToStr("yyyy-MM-dd HH:mm:ss EEEE", new Date());
        tvDate.setText(date);
    }

    @Override
    public void initPopupWindow() {

    }

    @Event(value = {R.id.btn_save, R.id.rl_classify, R.id.rl_date, R.id.ib_camera})
    private void OnClickView(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
            case R.id.rl_classify:
                JSystemKit.hideInputWindow(context, v);
                presenter.popupClassify();
                break;
            case R.id.rl_date:
                JSystemKit.hideInputWindow(context, v);
                presenter.openCalendar();
                break;
            case R.id.ib_camera:
                if (isSelectImg) {
                    Intent intent = new Intent();
                    intent.putExtra("url", photoSavePath);
                    intent.putExtra("name", "显示图片");
                    intent.putExtra("edit", true);
                    startActivityForResult(PhotoViewActivity.class,
                            intent, PhotoViewActivity.PHOTO_REQUEST);
                } else {
                    popupWindow.showAtLocation(this.view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showPopupClassify(List<String> rootList, List<List<String>> subList) {

    }

    @Override
    public void showCalendar() {

    }

    @Override
    public void showDateInfo(String date, String time, String week) {

    }

    @Override
    public void showClassifyText(String classify) {

    }

    @Override
    public void setPresenter(RecordContract.Presenter presenter) {

    }

    public void save() {
        String money = cetMoney.getText().toString();
        String type = tvType.getText().toString();
        String date = tvDate.getText().toString();
        String description = cetDescription.getText().toString();
        String dates[] = date.split(" ");
        if (TextUtils.isEmpty(money) || dates == null || dates.length == 0) {
            ToastUtil.getInstance().showMessage("内容不能为空");
            return;
        }
        if (TextUtils.isEmpty(rootType))
            return;
        presenter.saveJournals(money, rootType, type, description, dates, photoSavePath);

        EventBusUtil.postSync(new UpdateEvent("add", "RecordActivity", this));
        RecordActivity activity = (RecordActivity) getActivity();
        activity.finish();
    }
}
