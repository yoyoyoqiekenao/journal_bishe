package com.example.jorunal_bishe.edit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.jorunal_bishe.calendar.CalendarActivity;
import com.example.jorunal_bishe.dao.TbClassify;
import com.example.jorunal_bishe.dao.TbClassifyDao;
import com.example.jorunal_bishe.dao.TbJournal;
import com.example.jorunal_bishe.dao.TbJournalDao;
import com.example.jorunal_bishe.dao.TbSubclass;
import com.example.jorunal_bishe.dao.TbSubclassDao;
import com.example.jorunal_bishe.util.JLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:05
 * desc   :
 */
public class EditPresenter  implements EditContract.Presenter{
    protected Context context;
    protected EditContract.View view;
    protected TbJournalDao journalDao;
    protected TbClassifyDao classifyDao;
    protected TbSubclassDao subclassDao;

    public EditPresenter(Context context, EditContract.View view) {
        this.context = context;
        this.view = view;
        view.initPopupWindow();
    }

    @Override
    public void start() {

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
    public void updateJournals(TbJournal journal) {
        journalDao.updateJournal(journal);
    }

    @Override
    public void deleteJournals(long id) {
        journalDao.deleteJournal(id);
    }

    @Override
    public void openCalendar() {
        view.showCalendar();
    }

    @Override
    public void popupClassify(int type) {
        List<TbClassify> classifies = getRootList(type);
        List<String> rootList = new ArrayList<>();
        List<List<String>> subList = new ArrayList<>();
        for (TbClassify classify : classifies) {
            subList.add(getSubList(classify.idx));
            rootList.add(classify.name);
        }
        view.showPopupClassify(rootList, subList);
    }

    protected List<TbClassify> getRootList(int type) {
        List<TbClassify> classifies = classifyDao.findTbClassify(type);
        return classifies;
    }

    protected List<String> getSubList(int index) {
        List<TbSubclass> subclasses = subclassDao.findTbSubclass(index);
        List<String> list = new ArrayList<>();
        for (TbSubclass subclass : subclasses) {
            list.add(subclass.name);
        }
        return list;
    }
}
