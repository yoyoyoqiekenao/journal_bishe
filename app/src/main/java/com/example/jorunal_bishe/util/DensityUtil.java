package com.example.jorunal_bishe.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * The screen density tools DP and SP conversion tool for PX
 * 屏幕单位换算，对屏幕的操作，截图
 *
 * @author
 * @date 2016.05.16
 */
public class DensityUtil {
	private static  DensityUtil instance;
	private DisplayMetrics metrics;
	private WindowManager wm;
	private Context context;

	private DensityUtil(Context context) {
		this.context = context;
		metrics = context.getResources().getDisplayMetrics();
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}

	public static  DensityUtil getInstance(Context context) {
		if (instance == null) {
			instance =  new DensityUtil(context.getApplicationContext());
		}
		return instance;
	}

	public DisplayMetrics getDisplayMetrics() {
		return metrics;
	}

	public float getDensity() {
		return metrics.density;
	}

	public int getHeightInPx() {
		return metrics.heightPixels;
	}

	public int getWidthInPx() {
		return metrics.widthPixels;
	}

	public int getHeightInDp() {
		return px2dp(metrics.heightPixels);
	}

	public int getWidthInDp() {
		return px2dp(metrics.widthPixels);
	}

	/**
	 * The dip or DP value to PX value, ensure the size unchanged
	 *
	 * @param dp
	 * @return
	 */
	public int dp2px(float dp) {
		return (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp, metrics);
//		final float scale = getDensity();
//		return (int) (dp * scale + 0.5f);
	}

	/**
	 * Converts the value of dip to PX or DP to ensure that the size of the same
	 * size is constant.
	 *
	 * @param px
	 * @return
	 */
	public int px2dp(float px) {
		final float scale = getDensity();
		return (int) (px / scale + 0.5f);
	}

	/**
	 * Convert a PX value to a SP value, which does not change the size of text
	 *
	 * @param pxValue （DisplayMetrics Class attribute scaledDensity）
	 * @return
	 */
	public int px2sp(float pxValue) {
		final float fontScale = metrics.scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * Convert a SP value to a PX value, which does not change the size of text
	 *
	 * @param spValue （DisplayMetrics Class attribute scaledDensity）
	 * @return
	 */
	public int sp2px(float spValue) {
		return (int) applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spValue, metrics);
//		final float fontScale = metrics.scaledDensity;
//		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * TypedValue官方源码中的算法，任意单位转换为PX单位
	 *
	 * @param unit    TypedValue.COMPLEX_UNIT_DIP
	 * @param value   对应单位的值
	 * @param metrics 密度
	 * @return px值
	 */
	private float applyDimension(int unit, float value,
								 DisplayMetrics metrics) {
		switch (unit) {
			case TypedValue.COMPLEX_UNIT_PX:
				return value;
			case TypedValue.COMPLEX_UNIT_DIP:
				return value * metrics.density;
			case TypedValue.COMPLEX_UNIT_SP:
				return value * metrics.scaledDensity;
			case TypedValue.COMPLEX_UNIT_PT:
				return value * metrics.xdpi * (1.0f / 72);
			case TypedValue.COMPLEX_UNIT_IN:
				return value * metrics.xdpi;
			case TypedValue.COMPLEX_UNIT_MM:
				return value * metrics.xdpi * (1.0f / 25.4f);
		}
		return 0;
	}

	public int getScreenWidth() {
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
		//return getDisplayMetrics(context).widthPixels;
	}

	public int getScreenHeight() {
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
		//return getDisplayMetrics(context).heightPixels;
	}

	/**
	 * 获得状态栏的高度
	 *
	 * @return
	 */
	public int getStatusHeight() {
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			return context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;
	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 *
	 * @param activity
	 * @return
	 */
	public Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth();
		int height = getScreenHeight();
		Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}

	public Rect getTextBounds(String text, float textSize) {
		Paint paint = new Paint();
		Rect bounds = new Rect();
		paint.setTextSize(textSize);
		paint.getTextBounds(text, 0, text.length(), bounds);
		return bounds;
	}

	public int getTextWidth(String text, float textSize) {
		return getTextBounds(text, textSize).width();
	}

	/**
	 * 获得这个View的宽度 测量这个view，最后通过getMeasuredWidth()获取宽度.
	 *
	 * @param view 要测量的view
	 * @return 测量过的view的宽度
	 */
	public int getViewWidth(View view) {
		measureView(view);
		return view.getMeasuredWidth();
	}

	/**
	 * 获得这个View的高度 测量这个view，最后通过getMeasuredHeight()获取高度.
	 *
	 * @param view 要测量的view
	 * @return 测量过的view的高度
	 */
	public int getViewHeight(View view) {
		measureView(view);
		return view.getMeasuredHeight();
	}

	/**
	 * 测量这个view 最后通过getMeasuredWidth()获取宽度和高度.
	 *
	 * @param view 要测量的view
	 * @return 测量过的view
	 */
	private void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,
					View.MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}
}
