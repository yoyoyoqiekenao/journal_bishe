package com.example.jorunal_bishe.record;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityFrame;
import com.example.jorunal_bishe.widgets.TitleView;
import com.github.chrisbanes.photoview.PhotoView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * @author : 徐无敌
 * date   : 2021/4/2011:28
 * desc   :
 */
public class PhotoViewActivity  extends ActivityFrame {
    public static final int PHOTO_REQUEST = 30;
    @ViewInject(R.id.photo_view)
    private PhotoView photoView;
    @ViewInject(R.id.tv_title)
    private TitleView titleView;
    @ViewInject(R.id.rl_edit)
    private RelativeLayout rlEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String name = intent.getStringExtra("name");
        boolean isEdit = intent.getBooleanExtra("edit", false);
        if (isEdit) {
            rlEdit.setVisibility(View.VISIBLE);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        photoView.setImageBitmap(bitmap);
        titleView.setResource(R.drawable.selector_return_btn,
                R.string.app_name);
        titleView.setCenterDetail(name);
        titleView.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Event(value = {R.id.ib_delete})
    private void OnClickView(View v) {
        setResult(Activity.RESULT_OK);
        finish();
    }
}
