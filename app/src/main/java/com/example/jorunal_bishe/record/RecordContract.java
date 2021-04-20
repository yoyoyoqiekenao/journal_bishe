package com.example.jorunal_bishe.record;

import android.content.Context;
import android.content.Intent;

import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2010:34
 * desc   :
 */
public interface RecordContract {
    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode, Intent data);
        void initDataBase(Context context);
        void saveJournals(String money,String rootType, String subType,
                          String description, String dates[], String imgPath);
        void openCalendar();
        void popupClassify();
    }

    interface View extends BaseView<Presenter> {
        void initPopupWindow();
        void showPopupClassify(List<String> rootList, List<List<String>> subList);
        void showCalendar();
        void showDateInfo(String date, String time, String week);
        void showClassifyText(String classify);
    }
}
