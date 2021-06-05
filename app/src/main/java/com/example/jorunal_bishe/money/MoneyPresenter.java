package com.example.jorunal_bishe.money;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.jorunal_bishe.JournalType;
import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.been.Journal;
import com.example.jorunal_bishe.dao.TbBudget;
import com.example.jorunal_bishe.dao.TbBudgetDao;
import com.example.jorunal_bishe.dao.TbJournal;
import com.example.jorunal_bishe.dao.TbJournalDao;
import com.example.jorunal_bishe.util.AppPref;
import com.example.jorunal_bishe.util.BitmapUtil;
import com.example.jorunal_bishe.util.JDataKit;
import com.example.jorunal_bishe.util.JDateKit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoneyPresenter implements MoneyContract.Presenter {

    private final MoneyContract.View view;
    private Context context;
    private JournalType currentType = JournalType.TODAY;
    private TbJournalDao journalDao;
    private TbBudgetDao budgetDao;
    private AppPref appPref;
    private String budgetType;
    private String startDate;
    private String endDate;

    public MoneyPresenter(Context context, MoneyContract.View view) {
        this.context = context;
        this.view = view;
        appPref = AppPref.getInstance();
    }

    @Override
    public void initDataBase() {
        Log.d("xuwudi", "初始化");
        journalDao = TbJournalDao.getInstance();
        budgetDao = TbBudgetDao.getInstance();
    }

    @Override
    public void loadJournals() {
        String dateStr = JDateKit.dateToStr("yyyy-M-dd", new Date());
        String year = dateStr.split("-")[0];
        String day = dateStr.split("-")[2];
        Bitmap bitmap = null;
        String date = null;
        List<Journal> journals = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Journal journal = new Journal();
            calculateDate(JournalType.valueOf(i));
            switch (i) {
                case 0:
                    bitmap = BitmapUtil.drawTextToBitmap(context, R.mipmap.main_today,
                            day);
                    date = context.getString(R.string.today);
                    break;
                case 1:
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.icon_trans_item_week);
                    date = context.getString(R.string.this_week);
                    break;
                case 2:
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.icon_trans_item_month);
                    date = context.getString(R.string.this_month);
                    break;
                case 3:
                    int y = JDateKit.yearDays(Integer.parseInt(year));
                    bitmap = BitmapUtil.drawTextToBitmap(context, R.mipmap.main_today,
                            String.valueOf(y));
                    date = context.getString(R.string.this_year);
                    break;
                case 4:
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.mipmap.icon_all_bill);
                    date = context.getString(R.string.this_all_bill);
                    break;
                default:
                    break;
            }
            double incomeSum = 0;
            List<TbJournal> incomes = journalDao.findBetween(startDate,
                    endDate, TbJournal.INCOME);
            for (TbJournal income : incomes) {
                incomeSum += income.money;
            }
            double payOutSum = 0;
            List<TbJournal> payOuts = journalDao.findBetween(startDate,
                    endDate, TbJournal.PAYOUT);
            for (TbJournal payout : payOuts) {
                payOutSum += payout.money;
            }
            journal.setType(JournalType.valueOf(i));
            journal.setIncome(incomeSum);
            journal.setPayout(payOutSum);
            journal.setBitmap(bitmap);
            journal.setDate(date);
            String description = startDate + "-" + endDate;
            if (i == 0) {
                if (incomeSum == 0 && payOutSum == 0) {
                    journal.setDescription(context.getString(R.string.journal_description));
                } else {
                    journal.setDescription(context.getString(R.string.recently));
                }
            } else {
                journal.setDescription(description);
            }
            if (i == 2) {
                List<TbBudget> budgets = getBudget();
                view.showSurplusText(budgetType + context.getString(R.string.budget_surplus));
                if (budgets == null || budgets.size() == 0) {
                    view.showSurplus(JDataKit.doubleFormat(0));
                } else {
                     double outs = 0.0;
                    List<TbJournal> outList = journalDao.findBetween(startDate,
                            endDate, TbJournal.PAYOUT);
                    for (TbJournal payout : outList) {
                        outs += payout.money;
                    }
                    for (TbBudget budget : budgets) {
                        double money = budget.money - outs;
                        view.showSurplus(JDataKit.doubleFormat(money));
                    }
                }
                view.showIncome(JDataKit.doubleFormat(incomeSum) + "");
                view.showPayOut(JDataKit.doubleFormat(payOutSum) + "");
            }
            journals.add(journal);
        }
        view.showJournals(journals);
    }

    @Override
    public void loadBudget() {

    }

    @Override
    public void addNewJournal() {
        view.showAddJournal();
    }

    @Override
    public void openJournalDetails(Journal journal) {
        if (journal.getType() == JournalType.TODAY) {
            view.showTodayDetailsUi();
        } else {
            view.showConsumeDetailUi(journal);
        }
    }

    @Override
    public void openSurplusDetails() {
        view.showSurplusDetailsUi();

    }

    @Override
    public void setJournalType(JournalType requestType) {
        currentType = requestType;

    }

    @Override
    public JournalType getJournalType() {
        return currentType;
    }

    @Override
    public void start() {

    }

    private void calculateDate(JournalType type) {
        switch (type) {
            case THIS_WEEK:
                budgetType = context.getString(R.string.this_week);
                startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfWeek());
                endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfWeek());
                break;
            case THIS_MONTH:
                budgetType = context.getString(R.string.this_month);
                startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfMonth());
                endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfMonth());
                break;
            case THIS_YEAR:
                budgetType = context.getString(R.string.this_year);
                startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getFirstDayOfYear());
                endDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.getLastDayOfYear());
                break;
            case THIS_ALL_BILL:
                budgetType = context.getString(R.string.this_all_bill);
                startDate = JDateKit.dateToStr("yyyy-MM-dd", JDateKit.setDate(2000, 2, 2));
                endDate = JDateKit.dateToStr("yyyy-MM-dd", new Date());
                break;
            default:
                budgetType = context.getString(R.string.today);
                startDate = JDateKit.dateToStr("yyyy-MM-dd", new Date());
                endDate = JDateKit.dateToStr("yyyy-MM-dd", new Date());
                break;
        }
    }

    private List<TbBudget> getBudget() {
        int index = appPref.getIntValue("select_index", JournalType.THIS_WEEK.value());
        calculateDate(JournalType.valueOf(index));
        return budgetDao.findBudget(startDate, endDate);
    }
}
