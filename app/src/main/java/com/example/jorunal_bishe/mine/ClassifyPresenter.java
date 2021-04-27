package com.example.jorunal_bishe.mine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.dao.TbClassify;
import com.example.jorunal_bishe.dao.TbClassifyDao;
import com.example.jorunal_bishe.util.ClassifyType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2016:55
 * desc   :
 */
public class ClassifyPresenter implements ClassifyContract.Presenter {

    private ClassifyContract.View view;
    private Context context;
    private TbClassifyDao classifyDao;
    private List<Classify> classifyInfos;

    public ClassifyPresenter(Context context, ClassifyContract.View view) {
        this.context = context;
        this.view = view;
        classifyInfos = new ArrayList<>();
    }

    @Override
    public void start() {

    }

    @Override
    public void initDataBase() {
        classifyDao = TbClassifyDao.getInstance();
    }

    @Override
    public void loadClassify(ClassifyType type) {
        classifyInfos.clear();
        if (type == ClassifyType.INCOME) {
            loadIncome();
        } else {
            loadPayout();
        }
        view.showClassify(classifyInfos);
    }

    @Override
    public void deleteClassify(String name,int position) {
        classifyDao.deleteClassify(name);
        view.deleteClassify(position);
    }

    private void loadIncome() {
        List<TbClassify> classifyes = classifyDao.findTbClassify(ClassifyType.INCOME.value());
        if (classifyes != null && classifyes.size() > 0) {
            for (TbClassify classify : classifyes) {
                Classify bean = new Classify();
                Bitmap bitmap = BitmapFactory.decodeByteArray(classify.imgs, 0, classify.imgs.length);
                bean.setIcon(bitmap);
                bean.setName(classify.name);
                bean.setIndex(classify.idx);
                classifyInfos.add(bean);
            }
        }

        Classify classify = new Classify();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.edit_add_icon);
        classify.setIcon(bitmap);
        classify.setName(context.getResources().getString(R.string.add_first_income_classify));
        classify.setIndex(TbClassifyDao.INCOME_COUNT + classifyes.size());
        classifyInfos.add(classify);
    }

    private void loadPayout() {
        List<TbClassify> classifyes = classifyDao.findTbClassify(ClassifyType.PAYOUT.value());
        if (classifyes != null && classifyes.size() > 0) {
            for (TbClassify classify : classifyes) {
                Classify bean = new Classify();
                Bitmap bitmap = BitmapFactory.decodeByteArray(classify.imgs, 0, classify.imgs.length);
                bean.setIcon(bitmap);
                bean.setName(classify.name);
                bean.setIndex(classify.idx);
                classifyInfos.add(bean);
            }
        }

        Classify classify = new Classify();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.edit_add_icon);
        classify.setIcon(bitmap);
        classify.setName(context.getResources().getString(R.string.add_first_payout_classify));
        classify.setIndex(classifyes.size());
        classifyInfos.add(classify);
    }
}
