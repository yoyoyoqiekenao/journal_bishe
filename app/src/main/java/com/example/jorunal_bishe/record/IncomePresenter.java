package com.example.jorunal_bishe.record;

import android.content.Context;

import com.example.jorunal_bishe.dao.TbClassify;
import com.example.jorunal_bishe.dao.TbJournal;
import com.example.jorunal_bishe.dao.TbSubclass;
import com.example.jorunal_bishe.util.ClassifyType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2010:41
 * desc   :
 */
public class IncomePresenter extends RecordPresenter {

    public IncomePresenter(Context context, RecordContract.View view) {
        super(context, view);
        view.initPopupWindow();
    }

    @Override
    public void saveJournals(String money, String rootType, String subType,
                             String description, String dates[], String imgPath) {
        TbJournal income = new TbJournal();
        income.rootType = rootType;
        income.journalType = TbJournal.INCOME;
        income.subType = subType;
        income.description = description;
        income.imgPath = imgPath;
        money = money.replace(",", "");
        income.money = Double.parseDouble(money);
        income.date = dates[0];
        income.time = dates[1];
        income.week = dates[2];
        journalDao.saveJournal(income);
    }

    @Override
    protected List<TbClassify> getRootList() {
        List<TbClassify> classifies = classifyDao.findTbClassify(ClassifyType.INCOME.value());
        return classifies;
    }

    @Override
    protected List<String> getSubList(int index) {
        List<TbSubclass> subclasses = subclassDao.findTbSubclass(index);
        List<String> list = new ArrayList<>();
        for(TbSubclass subclass : subclasses){
            list.add(subclass.name);
        }
        return list;
    }
}
