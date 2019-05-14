package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.*;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ui.R;

public class LoadingView extends RelativeLayout {
	public boolean isStarting = false;

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();

	}

	public LoadingView(Context context) {
		super(context);
		init();
	}

	// 初始化
	public void init() {
		this.getBackground().setAlpha(229);
		this.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	// 设置文字
	public void setText(String text) {
		TextView loadingViewText = (TextView) this
				.findViewById(R.id.LoadingViewText);
		loadingViewText.setText(text);
	}

	// 开始动画
	public void startAnimation() {
		View loadingViewArc = this.findViewById(R.id.LoadingViewArc);
		if (!isStarting) {
			this.setVisibility(View.VISIBLE);

			AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
			alphaAnimation.setDuration(500);
			this.startAnimation(alphaAnimation);

			AnimationSet set = new AnimationSet(false);
			RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnimation.setInterpolator(new LinearInterpolator());
			rotateAnimation.setRepeatCount(-1);
			rotateAnimation.setDuration(1500);
			set.addAnimation(rotateAnimation);

			set.setFillAfter(true);
			loadingViewArc.startAnimation(set);

			isStarting = true;
		}
	}

	// 停止动画
	public void stopAnimation() {
		if (isStarting) {
			final View thisView = this;
			final View loadingViewArc = this
					.findViewById(R.id.LoadingViewArc);
			AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
			alphaAnimation.setDuration(500);
			alphaAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					thisView.setVisibility(View.GONE);
					loadingViewArc.clearAnimation();
				}
			});
			this.startAnimation(alphaAnimation);

			isStarting = false;
		}
	}
}
