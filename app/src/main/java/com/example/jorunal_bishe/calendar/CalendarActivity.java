package com.example.jorunal_bishe.calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.core.content.ContextCompat;

import com.example.jorunal_bishe.LocalApplication;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityFrame;
import com.example.jorunal_bishe.been.JCalendar;
import com.example.jorunal_bishe.widgets.BorderText;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2010:42
 * desc   :
 */
public class CalendarActivity extends ActivityFrame implements GestureDetector.OnGestureListener, CalendarContract.View {

    public final static int REQUEST_CALENDAR = 1;
    @ViewInject(R.id.flipper)
    private ViewFlipper flipper = null;
    @ViewInject(R.id.bt_top)
    private BorderText topText = null;

    private GestureDetector gestureDetector = null;
    private CalendarAdapter calendarAdapter = null;
    private GridView gridView = null;
    private Drawable draw = null;
    private CalendarContract.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        presenter = new CalendarPresenter(this);
        gestureDetector = new GestureDetector(context, this);
        flipper.removeAllViews();
        initGridView(0);
    }

    private void initGridView(int index){
        presenter.addGridView();
        calendarAdapter = new CalendarAdapter(this, new ArrayList<JCalendar>(0));
        gridView.setAdapter(calendarAdapter);
        flipper.addView(gridView, index);
        presenter.loadCalendar();
        presenter.initTopText();
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        int gvFlag = 0;         //每次添加gridview到viewflipper中时给的标记
        if (e1.getX() - e2.getX() > 120) {
            //像左滑动
            presenter.moveToLeft();
            gvFlag++;
            initGridView(gvFlag);

            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            this.flipper.showNext();
            flipper.removeViewAt(0);
            return true;
        } else if (e1.getX() - e2.getX() < -120) {
            //向右滑动
            presenter.moveToRight();
            gvFlag++;
            initGridView(gvFlag);

            this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
            this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
            this.flipper.showPrevious();
            flipper.removeViewAt(0);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

    @Override
    public void setPresenter(CalendarContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        //取得屏幕的宽度和高度
        int Width = LocalApplication.getInstance().screenW;
        int Height = LocalApplication.getInstance().screenH;

        gridView = new GridView(this);
        gridView.setNumColumns(7);
        gridView.setColumnWidth(46);
        gridView.setLayoutParams(params);

        if (Width == 480 && Height == 800) {
            gridView.setColumnWidth(69);
        }
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT)); // 去除gridView边框
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setBackgroundResource(R.mipmap.gridview_bk);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            //将gridview中的触摸事件回传给gestureDetector
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //gridView中的每一个item的点击事件
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                presenter.clickGridViewItem(position);
            }
        });
    }

    @Override
    public void showCalendar(List<JCalendar> calendar) {
        calendarAdapter.refreshDatas(calendar);
    }

    @Override
    public void showTopText(String text) {
        draw = ContextCompat.getDrawable(context, R.mipmap.top_day);
        topText.setBackgroundDrawable(draw);
        topText.setText(text);
        topText.setTextColor(Color.BLACK);
        topText.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void close(Intent intent) {
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
