package com.example.jorunal_bishe.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 带边框的EditText
 */
public class BorderEditText extends EditText {

	private Paint paint;
	private RectF rectF;

	public BorderEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		rectF = new RectF(2, 0, this.getWidth() - 2, this.getHeight() - 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setStrokeWidth(1);
		paint.setStyle(Style.STROKE);
		paint.setColor(android.graphics.Color.GRAY);
		paint.setAntiAlias(true);
		canvas.drawRoundRect(rectF, 8, 8, paint);
	}
}
