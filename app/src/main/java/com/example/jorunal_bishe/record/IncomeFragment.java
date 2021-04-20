package com.example.jorunal_bishe.record;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.eventbus.EventBusUtil;
import com.example.jorunal_bishe.eventbus.UpdateEvent;
import com.example.jorunal_bishe.util.JDateKit;
import com.example.jorunal_bishe.util.JFileKit;
import com.example.jorunal_bishe.util.JLogUtils;
import com.example.jorunal_bishe.util.JSystemKit;
import com.example.jorunal_bishe.util.ToastUtil;
import com.example.jorunal_bishe.widgets.BasePopupWindow;
import com.example.jorunal_bishe.widgets.ClearEditText;

import net.tsz.afinal.FinalBitmap;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.result(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CALL_CAMERA) {
                if (data == null) {
                    isSelectImg = true;
                    JLogUtils.getInstance().e("photoSavePath = " + photoSavePath);
                    finalBitmap.display(ivCamera, photoSavePath);
                }
            } else if (requestCode == SELECTIMAGE_ONE) {
                if (data != null) {
                    isSelectImg = true;
                    // 获取相册选择结果（保存路径）
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    photoSavePath = cursor.getString(columnIndex);
                    finalBitmap.display(ivCamera, photoSavePath);
                }
            } else if (requestCode == PhotoViewActivity.PHOTO_REQUEST) {
                isSelectImg = false;
                ivCamera.setImageResource(R.mipmap.camera_btn);
            }
        }
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

    @Override
    public void setPresenter(RecordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initPopupWindow() {
        popupWindow = new BasePopupWindow(context);
        popupWindow.setAnimationStyle(R.style.style_bottom_window_animation);
        View view = LayoutInflater.from(context).
                inflate(R.layout.camera_popupwindow, new LinearLayout(context), false);
        view.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                callCameraTakePhoto();
            }
        });
        view.findViewById(R.id.btn_photo_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                // 图片单选，直接跳转至系统图片库
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECTIMAGE_ONE);
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
    }

    /**
     * 调用相机拍照并存储照片
     */
    private void callCameraTakePhoto() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINESE);
        current_datetime = sdf.format(currentDate); // 初始化当前时间值
//		路径规则：SD卡路径（内部存储）/packageName/no_upload_media/yyyyMMddHHmmss.jpg
        String path = JFileKit.getDiskCacheDir(context) + "/upload_media";
        photoName = current_datetime + ".jpg"; // 初始化图片文件名
        photoSavePath = path + File.separator + photoName; // 初始化文件夹位置
        JLogUtils.getInstance().e("path", photoSavePath);

        init_pic_dir(path); // 查询并创建文件夹

        // 启动相机并拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoSavePath)));
        startActivityForResult(intent, CALL_CAMERA);
    }

    /**
     * 查询指定路径文件是否存在，若不存在就直接创建
     *
     * @param path 指定路径
     */
    private void init_pic_dir(String path) {
        File d = new File(path + File.separator);
        if (!d.exists()) {
            d.mkdirs();
        }
    }

    @Override
    public void showPopupClassify(final List<String> rootList, final List<List<String>> subList) {
        OptionsPickerView pickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                rootType = rootList.get(options1);
                String type = subList.get(options1).get(options2);
                tvType.setText(type);
            }
        }).setTitleText("选择收入类型")
                .setTitleSize(20)
                .setContentTextSize(18)
                .build();
        pickerView.setPicker(rootList, subList);
        pickerView.show();
        rootType = null;
    }

    @Override
    public void showCalendar() {
        TimePickerView pickerView = new TimePickerView.Builder(context, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String str = JDateKit.dateToStr("yyyy-MM-dd HH:mm:ss EEEE", date);
                tvDate.setText(str);
            }
        }).setTitleText("选择时间")
                .setTitleSize(20)
                .setContentSize(18)
                .isCyclic(true)
                .build();
        pickerView.setDate(Calendar.getInstance());
        pickerView.show();
    }

    @Override
    public void showDateInfo(String date, String time, String week) {
        //tvDate.setText(date + " " + time + " " + week);
    }

    @Override
    public void showClassifyText(String classify) {
        tvType.setText(classify);
    }


}
