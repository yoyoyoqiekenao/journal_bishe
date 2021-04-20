package com.example.jorunal_bishe.record;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.jorunal_bishe.calendar.CalendarActivity;
import com.example.jorunal_bishe.dao.TbClassify;
import com.example.jorunal_bishe.dao.TbClassifyDao;
import com.example.jorunal_bishe.dao.TbJournalDao;
import com.example.jorunal_bishe.dao.TbSubclassDao;
import com.example.jorunal_bishe.util.JLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2010:41
 * desc   :
 */
public abstract class RecordPresenter implements RecordContract.Presenter{
    protected Context context;
    protected RecordContract.View view;
    protected TbJournalDao journalDao;
    protected TbClassifyDao classifyDao;
    protected TbSubclassDao subclassDao;

    public RecordPresenter(Context context, RecordContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK != resultCode || data == null) {
            return;
        }
        if (requestCode == CalendarActivity.REQUEST_CALENDAR) {
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            String week = data.getStringExtra("week");
            JLogUtils.getInstance().i("date = " + date);
            view.showDateInfo(date, time, week);
        }
    }

    @Override
    public void initDataBase(Context context) {
        journalDao = TbJournalDao.getInstance();
        classifyDao = TbClassifyDao.getInstance();
        subclassDao = TbSubclassDao.getInstance();
    }

    @Override
    public void openCalendar() {
        view.showCalendar();
    }

    @Override
    public void popupClassify() {
        List<TbClassify> classifies = getRootList();
        List<String> rootList = new ArrayList<>();
        List<List<String>> subList = new ArrayList<>();
        for (TbClassify classify : classifies) {
            subList.add(getSubList(classify.idx));
            rootList.add(classify.name);
        }
        view.showPopupClassify(rootList, subList);
    }

    @Override
    public void start() {

    }

    protected abstract List<TbClassify> getRootList();

    protected abstract List<String> getSubList(int index);
}
