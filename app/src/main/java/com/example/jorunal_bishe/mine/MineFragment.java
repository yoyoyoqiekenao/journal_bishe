package com.example.jorunal_bishe.mine;

import android.content.Intent;
import android.view.View;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.base.FragmentBase;
import com.example.jorunal_bishe.widgets.SettingItemView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : 徐无敌
 * date   : 2021/4/2015:37
 * desc   :
 */
@ContentView(R.layout.fragment_mine)
public class MineFragment extends FragmentBase implements View.OnClickListener {

    @BindView(R.id.item_update_pwd)
    SettingItemView item_update_pwd;

    @Override
    protected void initWidgets() {
        ButterKnife.bind(this, view);

        item_update_pwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_update_pwd:
                startActivity(new Intent(getContext(), ModifyClassifyActivity.class));
                break;
            default:
        }
    }
}
