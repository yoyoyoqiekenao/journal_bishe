package com.example.jorunal_bishe.consume;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityFrame;
import com.example.jorunal_bishe.dao.TbJournal;
import com.example.jorunal_bishe.edit.EditActivity;
import com.example.jorunal_bishe.eventbus.UpdateEvent;
import com.example.jorunal_bishe.record.PhotoViewActivity;
import com.example.jorunal_bishe.util.JDateKit;
import com.example.jorunal_bishe.util.JLogUtils;
import com.example.jorunal_bishe.widgets.ScrollChildSwipeRefreshLayout;
import com.example.jorunal_bishe.widgets.TitleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:16
 * desc   :
 */
public class ConsumeDetailActivity extends ActivityFrame
        implements ConsumeContract.View,
        BeforeAdapter.OnImgClickListener,
        BeforeAdapter.OnItemClickListener {

    @ViewInject(R.id.titleview)
    private TitleView titleView;
    @ViewInject(R.id.tv_surplus_value)
    private TextView tvSurplus;
    @ViewInject(R.id.tv_income_value)
    private TextView tvIncome;
    @ViewInject(R.id.tv_payout_value)
    private TextView tvPayout;
    @ViewInject(R.id.rv_list)
    private RecyclerView rvList;
    @ViewInject(R.id.refresh_scroll)
    private ScrollChildSwipeRefreshLayout refreshScrollView;
    private ConsumeContract.Presenter presenter;
    private BeforeAdapter beforeAdapter;
    private List<TbJournal> journals = new ArrayList<>();
    private String title;
    //显示的类型：本周、本月、本年
    private int index;
    private int loadTime = 0;
    private String start = "";
    private String end = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_consume_detail;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        index = getIntent().getIntExtra("index", -1);
        switch (index) {
            case 0:
                title = getString(R.string.today) + JDateKit.dateToStr("MM-dd", new Date());
                break;
            case 1:
                start = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfWeek());
                end = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfWeek());
                String startDate = JDateKit.dateToStr("MM.dd", JDateKit.getFirstDayOfWeek());
                String endDate = JDateKit.dateToStr("MM.dd", JDateKit.getLastDayOfWeek());
                title = startDate + "-" + endDate;
                break;
            case 2:
                start = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfMonth());
                end = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfMonth());
                title = JDateKit.dateToStr("yyyy年MM月", new Date());
                break;
            case 3:
                start = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfYear());
                end = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfYear());
                title = JDateKit.dateToStr("yyyy年", new Date());
                break;
            case 4:
                start = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.setDate(2000, 2, 2));
                end = JDateKit.dateToStr("yyyy-MM-dd", new Date());
                title = context.getString(R.string.this_all_bill);
                break;
        }
        beforeAdapter = new BeforeAdapter(context, journals);
        beforeAdapter.setOnImgClickListener(this);
        beforeAdapter.setOnItemClickListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        rvList.setAdapter(beforeAdapter);
        presenter = new ConsumePresenter(context, this);
        presenter.loadJournals(start, end);
        titleView.setLeftDetail(title);
        titleView.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshScrollView.setColorSchemeColors(
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.colorPrimaryDark)
        );
        refreshScrollView.setScrollUpChild(rvList);
        //refreshScrollView.canChildScrollUp();
        refreshScrollView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                //mPresenter.loadTasks(false);
                JLogUtils.getInstance().i("onRefresh...");
                refreshScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadTime++;
                        switch (index) {
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                start = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfYear());
                                end = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfYear());
                                title = JDateKit.dateToStr("yyyy年", new Date());
                                break;
                            default:
                                break;
                        }
                        presenter.loadJournals(start, end);
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
                        refreshScrollView.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public void setPresenter(ConsumeContract.Presenter presenter) {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.loadJournals(start, end);
    }

    @Override
    public void showJournals(List<TbJournal> journals) {
        this.journals.clear();
        this.journals.addAll(journals);
        JLogUtils.getInstance().e("showJournals size = " + this.journals.size());
        beforeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSurplus(String surplus) {
        tvSurplus.setText(surplus);
    }

    @Override
    public void showIncome(String income) {
        tvIncome.setText(income);
    }

    @Override
    public void showPayOut(String payout) {
        tvPayout.setText(payout);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateEvent response) {
        if (response.from.equals("EditActivity")) {
            if (response.event.equals("delete")) {
//				presenter.loadTodayJournals();
//				presenter.loadJournals();
            } else if (response.event.equals("update")) {
//				presenter.loadTodayJournals();
//				presenter.loadJournals();
            }
        } else if (response.from.equals("RecordActivity")) {
            if (response.event.equals("add")) {
//				presenter.loadTodayJournals();
//				presenter.loadJournals();
            }
        }
    }

    @Override
    public void onClick(String path) {
        Intent intent = new Intent();
        intent.putExtra("url", path);
        intent.putExtra("name", "显示图片");
        startActivity(PhotoViewActivity.class, intent);
    }

    @Override
    public void onItemClick(TbJournal item) {
        Intent intent = new Intent();
        intent.putExtra(TbJournal.ID, item.id);
        startActivity(EditActivity.class, intent);
    }
}
