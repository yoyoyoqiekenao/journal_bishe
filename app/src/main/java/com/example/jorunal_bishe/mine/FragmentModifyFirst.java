package com.example.jorunal_bishe.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class FragmentModifyFirst extends FragmentBase implements ClassifyContract.View, PopupWindow.OnDismissListener {
    @ViewInject(R.id.lv_classify)
    private ListView lvClassify;
    @ViewInject(R.id.rootView)
    private RelativeLayout rootView;

    private ClassifyType classifyType = ClassifyType.INCOME;
    private ClassifyAdapter classifyAdapter;
    private List<Classify> datas = new ArrayList<>();
    private ClassifyContract.Presenter presenter;
    private PopupWindow mPop;


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
        lvClassify.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == datas.size() - 1) {

                } else {
                    showDeletePop(position);
                }
                return true;
            }
        });
    }

    private void showDeletePop(int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_delete, null);
        mPop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvSure = view.findViewById(R.id.tv_sure);
        TextView tvNo = view.findViewById(R.id.tv_no);
        backgroundAlpha(0.5f);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setOutsideTouchable(true);
        mPop.setAnimationStyle(R.style.anim_bottonbar);

        mPop.setOnDismissListener(this);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPop.dismiss();
                onResume();
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
            }
        });
        mPop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
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
        Log.d("xuwudi", "size===" + classify.size());
        classifyAdapter.notifyDataSetChanged();
    }



    /**
     * 设置添加屏幕的背景透明度 * * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1.0f);
    }
}
