package com.example.jorunal_bishe;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorunal_bishe.adapter.ExamplePagerAdapter;
import com.example.jorunal_bishe.base.ActivityPermissions;
import com.example.jorunal_bishe.mine.MineFragment;
import com.example.jorunal_bishe.money.MoneyFragment;
import com.example.jorunal_bishe.note.NoteFragment;
import com.example.jorunal_bishe.permission.PermissionListener;
import com.example.jorunal_bishe.share.ShareFragment;
import com.example.jorunal_bishe.util.JLogUtils;
import com.example.jorunal_bishe.util.ToastUtil;
import com.gyf.immersionbar.ImmersionBar;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ActivityPermissions {

    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private static final String[] CHANNELS = new String[]{"分享", "理财", "便签", "我的"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ExamplePagerAdapter mExamplePagerAdapter;
    private List<Fragment> mList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParams(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        ImmersionBar.with(this).init();
        requestRuntimePermission();
        initMagicIndicator();
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            firstStart();
        }
    }

    private void initMagicIndicator() {
        mList.add(new ShareFragment());
        mList.add(new MoneyFragment());
        mList.add(new NoteFragment());
        mList.add(new MineFragment());

        mExamplePagerAdapter = new ExamplePagerAdapter(getSupportFragmentManager(), mList);
        mViewPager.setAdapter(mExamplePagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mMagicIndicator.setBackgroundColor(Color.BLACK);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                // load   custom layout
                View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
                final ImageView titleImg = (ImageView) customLayout.findViewById(R.id.title_img);
                final TextView titleText = (TextView) customLayout.findViewById(R.id.title_text);
                titleImg.setImageResource(R.mipmap.ic_launcher);
                titleText.setText(mDataList.get(index));
                commonPagerTitleView.setContentView(customLayout);

                commonPagerTitleView.setContentView(customLayout);
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        titleText.setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        titleText.setTextColor(Color.LTGRAY);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
                        titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
                    }

                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
                        titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
                    }
                });

                commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private void requestRuntimePermission() {
        requestRuntimePermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE},
                new PermissionListener() {

                    @Override
                    public void onGranted() {
                        //当权限被授予时调用
                        ToastUtil.getInstance().showMessage("Permission granted");
                        firstStart();
                    }

                    @Override
                    public void onDenied() {
                        ToastUtil.getInstance().showMessage("Permission denied");
                    }

                    @Override
                    public void onShowRationale(String[] permissions) {
                        if (permissions != null) {
                            JLogUtils.getInstance().i("onShowRationale");
                            helper.setIsPositive(true);
                            helper.request();
                        }
                    }
                });
    }

    private void firstStart() {
        boolean result = appPref.getBoolValue("first", false);
        if (!result) {
            appPref.setBoolValue("first", true);
            Intent intent = new Intent(MainActivity.this, JournalService.class);
            startService(intent);
        }
    }
}