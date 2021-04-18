package com.example.jorunal_bishe.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.example.jorunal_bishe.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


public class SettingItemView extends RelativeLayout {

	@ViewInject(R.id.setting_item_name)
	private TextView mSettingItemTV;
	@ViewInject(R.id.setting_item_tips)
	private TextView mSettingItemValue;
	@ViewInject(R.id.setting_value_content)
	private View mSettingValueContent;
	@ViewInject(R.id.setting_item_crow)
	private ImageView mSettingArrow;
	@ViewInject(R.id.setting_item_toggle)
	private ToggleButton mSettingToggleBtn;

	public SettingItemView(Context context) {
		this(context, null);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews(context, attrs);
	}

	private void initViews(Context context, AttributeSet attrs) {
		View view = View.inflate(context, R.layout.item_setting_item_view, this);
		view.setBackgroundResource(R.drawable.selector_white_gray);
		x.view().inject(view);

		if (attrs != null) {
			TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.settingItem);
			String name = mTypedArray.getString(R.styleable.settingItem_settingName);
			mSettingItemTV.setText(name);

			int type = mTypedArray.getInt(R.styleable.settingItem_settingType, 0);
			if (type == 0) {
				mSettingToggleBtn.setVisibility(View.GONE);
				mSettingValueContent.setVisibility(View.VISIBLE);
			} else if (type == 1) {
				mSettingToggleBtn.setVisibility(View.VISIBLE);
				mSettingValueContent.setVisibility(View.GONE);
			} else {
				mSettingArrow.setVisibility(View.GONE);
				mSettingToggleBtn.setVisibility(View.GONE);
				mSettingValueContent.setVisibility(View.VISIBLE);
			}

			int toggleCheck = mTypedArray.getInt(R.styleable.settingItem_settingToggleCheck, 0);
			mSettingToggleBtn.setChecked(toggleCheck > 0);

			mTypedArray.recycle();
		}
	}

	public void setOnItemClickListener(OnClickListener listener) {
		this.setOnClickListener(listener);
	}

	public void setSettingItemValue(String value) {
		mSettingItemValue.setText(value);
	}

	public void setToggleChecked(boolean isCheck) {
		mSettingToggleBtn.setChecked(isCheck);
	}

	public void setToggleBtnVisible(OnCheckedChangeListener listener) {
		mSettingValueContent.setVisibility(View.GONE);
		mSettingToggleBtn.setVisibility(View.VISIBLE);
		mSettingToggleBtn.setOnCheckedChangeListener(listener);
	}

	public void changeToggleBtnCheck() {
		mSettingToggleBtn.toggle();
	}

}
