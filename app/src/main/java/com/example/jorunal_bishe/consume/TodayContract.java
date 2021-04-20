package com.example.jorunal_bishe.consume;

import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;
import com.example.jorunal_bishe.dao.TbJournal;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2014:55
 * desc   :
 */
public class TodayContract {
    interface Presenter extends BasePresenter {
        void initDataBase();
        void loadJournals();
        void loadTodayJournals();
        void addNewJournal();
    }

    interface View extends BaseView<Presenter> {
        void showJournals(List<TbJournal> incomes, List<TbJournal> payOuts);
        void showTodayJournals(List<TbJournal> incomes, List<TbJournal> payOuts);
        //		void showSurplusText(String text);
        void showSurplus(String surplus);
        void showIncome(String income);
        void showPayOut(String payout);
        //		void showSurplusDetailsUi();
        void showAddJournal();
//		void showTodayDetailsUi();
//		void showConsumeDetailUi(Journal journal);
    }
}
