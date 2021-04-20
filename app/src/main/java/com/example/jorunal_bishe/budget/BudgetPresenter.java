package com.example.jorunal_bishe.budget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.jorunal_bishe.JournalType;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.been.BudgetInfo;
import com.example.jorunal_bishe.dao.TbBudget;
import com.example.jorunal_bishe.dao.TbBudgetDao;
import com.example.jorunal_bishe.dao.TbClassify;
import com.example.jorunal_bishe.dao.TbClassifyDao;
import com.example.jorunal_bishe.dao.TbJournal;
import com.example.jorunal_bishe.dao.TbJournalDao;
import com.example.jorunal_bishe.util.AppPref;
import com.example.jorunal_bishe.util.ClassifyType;
import com.example.jorunal_bishe.util.JDataKit;
import com.example.jorunal_bishe.util.JDateKit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:25
 * desc   :
 */
public class BudgetPresenter implements BudgetContract.Presenter {

    private JournalType currentType = JournalType.TODAY;
    private BudgetContract.View view;
    private AppPref appPref;
    private Context context;
    private List<String> list;
    private List<BudgetInfo> budgetInfos;
    private TbJournalDao payOutDao;
    private TbBudgetDao budgetDao;
    private TbClassifyDao classifyDao;
    private String startDate;
    private String endDate;

    public BudgetPresenter(Context context, BudgetContract.View view) {
        this.view = view;
        this.context = context;
        appPref = AppPref.getInstance();
        list = new ArrayList<>();
        budgetInfos = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void initDataBase() {
        payOutDao = TbJournalDao.getInstance();
        budgetDao = TbBudgetDao.getInstance();
        classifyDao = TbClassifyDao.getInstance();
    }

    @Override
    public void initTitle() {
        int index = appPref.getIntValue("select_index", 1);
        for (int i = 0; i < 4; i++) {
            int resId = R.string.today;
            switch (i) {
                case 1:
                    resId = R.string.this_week;
                    break;
                case 2:
                    resId = R.string.this_month;
                    break;
                case 3:
                    resId = R.string.this_year;
                    break;
            }
            list.add(context.getString(resId));
        }
        view.initPopupWindow();
        changeTitle(index);
        view.showPopupDataType(list);
    }

    @Override
    public void changeTitle(int position) {
        setJournalType(JournalType.valueOf(position));
        view.showTitle(list.get(position));
    }

    @Override
    public void popupDataType() {
        //view.showPopupDataType();
    }

    @Override
    public void loadBudgets() {
        budgetInfos.clear();
        List<TbClassify> classifies = classifyDao.findTbClassify(ClassifyType.PAYOUT.value());
        calculateDate(getJournalType());
        List<TbJournal> journals = payOutDao.findBetween(startDate, endDate, 1);
        for (TbClassify classify : classifies) {
            BudgetInfo info = new BudgetInfo();
            info.setTitle(classify.name);
            Bitmap bitmap = BitmapFactory.decodeByteArray(classify.imgs, 0, classify.imgs.length);
            info.setIcon(bitmap);
            double sum = 0;
            for (TbJournal journal : journals) {
                if (journal.rootType.equals(classify.name)) {
                    sum += journal.money;
                }
            }
            if (sum != 0)
                info.setBalance(JDataKit.doubleFormat(sum));
            budgetInfos.add(info);
        }
        view.showBudgets(budgetInfos);

        double payOutSum = 0;
        journals = payOutDao.findBetween(startDate, endDate, 1);
        for (TbJournal journal : journals) {
            payOutSum += journal.money;
        }
        view.showUsedBudget(JDataKit.doubleFormat(payOutSum));
        double money = 0;
        double surplus = 0;
        List<TbBudget> budgets = getBudget();
        //计算结余
        for (TbBudget budget : budgets) {
            money = budget.money;
            surplus = budget.money - payOutSum;
        }
        view.showMoney(JDataKit.doubleFormat(money));
        view.showUsableBudget(JDataKit.doubleFormat(surplus));
    }

    private List<TbBudget> getBudget() {
        calculateDate(getJournalType());
        return budgetDao.findBudget(startDate, endDate);
    }

    private void calculateDate(JournalType type) {
        switch (type) {
            case TODAY:
                startDate = JDateKit.dateToStr("yyyy-MM-dd", new Date());
                endDate = JDateKit.dateToStr("yyyy-MM-dd", new Date());
                break;
            case THIS_WEEK:
                startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfWeek());
                endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfWeek());
                break;
            case THIS_MONTH:
                startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfMonth());
                endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfMonth());
                break;
            case THIS_YEAR:
                startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfYear());
                endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfYear());
                break;
        }
    }

    @Override
    public void saveBudget(String money) {
        List<TbBudget> budgets = getBudget();
        for (TbBudget budget : budgets) {
            budgetDao.deleteBudget(budget);
        }
        TbBudget budget = new TbBudget();
        int index = appPref.getIntValue("select_index", 1);
        budget.index = index;
        budget.type = list.get(getJournalType().value());
        budget.start = startDate;
        budget.end = endDate;
        money = money.replace(",", "");
        budget.money = Double.parseDouble(money);
        budgetDao.saveBudget(budget);
        view.close();
    }

    @Override
    public void setJournalType(JournalType requestType) {
        this.currentType = requestType;
        appPref.setIntValue("select_index", currentType.value());
    }

    @Override
    public JournalType getJournalType() {
        return currentType;
    }
}
