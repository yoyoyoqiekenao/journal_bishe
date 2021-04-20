package com.example.jorunal_bishe.mine;

import com.example.jorunal_bishe.base.BasePresenter;
import com.example.jorunal_bishe.base.BaseView;
import com.example.jorunal_bishe.util.ClassifyType;

import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2016:54
 * desc   :
 */
public interface ClassifyContract {
    interface Presenter extends BasePresenter {
        void initDataBase();
        void loadClassify(ClassifyType type);
    }

    interface View extends BaseView<Presenter> {
        void showClassify(List<Classify> classify);
    }
}
