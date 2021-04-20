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
 * date   : 2021/4/2011:41
 * desc   :
 */
public class PayoutPresenter extends RecordPresenter{

    public PayoutPresenter(Context context, RecordContract.View view) {
        super(context, view);
        view.initPopupWindow();
    }

    @Override
    public void saveJournals(String money,String rootType, String subType,
                             String description, String dates[], String imgPath) {
        TbJournal payOut = new TbJournal();
        payOut.rootType = rootType;
        payOut.journalType = TbJournal.PAYOUT;
        payOut.subType = subType;
        payOut.description = description;
        payOut.imgPath = imgPath;
        money = money.replace(",", "");
        payOut.money = Double.parseDouble(money);
        payOut.date = dates[0];
        payOut.time = dates[1];
        payOut.week = dates[2];
        journalDao.saveJournal(payOut);
    }

    @Override
    protected List<TbClassify> getRootList() {
        List<TbClassify> classifies = classifyDao.findTbClassify(ClassifyType.PAYOUT.value());
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
