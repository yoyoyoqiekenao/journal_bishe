package com.example.jorunal_bishe.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.util.ClassifyType;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 徐无敌
 * date   : 2021/4/2016:53
 * desc   :
 */
@ContentView(R.layout.fragment_modify_first)

public class FragmentModifyFirst extends FragmentBase implements ClassifyContract.View {
    @ViewInject(R.id.lv_classify)
    private ListView lvClassify;
    private ClassifyType classifyType = ClassifyType.INCOME;
    private ClassifyAdapter classifyAdapter;
    private List<Classify> datas = new ArrayList<>();
    private ClassifyContract.Presenter presenter;

    @SuppressLint("ValidFragment")
    private FragmentModifyFirst() {
        super();
    }

    @SuppressLint("ValidFragment")
    private FragmentModifyFirst(ClassifyType classify) {
        this();
        this.classifyType = classify;
    }

    public static FragmentModifyFirst newInstance(ClassifyType classify) {
        return new FragmentModifyFirst(classify);
    }

    @Override
    protected void initWidgets() {
        presenter = new ClassifyPresenter(context, this);
        presenter.initDataBase();
        classifyAdapter = new ClassifyAdapter(context, datas);
        lvClassify.setAdapter(classifyAdapter);
        presenter.loadClassify(classifyType);
        lvClassify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                if (i == datas.size() - 1) {
                    if (classifyType == ClassifyType.INCOME) {
                        intent.putExtra("title", getString(R.string.new_first_income_classify));
                    } else {
                        intent.putExtra("title", getString(R.string.new_first_payout_classify));
                    }
                    intent.putExtra("step", getString(R.string.next));
                } else {
                    if (classifyType == ClassifyType.INCOME) {
                        intent.putExtra("title", getString(R.string.new_second_income_classify));
                    } else {
                        intent.putExtra("title", getString(R.string.new_second_payout_classify));
                    }
                    intent.putExtra("step", getString(R.string.finish));
                }
                Classify classify = datas.get(i);
                intent.putExtra("size", datas.size());
                intent.putExtra("index", classify.getIndex());
                intent.putExtra("classify_type", classifyType.value());
                startActivityForResult(NewClassifyActivity.class, intent, NewClassifyActivity.REQUEST_NEW_CLASSIFY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) {
            return;
        }
        if (requestCode == NewClassifyActivity.REQUEST_NEW_CLASSIFY) {
            presenter.loadClassify(classifyType);
        }
    }

    public void save() {

    }

    @Override
    public void setPresenter(ClassifyContract.Presenter presenter) {

    }

    @Override
    public void showClassify(List<Classify> classify) {
        datas.clear();
        datas.addAll(classify);
        classifyAdapter.notifyDataSetChanged();
    }
}
