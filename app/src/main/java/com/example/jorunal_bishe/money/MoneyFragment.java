package com.example.jorunal_bishe.money;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jorunal_bishe.JournalType;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.adapter.MoneyAdapter;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.been.Journal;
import com.example.jorunal_bishe.budget.BudgetActivity;
import com.example.jorunal_bishe.consume.ConsumeDetailActivity;
import com.example.jorunal_bishe.consume.TodayDetailActivity;
import com.example.jorunal_bishe.eventbus.UpdateEvent;
import com.example.jorunal_bishe.record.RecordActivity;
import com.example.jorunal_bishe.util.JDateKit;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@ContentView(R.layout.fragment_money)
public class MoneyFragment extends FragmentBase implements MoneyContract.View, View.OnClickListener {

    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_income_value)
    TextView tvIncome;
    @BindView(R.id.tv_payout_value)
    TextView tvPayOut;
    @BindView(R.id.tv_surplus_value)
    TextView tvSurplus;
    @BindView(R.id.rc_journal)
    ListView rc_journal;
    @BindView(R.id.btn_remember)
    Button btnRemember;

    private MoneyContract.Presenter presenter;
    private MoneyAdapter mAdapter;
    private MoneyAdapter.JournalItemListener listener = new MoneyAdapter.JournalItemListener() {
        @Override
        public void onJournalClick(Journal clickedJournal) {
            presenter.openJournalDetails(clickedJournal);
        }
    };


    @Override
    protected void initWidgets() {
        ButterKnife.bind(this, view);
        presenter = new MoneyPresenter(context, this);
        presenter.initDataBase();

        mAdapter = new MoneyAdapter(context, new ArrayList<Journal>(0), listener);
        rc_journal.setAdapter(mAdapter);

        //presenter.loadJournals();
        setCurDate();
        btnRemember.setOnClickListener(this);
        tvIncome.setOnClickListener(this);
        tvPayOut.setOnClickListener(this);
        tvSurplus.setOnClickListener(this);
    }

    private void setCurDate() {
        {
            String date = JDateKit.dateToStr("yyyy-M", new Date());
            String year = date.split("-")[0];
            String month = date.split("-")[1];
            SpannableString sp = new SpannableString(month + "/" + year);
            sp.setSpan(new ForegroundColorSpan(Color.RED), 0, month.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan(new StyleSpan(Typeface.BOLD),
                    0, month.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan(new RelativeSizeSpan(2.0f), 0, month.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan(new RelativeSizeSpan(1.0f), month.length(), sp.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_date.setText(sp.toString());
        }
    }


    @Override
    public void showJournals(List<Journal> journals) {
        mAdapter.refreshDatas(journals);
    }

    @Override
    public void showSurplusText(String text) {
        tvSurplus.setText(getString(R.string.rmb) + text);
    }

    @Override
    public void showSurplus(String surplus) {
        tvSurplus.setText(getString(R.string.rmb) + surplus);
    }

    @Override
    public void showIncome(String income) {
        tvIncome.setText(getString(R.string.rmb) + income);
    }

    @Override
    public void showPayOut(String payout) {
        tvPayOut.setText(getString(R.string.rmb) + payout);
    }

    @Override
    public void showSurplusDetailsUi() {
        Intent intent = new Intent(getContext(), BudgetActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAddJournal() {
        Intent intent = new Intent(getContext(), RecordActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTodayDetailsUi() {
        Intent intent = new Intent(getContext(), TodayDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void showConsumeDetailUi(Journal journal) {
        Intent intent = new Intent(getContext(), ConsumeDetailActivity.class);
        intent.putExtra("index", journal.getType().value());
        startActivity(intent);
    }

    @Override
    public void setPresenter(MoneyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_remember:
                presenter.addNewJournal();
                break;
            case R.id.tv_income_value:
            case R.id.tv_payout_value:
                Intent intent = new Intent();
                intent.putExtra("index", JournalType.THIS_MONTH.value());
                startActivity(ConsumeDetailActivity.class, intent);
                break;
            case R.id.tv_surplus_value:
                presenter.openSurplusDetails();
                break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateEvent response) {
        if (response.from.equals("EditActivity")) {
            if (response.event.equals("delete")) {
                presenter.loadJournals();
            } else if (response.event.equals("update")) {
                presenter.loadJournals();
            }
        } else if (response.from.equals("RecordActivity")) {
            if (response.event.equals("add")) {
                presenter.loadJournals();
            }
        } else if (response.from.equals("BudgetActivity")) {
            if (response.event.equals("budget")) {
                presenter.loadJournals();
            } else if (response.event.equals("change_budget")) {
                presenter.loadJournals();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadJournals();
    }
}
