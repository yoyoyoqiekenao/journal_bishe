package com.example.jorunal_bishe.budget;

import com.example.jorunal_bishe.JournalType;
import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;
import com.example.jorunal_bishe.been.BudgetInfo;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:25
 * desc   :
 */
public class BudgetContract {
    interface Presenter extends BasePresenter {
        void initDataBase();
        void initTitle();
        void changeTitle(int position);
        void popupDataType();
        void loadBudgets();
        void saveBudget(String money);
        void setJournalType(JournalType requestType);
        JournalType getJournalType();
    }

    interface View extends BaseView<Presenter> {
        void initPopupWindow();
        void showPopupDataType(List<String> dataTypes);
        void showBudgets(List<BudgetInfo> budgets);
        void showTitle(String title);
        void showUsedBudget(String used);
        void showUsableBudget(String usable);
        void showMoney(String money);
        void close();
    }
}
