/**
 * Copyright (C) 2015, all rights reserved.
 * Since	2015.08.20
 */
package com.example.jorunal_bishe.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
 import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.jorunal_bishe.R;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * @Since 2015.08.20
 */
public class TitleView extends RelativeLayout {

	private Context context;
	@ViewInject(R.id.tv_head_left)
	private TextView mLeftTextView;
	@ViewInject(R.id.tv_head_title)
	private TextView mTitleTextView;
	@ViewInject(R.id.iv_head_title)
	private ImageView mTitleImageView;
	@ViewInject(R.id.tv_head_right)
	private TextView mRightTextView;
	@ViewInject(R.id.iv_head_left)
	private ImageView mLeftImageView;
	@ViewInject(R.id.iv_head_right)
	private ImageView mRightImageView;

	public TitleView(Context context) {
		this(context, null);
	}

	public TitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews(context);
	}

	private void initViews(Context context) {
		this.context = context;
		View view = inflate(context, R.layout.title_view, this);
		//view.setBackgroundColor(Color.parseColor("#FF03A9F4"));
		view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
		//view.setBackgroundResource(R.drawable.main_top_bg);
		x.view().inject(view);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Consumed tounchEvent event, to avoid the event to the bottom view
		return true;
	}

	public void setResource(int resIdLeft, int resIdCenter) {
		setLeftDetail(resIdLeft);
		setCenterDetail(resIdCenter);
	}

	public void setResource(int resIdLeft, int resIdCenter, int resIdRight) {
		setLeftDetail(resIdLeft);
		setCenterDetail(resIdCenter);
		setRightDetail(resIdRight);
	}

	public void setLeftClickListener(OnClickListener listener) {
		mLeftTextView.setOnClickListener(listener);
		mLeftImageView.setOnClickListener(listener);
	}

	public void setTitleRightDrawable(int resid, OnClickListener listener) {
		mTitleTextView.setOnClickListener(listener);
		mTitleImageView.setOnClickListener(listener);
		mTitleImageView.setVisibility(VISIBLE);
		if (resid < 0)
			return;
		mTitleImageView.setBackgroundResource(resid);
	}

	public void setTitleAnimation(boolean flag) {
		//使用属性动画
		ObjectAnimator animation;
		if (flag) {
			animation = ObjectAnimator.ofFloat(mTitleImageView,
					"rotation", 180f, 0f);
		} else {
			animation = ObjectAnimator.ofFloat(mTitleImageView,
					"rotation", 0f, 180f);
		}
		animation.setDuration(500);
		animation.start();
	}

	public void setRightClickListener(OnClickListener listener) {
		mRightTextView.setOnClickListener(listener);
		mRightImageView.setOnClickListener(listener);
	}

	public void setLeftDetail(int resId, CharSequence text) {
		mLeftTextView.setText(text);
		mLeftTextView.setVisibility(VISIBLE);
		Drawable drawable = ContextCompat.getDrawable(context, resId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		mLeftTextView.setCompoundDrawables(drawable, null, null, null);
	}

	public void setLeftDetail(int resId) {
		if (resId > 0 && !setImgDetail(mLeftImageView, resId)) {
			setTxvDetail(mLeftTextView, resId);
		}
	}

	public void setLeftDetail(String text) {
		mLeftTextView.setText(text);
		mLeftTextView.setVisibility(VISIBLE);
	}

	public void setCenterDetail(int resId) {
		setCenterDetail(getResources().getString(resId));
	}

	public void setCenterDetail(String title) {
		mTitleTextView.setText(title);
	}

	public void setRightDetail(int resId) {
		if (resId > 0 && !setImgDetail(mRightImageView, resId)) {
			setTxvDetail(mRightTextView, resId);
		}
	}

	public void setRightDetail(String text) {
		mRightTextView.setText(text);
		mRightTextView.setVisibility(VISIBLE);
	}

	public void setRightDetialInvisible() {
		mRightTextView.setVisibility(GONE);
		mRightImageView.setVisibility(GONE);
	}

	private boolean setTxvDetail(TextView txv, int resId) {
		if (resId <= 0) {
			txv.setVisibility(INVISIBLE);
		} else {
			txv.setVisibility(VISIBLE);
			String text = getStringFromResId(resId);
			if (text == null) {
				txv.setVisibility(INVISIBLE);
			} else {
				txv.setText(text);
				return true;
			}
		}
		return false;
	}

	private boolean setImgDetail(ImageView img, int resId) {
		boolean result = false;
		if (resId <= 0) {
			img.setVisibility(View.INVISIBLE);
		} else {
			img.setVisibility(View.VISIBLE);
			Drawable drawable = getDrawableFromResId(resId);
			if (drawable == null) {
				img.setVisibility(View.INVISIBLE);
			} else {
				img.setImageDrawable(drawable);
				result = true;
			}
		}
		return result;
	}

	private String getStringFromResId(int resId) {
		String result = null;
		try {
			result = getResources().getString(resId);
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Drawable getDrawableFromResId(int resId) {
		return ContextCompat.getDrawable(context, resId);
	}

}
