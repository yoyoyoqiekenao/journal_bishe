package com.example.jorunal_bishe.consume;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityFrame;
import com.example.jorunal_bishe.dao.TbJournal;
import com.example.jorunal_bishe.edit.EditActivity;
import com.example.jorunal_bishe.eventbus.UpdateEvent;
import com.example.jorunal_bishe.record.PhotoViewActivity;
import com.example.jorunal_bishe.record.RecordActivity;
import com.example.jorunal_bishe.util.JDataKit;
import com.example.jorunal_bishe.util.JDateKit;
import com.example.jorunal_bishe.widgets.TitleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2014:55
 * desc   :
 */
public class TodayDetailActivity extends ActivityFrame implements TodayContract.View,
        BeforeAdapter.OnImgClickListener,
        BeforeAdapter.OnItemClickListener {

    @ViewInject(R.id.titleview)
    private TitleView titleView;
    @ViewInject(R.id.rl_remember)
    private RelativeLayout rlRemember;
    @ViewInject(R.id.tv_surplus_value)
    private TextView tvSurplus;
    @ViewInject(R.id.tv_income_value)
    private TextView tvIncome;
    @ViewInject(R.id.tv_payout_value)
    private TextView tvPayout;
    @ViewInject(R.id.rv_list)
    private RecyclerView rvList;
    private BeforeAdapter beforeAdapter;
    private List<TbJournal> journals = new ArrayList<>();
    private TodayContract.Presenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_today_detail;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        presenter = new TodayPresenter(context, this);
        String title = getString(R.string.today) +
                JDateKit.dateToStr("MM月dd日", new Date());
        titleView.setLeftDetail(title);
        titleView.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //RecyclerView一定要加这句才能显示出来
        rvList.setLayoutManager(new LinearLayoutManager(context));
        beforeAdapter = new BeforeAdapter(context, journals);
        beforeAdapter.setOnImgClickListener(this);
        beforeAdapter.setOnItemClickListener(this);
        rvList.setAdapter(beforeAdapter);
        presenter.loadTodayJournals();
        presenter.loadJournals();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateEvent response) {
        if (response.from.equals("EditActivity")) {
            if (response.event.equals("delete")) {
                presenter.loadTodayJournals();
                presenter.loadJournals();
            } else if (response.event.equals("update")) {
                presenter.loadTodayJournals();
                presenter.loadJournals();
            }
        } else if (response.from.equals("RecordActivity")) {
            if (response.event.equals("add")) {
                presenter.loadTodayJournals();
                presenter.loadJournals();
            }
        }
    }

    @Event(value = {R.id.rl_remember})
    private void onClickView(View v) {
        switch (v.getId()) {
            case R.id.rl_remember:
                presenter.addNewJournal();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.loadJournals();
        presenter.loadTodayJournals();
    }

    @Override
    public void showJournals(List<TbJournal> incomes, List<TbJournal> payOuts) {
        journals.clear();
        journals.addAll(incomes);
        journals.addAll(payOuts);
        Log.d("xuwudi", "size===" + journals.size());
        Collections.sort(journals, new DateComparator());
        beforeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTodayJournals(List<TbJournal> incomes, List<TbJournal> payOuts) {
        if (incomes.isEmpty() && payOuts.isEmpty()) {
            rlRemember.setVisibility(View.VISIBLE);
        } else {
            rlRemember.setVisibility(View.GONE);
            double incomeSum = 0;
            for (TbJournal income : incomes) {
                incomeSum += income.money;
            }
            tvIncome.setText("+ " + JDataKit.doubleFormat(incomeSum));
            double payoutSum = 0;
            for (TbJournal payout : payOuts) {
                payoutSum += payout.money;
            }
            tvPayout.setText("- " + JDataKit.doubleFormat(payoutSum));
            double surplus = incomeSum - payoutSum;
            tvSurplus.setText(JDataKit.doubleFormat(surplus));
        }
    }

    @Override
    public void showSurplus(String surplus) {

    }

    @Override
    public void showIncome(String income) {

    }

    @Override
    public void showPayOut(String payout) {

    }

    @Override
    public void showAddJournal() {
        startActivity(RecordActivity.class, null);
    }

    @Override
    public void setPresenter(TodayContract.Presenter presenter) {

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
