package com.example.jorunal_bishe.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;


/**
 * Picture tools
 *
 * @author
 */
public class BitmapUtil {

	/**
	 * 缩放/裁剪图片
	 *
	 * @param bm
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap zoomImg(Bitmap bm, double newWidth, double newHeight) {
		if (newWidth == 0 || newHeight == 0 || bm == null) {
			return bm;
		}
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
	}

	/**
	 * 通过路径生成Base64文件
	 *
	 * @param path
	 * @return
	 */
	public static String getBase64FromPath(String path) {
		try {
			File file = new File(path);
			byte[] buffer = new byte[(int) file.length() + 100];
			int length = new FileInputStream(file).read(buffer);
			return Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把bitmap转换成base64
	 *
	 * @param bitmap
	 * @param bitmapQuality
	 * @return
	 */
	public static String getBase64FromBitmap(Bitmap bitmap, int bitmapQuality) {
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
		byte[] bytes = bStream.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	/**
	 * 把base64转换成bitmap
	 *
	 * @param string
	 * @return
	 */
	public static Bitmap getBitmapFromBase64(String string) {
		try {
			byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
			return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过文件路径获取到bitmap
	 *
	 * @param filePath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 * @author andrew
	 */
	public static Bitmap getSDCardBitmap(String filePath, int reqWidth,
										 int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		BitmapFactory.decodeFile(filePath, options);
		if (options.mCancel || options.outWidth == -1
				|| options.outHeight == -1) {
			return null;
		}
		int realWidth = options.outWidth;
		int realHeight = options.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (realWidth > reqWidth || realHeight > reqHeight) {
			// 缩放
			scaleWidth = ((float) realWidth) / reqWidth;
			scaleHeight = ((float) realHeight) / reqHeight;
		}
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inSampleSize = (int) Math.max(scaleWidth, scaleHeight);
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		WeakReference<Bitmap> weak = new WeakReference<>(BitmapFactory.decodeFile(filePath,
				options));
		return Bitmap.createScaledBitmap(weak.get(), reqWidth, reqHeight, true);
	}

	/**
	 * 在图片上添加文字
	 *
	 * @param context
	 * @param resId
	 * @param text
	 * @return
	 */
	public static Bitmap drawTextToBitmap(Context context,
										  int resId,
										  String text) {
		Resources resources = context.getResources();
		float scale = DensityUtil.getInstance(context).getDensity();
		Bitmap bitmap =
				BitmapFactory.decodeResource(resources, resId);

		bitmap = zoomImg(bitmap, 0, 0);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap.Config bitmapConfig =
				bitmap.getConfig();

		// set default bitmap config if none
		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.ARGB_8888;
		}
		// resource bitmaps are imutable,
		// so we need to convert it to mutable one
		bitmap = bitmap.copy(bitmapConfig, true);

		Canvas canvas = new Canvas(bitmap);
		// new antialised Paint
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.GRAY);
		paint.setTextSize(12*scale);
		paint.setDither(true); //获取跟清晰的图像采样
		paint.setFilterBitmap(true);//过滤一些
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		canvas.drawText(text, width / 2 - (bounds.right - bounds.left) / 2,
				height / 2 + (bounds.bottom - bounds.top) / 2 + 5, paint);
		return bitmap;
	}
}
