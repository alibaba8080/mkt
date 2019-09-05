package com.pst.basebata.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.PopupWindow;


/**
 * ----------------------------------------------------------------------------------------------
 * <p>
 * 项目: smart_door_user_android_app      com.dream.zrdx.dream.view
 * 创建: pst  邮箱：1274218999@lecent.com
 * 时间: 19-7-22 下午2:29
 * <p>
 * <p>
 * 描述:
 * ----------------------------------------------------------------------------------------------
 */

/***
 * PopupWindow基类
 *
 * @author pei
 * @version 1.0
 * @cretae 2016-7-21
 * @注：若要popwindow点击外部消失，则设置 this.setFocusable(true)
 *     若要popwindow点击外部不消失，不做setFocusable设置，也不要设置成this.setFocusable(false)
 *
 */
public abstract class BasePopupWindow extends PopupWindow {

    protected View mLayoutView;
    protected int mLayoutId;
    protected Context mContext;
    protected int mWidth;
    protected int mHeight;
    protected PopuWindowListener popuWindowListener;
    public BasePopupWindow(int width, int height, int layoutId, Context context) {
        this.mWidth = width;
        this.mHeight = height;
        this.mLayoutId = layoutId;
        this.mContext = context;
        mLayoutView = LayoutInflater.from(context).inflate(mLayoutId, null);
        setWindow();
    }

    /** PopupWindow基本设置 **/
    protected void setWindow() {
        this.setContentView(mLayoutView);
        this.setWidth(mWidth);
        this.setHeight(mHeight);
        // this.setFocusable(true);// 可点击
        // 实例化一个ColorDrawable颜色为半透明(半透明遮罩颜色代码#66000000)
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        this.setBackgroundDrawable(dw);
    }

    /** PopupWindow背景设置 **/
    protected void setBackground(int color) {
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(color);
        this.setBackgroundDrawable(dw);
    }

    protected abstract void initView();
    protected abstract void initData();
    public  void setListener(PopuWindowListener popuWindowListener){
        this.popuWindowListener=popuWindowListener;
    }

    /** PopupWindow点击间隙处理，根据实际情况重写 **/
    protected void onTouchdimiss() {
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mLayoutView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
//              int height = mLayoutView.getTop();
//              int y = (int) event.getY();
//              if (event.getAction() == MotionEvent.ACTION_UP) {
//                  if (y < height) {
//                      dismiss();
//                  }
//              }
                return false;
            }
        });
    }

    /**
     * 显示在控件正上方
     *
     * @param view
     *            依赖的控件
     * @param marginDp
     *            设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationTop(View view, float marginDp) {
        mLayoutView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int popupWidth = mLayoutView.getMeasuredWidth();
        int popupHeight = mLayoutView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight - dp2px(marginDp));
        update();
    }

    /**
     * 显示在控件正下方
     *
     * @param view
     *            依赖的控件
     * @param marginDp
     *            设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationGravityBottom(View view, float marginDp) {
        mLayoutView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int popupWidth = mLayoutView.getMeasuredWidth();
        int popupHeight = mLayoutView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, (location[0]+view.getWidth()/2)-popupWidth/2,
                location[1]+view.getHeight()+dp2px(marginDp));
        update();
    }

    /**显示在控件下方
     *
     * @param view 依赖的控件
     * @param marginDp  设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationBottom(View view, float marginDp){
        showAsDropDown(view, 0, dp2px(marginDp));
        update();
    }


    /**
     * 显示在控件左方
     *
     * @param view
     *            依赖的控件
     * @param marginDp
     *            设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationLeft(View view, float marginDp) {
        mLayoutView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int popupWidth = mLayoutView.getMeasuredWidth();
        int popupHeight = mLayoutView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] - popupWidth - dp2px(marginDp), (location[1] + view.getHeight() / 2) - popupHeight / 2);
        update();
    }

    /**
     * 显示在控件右方
     *
     * @param view
     *            依赖的控件
     * @param marginDp
     *            设置的间距(直接写数字即可，已经做过dp2px转换)
     */
    public void showAtLocationRight(View view, float marginDp) {
        mLayoutView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int popupWidth = mLayoutView.getMeasuredWidth();
        int popupHeight = mLayoutView.getMeasuredHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.NO_GRAVITY, location[0] + view.getWidth() + dp2px(marginDp), (location[1] + view.getHeight() / 2) - popupHeight / 2);
        update();
    }

    /** dp转px **/
    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, mContext.getResources().getDisplayMetrics());
    }
   public interface PopuWindowListener<T>{
        void mValue(T t);
   }
}



