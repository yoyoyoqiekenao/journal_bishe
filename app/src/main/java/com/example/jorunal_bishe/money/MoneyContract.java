package com.example.jorunal_bishe.money;

import com.example.jorunal_bishe.JournalType;
import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;
import com.example.jorunal_bishe.been.Journal;

import java.util.List;

public class MoneyContract {
    interface Presenter extends BasePresenter {
        void initDataBase();
        void loadJournals();
        void loadBudget();
        void addNewJournal();
        void openJournalDetails(Journal journal);
        void openSurplusDetails();
        void setJournalType(JournalType requestType);
        JournalType getJournalType();
    }

    interface View extends BaseView<Presenter> {
        void showJournals(List<Journal> journals);
        void showSurplusText(String text);
        void showSurplus(String surplus);
        void showIncome(String income);
        void showPayOut(String payout);
        void showSurplusDetailsUi();
        void showAddJournal();
        void showTodayDetailsUi();
        void showConsumeDetailUi(Journal journal);
    }
}
