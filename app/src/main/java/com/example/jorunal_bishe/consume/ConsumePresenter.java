package com.example.jorunal_bishe.consume;

import android.content.Context;

import com.example.jorunal_bishe.dao.TbJournal;
import com.example.jorunal_bishe.dao.TbJournalDao;
import com.example.jorunal_bishe.util.JDataKit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:19
 * desc   :
 */
public class ConsumePresenter implements ConsumeContract.Presenter{
    private ConsumeContract.View view;
    private Context context;
    private TbJournalDao journalDao;

    public ConsumePresenter(Context context, ConsumeContract.View view) {
        this.context = context;
        this.view = view;
        initDataBase();
    }

    @Override
    public void start() {

    }

    @Override
    public void initDataBase() {
        journalDao = TbJournalDao.getInstance();
    }

    @Override
    public void loadJournals(String start, String end) {
        List<TbJournal> incomes = journalDao.findBetween(start, end, TbJournal.INCOME);
        double incomeSum = 0;
        for (TbJournal income : incomes) {
            incomeSum += income.money;
        }
        view.showIncome(JDataKit.doubleFormat(incomeSum));
        List<TbJournal> payouts = journalDao.findBetween(start, end, TbJournal.PAYOUT);
        double payoutSum = 0;
        for (TbJournal payout : payouts) {
            payoutSum += payout.money;
        }
        view.showPayOut(JDataKit.doubleFormat(payoutSum));
        double surplus = incomeSum - payoutSum;
        view.showSurplus(JDataKit.doubleFormat(surplus));
        List<TbJournal> list = new ArrayList<>();
        list.addAll(incomes);
        list.addAll(payouts);
        Collections.sort(list, new DateComparator());
        view.showJournals(list);
    }
}
