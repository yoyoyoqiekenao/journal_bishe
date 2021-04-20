package com.example.jorunal_bishe.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityFrame;
import com.example.jorunal_bishe.dao.TbClassify;
import com.example.jorunal_bishe.dao.TbClassifyDao;
import com.example.jorunal_bishe.dao.TbSubclass;
import com.example.jorunal_bishe.dao.TbSubclassDao;
import com.example.jorunal_bishe.util.ClassifyType;
import com.example.jorunal_bishe.util.ToastUtil;
import com.example.jorunal_bishe.widgets.TitleView;

import org.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;

/**
 * @author : 徐无敌
 * date   : 2021/4/2016:56
 * desc   :
 */
public class NewClassifyActivity extends ActivityFrame {
    public static final int REQUEST_NEW_CLASSIFY = 20;
    @ViewInject(R.id.tv_title)
    private TitleView titleView;
    @ViewInject(R.id.et_name)
    private EditText etName;
    @ViewInject(R.id.iv_icon)
    private ImageView ivIcon;
    private int classifyType = 0;
    private int index = 0;
    private int size = 0;
    private String rightTitle;
    private TbSubclassDao subclassDao;
    private TbClassifyDao classifyDao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_classify;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        subclassDao = TbSubclassDao.getInstance();
        classifyDao = TbClassifyDao.getInstance();
        Intent intent = getIntent();
        classifyType = intent.getIntExtra("classify_type", -1);
        index = intent.getIntExtra("index", -1);
        size = intent.getIntExtra("size", -1);
        String title = intent.getStringExtra("title");
        rightTitle = intent.getStringExtra("step");
        titleView.setResource(R.drawable.selector_return_btn, R.string.today);
        titleView.setCenterDetail(title);

        titleView.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleView.setRightDetail(rightTitle);
        titleView.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightTitle.equals(getString(R.string.finish))) {
                    finishClassify();
                } else {
                    nextClassify();
                }
            }
        });
    }

    private void finishClassify() {
        String text = etName.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.getInstance().showMessage("分类不能为空");
            return;
        }
        TbSubclass subclass = new TbSubclass();
        subclass.index = index;
        subclass.name = text;
        subclassDao.saveSubclass(subclass);
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void nextClassify() {
        String text = etName.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.getInstance().showMessage("分类不能为空");
            return;
        }
        TbClassify classify = new TbClassify();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_zysr_jzsr);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        classify.imgs = baos.toByteArray();
        classify.name = text;
        classify.type = classifyType;
        String title;
        if (classifyType == ClassifyType.INCOME.value()) {
            index = TbClassifyDao.INCOME_COUNT + size - 1;
            title = getString(R.string.new_second_income_classify);
        } else {
            index = size - 1;
            title = getString(R.string.new_second_payout_classify);
        }
        classify.idx = index;
        classifyDao.saveClassify(classify);
        titleView.setCenterDetail(title);
        rightTitle = getString(R.string.finish);
        titleView.setRightDetail(rightTitle);
        etName.setText("");
    }
}
