package com.example.jorunal_bishe.edit;

import android.content.Context;
import android.content.Intent;

import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;
import com.example.jorunal_bishe.dao.TbJournal;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:05
 * desc   :
 */
public class EditContract {
    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode, Intent data);
        void initDataBase(Context context);
        void updateJournals(TbJournal journal);
        void deleteJournals(long id);
        void openCalendar();
        void popupClassify(int type);
    }

    interface View extends BaseView<Presenter> {
        void initPopupWindow();
        void showPopupClassify(List<String> rootList, List<List<String>> subList);
        void showCalendar();
        void showDateInfo(String date, String time, String week);
        void showClassifyText(String classify);
    }
}
