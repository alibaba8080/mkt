package com.pst.basebata.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.pst.base.R;


@SuppressLint("DrawAllocation")
public class ArcView extends View {

	private Context mContext = null;
	private float startAngle = 0;
	private float endAngle = 360;
	private float borderWidth = 2;
	private int borderColor = Color.BLACK;

	public ArcView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}




	@SuppressLint("Recycle")
	public ArcView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					R.styleable.ArcView);
			startAngle = a.getFloat(R.styleable.ArcView_arcStartAngle,
					startAngle);
			endAngle = a.getFloat(R.styleable.ArcView_arcEndAngle, endAngle);
			borderWidth = a.getFloat(R.styleable.ArcView_arcBorderWidth,
					borderWidth);
			borderColor = a.getColor(R.styleable.ArcView_arcBorderColor,
					borderColor);
		}
	}

	public ArcView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int stockWidth = dip2px(mContext, borderWidth);
		int width = super.getMeasuredWidth() - stockWidth / 2;

		// 画笔定义
		Paint p = new Paint();
		p.setColor(borderColor);
		p.setAntiAlias(true);
		p.setStrokeWidth(stockWidth);
		p.setStyle(Paint.Style.STROKE);

		// 画内容弧度

		RectF oval = new RectF(stockWidth / 2, stockWidth / 2, width, width);

		canvas.drawArc(oval, startAngle, endAngle - startAngle, false, p);

	}


	// px 与 dip 转换
	public int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
