package com.example.jorunal_bishe.calendar;

import android.content.Intent;

import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;
import com.example.jorunal_bishe.been.JCalendar;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2010:43
 * desc   :
 */
public interface CalendarContract {
    interface Presenter extends BasePresenter {
        void initTopText();
        void loadCalendar();
        void addGridView();
        void moveToLeft();
        void moveToRight();
        void clickGridViewItem(int position);
    }

    interface View extends BaseView<Presenter> {
        void showGridView();
        void showCalendar(List<JCalendar> calendar);
        void showTopText(String text);
        void close(Intent intent);
    }
}
