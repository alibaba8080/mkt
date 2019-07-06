package com.pst.basebata.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.pst.base.R;

import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressLint("SimpleDateFormat")
public class PullRefreshView extends LinearLayout {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    enum Status {
        NORMAL, DRAGGING, REFRESHING,
    }

    private Status status = Status.NORMAL;

    private final static String REFRESH_RELEASE_TEXT = "释放后立即刷新";
    private final static String REFRESH_DOWN_TEXT = "下拉可以刷新";
    private final static float MIN_MOVE_DISTANCE = 5.0f;// 最小移动距离，用于判断是否在下拉，设置为0则touch事件的判断会过于平凡。具体值可以根据自己来设定

    private Scroller scroller;
    private View refreshView;
    private ImageView refreshIndicatorView;
    private int refresh_arrow_up;
    private int refresh_arrow_down;

    private int refreshTargetTop = -50;
    private ProgressBar bar;
    private TextView downTextView;
    private TextView timeTextView;

    private RefreshListener refreshListener;// 刷新监听器

    // 需要用到的文字引用
    private String downCanRefreshText;
    private String releaseCanRefreshText;

    private int lastY;
    private Context mContext;


    public PullRefreshView(Context context) {
        super(context);
        mContext = context;
    }

    public PullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * 初始化
     *
     * @throws @since 1.0.0
     * @MethodDescription init
     */
    private void init() {
        // TODO Auto-generated method stub
        refreshTargetTop = getPixelByDip(mContext, refreshTargetTop);
        // 滑动对象，
        scroller = new Scroller(mContext);
        // 刷新视图顶端的的view
        refreshView = LayoutInflater.from(mContext).inflate(R.layout.public_refresh_top_item, null);
        // 指示器view
        refreshIndicatorView = (ImageView) refreshView.findViewById(R.id.RefreshIndicator);
        refresh_arrow_up = R.mipmap.public_refresh_arrow_up;
        refresh_arrow_down = R.mipmap.public_refresh_arrow_down;

        // 刷新bar
        bar = (ProgressBar) refreshView.findViewById(R.id.RefreshProgress);
        // 下拉显示text
        downTextView = (TextView) refreshView.findViewById(R.id.RefreshHint);
        // 下来显示时间
        timeTextView = (TextView) refreshView.findViewById(R.id.RefreshTime);
        LayoutParams lp = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                -refreshTargetTop);
        lp.topMargin = refreshTargetTop;
        lp.gravity = Gravity.CENTER;
        addView(refreshView, lp);
        // //文字资源可以归档在资源集中，此处为了方便。
        downCanRefreshText = REFRESH_DOWN_TEXT;
        releaseCanRefreshText = REFRESH_RELEASE_TEXT;
        refreshTimeBySystem();
        // refreshTime = "1989-12-24 12:12:12";// 可以从保存文件中取得上次的更新时间
        // if (refreshTime != null) {
        // setRefreshTime(refreshTime);
        // }
    }

    //设置白色主题
    public void setRefreshThemeWhite() {
        setRefreshThemeWhite(Color.WHITE);
    }

    public void setRefreshThemeWhite(@ColorInt int bgColor) {
        refreshView.setBackgroundColor(bgColor);
        downTextView.setTextColor(Color.BLACK);
        timeTextView.setTextColor(Color.BLACK);
        refresh_arrow_up = R.mipmap.public_refresh_arrow_up_white;
        refresh_arrow_down = R.mipmap.public_refresh_arrow_down_white;
    }

    /**
     * 设置刷新后的内容
     *
     * @param time
     * @throws @since 1.0.0
     * @MethodDescription setRefreshText
     */
    private void setRefreshText(String time) {
        timeTextView.setText(time);
    }

    public boolean isRefreshing() {
        return status == Status.REFRESHING;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (status == Status.REFRESHING)
            return false;

        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录下y坐标
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // y移动坐标
                int m = y - lastY;
                doMovement(m);
                // 记录下此刻y坐标
                this.lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                fling();
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        // layout截取touch事件
        int action = e.getAction();
        int y = (int) e.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // y移动坐标
                int m = y - lastY;
                // 记录下此刻y坐标
                this.lastY = y;
                if (m > MIN_MOVE_DISTANCE && canScroll()) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    /**
     * up事件处理
     */
    private void fling() {
        // TODO Auto-generated method stub
        LayoutParams lp = (LayoutParams) refreshView.getLayoutParams();

        if (lp.topMargin > 0) {// 拉到了触发可刷新事件
            status = Status.REFRESHING;
            refresh();
        } else {
            status = Status.NORMAL;
            returnInitState();
        }
    }

    private void returnInitState() {
        // TODO Auto-generated method stub
        LayoutParams lp = (LayoutParams) this.refreshView.getLayoutParams();
        int i = lp.topMargin;
        scroller.startScroll(0, i, 0, refreshTargetTop);
        invalidate();
    }

    /**
     * 执行刷新
     *
     * @throws @since 1.0.0
     * @MethodDescription refresh
     */
    private void refresh() {
        // TODO Auto-generated method stub
        LayoutParams lp = (LayoutParams) this.refreshView.getLayoutParams();
        int i = lp.topMargin;
        refreshIndicatorView.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        downTextView.setVisibility(View.GONE);
        scroller.startScroll(0, i, 0, 0 - i);
        invalidate();
        if (refreshListener != null) {
            refreshListener.onRefresh(this);
        }
    }

    /**
     *
     */
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {// scroll 动作还未结束
            int i = this.scroller.getCurrY();
            LayoutParams lp = (LayoutParams) this.refreshView.getLayoutParams();
            int k = Math.max(i, refreshTargetTop);
            lp.topMargin = k;
            this.refreshView.setLayoutParams(lp);
            postInvalidate();
        }
    }

    /**
     * 下拉move事件处理
     *
     * @param moveY
     */
    private void doMovement(int moveY) {
        status = Status.DRAGGING;
        LayoutParams lp = (LayoutParams) refreshView.getLayoutParams();
        float f1 = lp.topMargin;
        float f2 = moveY * 0.3F;// 以0.3比例拖动
        int i = (int) (f1 + f2);
        // 修改上边距
        lp.topMargin = i;
        // 修改后刷新
        refreshView.setLayoutParams(lp);
        refreshView.invalidate();
        invalidate();

        timeTextView.setVisibility(View.VISIBLE);
        downTextView.setVisibility(View.VISIBLE);
        refreshIndicatorView.setVisibility(View.VISIBLE);
        bar.setVisibility(View.GONE);
        if (lp.topMargin > 0) {
            downTextView.setText(releaseCanRefreshText);
            refreshIndicatorView.setImageResource(refresh_arrow_up);
        } else {
            downTextView.setText(downCanRefreshText);
            refreshIndicatorView.setImageResource(refresh_arrow_down);
        }

    }

    /**
     * 设置刷新时间
     *
     * @param refreshTime
     * @throws @since 1.0.0
     * @MethodDescription setRefreshTime
     */
    public void setRefreshTime(String refreshTime) {
        timeTextView.setText("最后更新:" + refreshTime);
    }

    /**
     * 设置监听
     *
     * @param listener
     * @throws @since 1.0.0
     * @MethodDescription setRefreshListener
     */
    public void setRefreshListener(RefreshListener listener) {
        this.refreshListener = listener;
    }

    /**
     * 刷新时间
     *
     * @param
     */
    private void refreshTimeBySystem() {
        String dateStr = dateFormat.format(new Date());// 可以将时间保存起来
        this.setRefreshText("最后更新：" + dateStr);

    }

    /**
     * 结束刷新事件
     */
    public void finishRefresh() {
        LayoutParams lp = (LayoutParams) this.refreshView.getLayoutParams();
        int i = lp.topMargin;
        refreshIndicatorView.setVisibility(View.VISIBLE);// 下拉箭头显示
        timeTextView.setVisibility(View.VISIBLE);// 时间控件
        downTextView.setVisibility(VISIBLE);// 下拉提示语控件
        refreshTimeBySystem();// 修改时间；
        bar.setVisibility(GONE);
        scroller.startScroll(0, i, 0, refreshTargetTop);
        invalidate();
        status = Status.NORMAL;
    }

    private boolean isRefreshAble = true;


    public void setRefreshAble(boolean canRefresh) {
        isRefreshAble = canRefresh;
    }

    /**
     * 此方法兼容两种子布局的判断，listview，scrollview 主要作用是判断两个子View是否滚动到了最上面，若是，则表示此次touch
     * move事件截取然后让layout来处理，来移动下拉视图，反之则不然
     *
     * @return
     * @throws @since 1.0.0
     * @MethodDescription canScroll
     */
    private boolean canScroll() {
        // TODO Auto-generated method stub
        if (!isRefreshAble) {
            return false;
        }
        try {
            View childView;
            if (getChildCount() > 1) {
                childView = this.getChildAt(1);

                if (childView instanceof ListView) {
                    int top = ((ListView) childView).getChildAt(0).getTop();
                    int pad = ((ListView) childView).getListPaddingTop();
                    if ((Math.abs(top - pad)) < 3 && ((ListView) childView).getFirstVisiblePosition() == 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (childView instanceof ScrollView) {
                    if (((ScrollView) childView).getScrollY() == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }

            }
        } catch (Exception e) {
        }

        return false;
    }

    public static int getPixelByDip(Context c, int pix) {
        float f1 = c.getResources().getDisplayMetrics().density;
        float f2 = pix;
        return (int) (f1 * f2 + 0.5F);
    }

    /**
     * 刷新监听接口
     *
     * @author Nono
     */
    public interface RefreshListener {
        public void onRefresh(PullRefreshView view);
    }

}
