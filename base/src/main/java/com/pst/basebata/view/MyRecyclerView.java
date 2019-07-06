package com.pst.basebata.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.AbsListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pst.base.R;


public class MyRecyclerView extends PullRefreshView {
    public ScrollListener scrollListener = null;
    public RecyclerView recyclerView = null;
    private int page = 1;
    // 用于解决 加载下一页时候 频繁通知的问题
    public Boolean isLoading = false;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollListener getScrollListener() {
        return scrollListener;
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setMyAdapter(BaseQuickAdapter baseQuickAdapter) {
        recyclerView = (RecyclerView) findViewById(R.id.MyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(baseQuickAdapter);

        // 事件设定
        // 下拉刷新事件
        this.setRefreshListener(new RefreshListener() {

            @Override
            public void onRefresh(PullRefreshView view) {
                new android.os.Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (scrollListener != null) {
                            page = 1;
                            scrollListener.startRefresh();
                        }
                    }
                }, 3000);
            }
        });


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dy > 0)
                //向下滚动
                {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        if (scrollListener != null) {
                            if (!isLoading) {
                                isLoading = true;
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        isLoading = false;
                                    }
                                }, 1000);
                                scrollListener.loadNextPage(page++);
                            }
                        }
                    }
                }
            }
        });

    }

    // 结束刷新
    @Override
    public void finishRefresh() {
        isLoading = false;
        super.finishRefresh();
    }

    public interface ScrollListener {
        public void startRefresh();

        // 加载下一页
        public void loadNextPage(int page);


        // 行滚动
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }
}
