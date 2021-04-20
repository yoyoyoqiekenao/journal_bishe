package com.example.jorunal_bishe.mine;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.ActivityBase;
import com.example.jorunal_bishe.base.FragmentAdapter;
import com.example.jorunal_bishe.util.ClassifyType;
import com.example.jorunal_bishe.widgets.TitleView;
import com.google.android.material.tabs.TabLayout;

import org.xutils.view.annotation.ViewInject;

/**
 * @author : 徐无敌
 * date   : 2021/4/2016:48
 * desc   :
 */
public class ModifyClassifyActivity extends ActivityBase implements TabLayout.OnTabSelectedListener {

    @ViewInject(R.id.tv_title)
    private TitleView titleView;
    @ViewInject(R.id.tl_tab)
    private TabLayout indicator;
    @ViewInject(R.id.viewpager)
    protected ViewPager mFileViewPager;

    protected int mCurrentTabIndex = 0;
    private FragmentModifyFirst fragmentIncome;
    private FragmentModifyFirst fragmentPayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_modify_classify;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        titleView.setResource(R.drawable.selector_return_btn,
                R.string.add_first_classify);
        titleView.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleView.setRightDetail(R.drawable.selector_ok_btn);
        titleView.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentTabIndex == 0) {
                    fragmentIncome.save();
                } else {
                    fragmentPayout.save();
                }
                finish();
            }
        });

        fragmentIncome = FragmentModifyFirst.newInstance(ClassifyType.INCOME);
        fragmentPayout = FragmentModifyFirst.newInstance(ClassifyType.PAYOUT);
        indicator.addOnTabSelectedListener(this);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(fragmentIncome, "收入");
        adapter.addFragment(fragmentPayout, "支出");
        mFileViewPager.setAdapter(adapter);
        indicator.setupWithViewPager(mFileViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        indicator.removeOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getText().equals("收入")) {
            mCurrentTabIndex = 0;
        } else {
            mCurrentTabIndex = 1;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
