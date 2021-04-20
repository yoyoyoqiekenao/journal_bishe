package com.example.jorunal_bishe.consume;

import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;
import com.example.jorunal_bishe.dao.TbJournal;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:17
 * desc   :
 */
public class ConsumeContract {
    interface Presenter extends BasePresenter {
        void initDataBase();
        void loadJournals(String start, String end);
//		void loadBudget();
//		void addNewJournal();
//		void openJournalDetails(Journal journal);
//		void openSurplusDetails();
//		void setJournalType(JournalType requestType);
//		JournalType getJournalType();
    }

    interface View extends BaseView<Presenter> {
        void showJournals(List<TbJournal> journals);
        //		void showSurplusText(String text);
        void showSurplus(String surplus);
        void showIncome(String income);
        void showPayOut(String payout);
    }
}
