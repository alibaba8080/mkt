package com.pst.basebata.base;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pst.base.R;
import com.pst.basebata.annotation.AutoArg;
import com.pst.basebata.util.FragmentUtils;
import com.pst.basebata.util.GsonUtils;
import com.pst.basebata.view.LoadingView;
import com.pst.basebata.view.MyRecyclerView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by goldze on 2017/6/15.
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity, 但是需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements MyRecyclerView.ScrollListener {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private LoadingView loadingView = null;
    private Intent mIntent;
    private Bundle mBundle = new Bundle();

    public LinearLayout mNavBackground;
    public ImageView mNavGobackBtn;
    public TextView mNavTitleTv;
    public TextView mNavRightTv;
    public ImageView mNavRightIv;

    private MyRecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏 设置视图
        hideStatusBar();
        //私有的初始化Databinding和ViewModel方法
        injectBundle(getIntent().getExtras());
        initViewDataBinding(savedInstanceState);
        if (findViewById(R.id.LoadingView) != null) {
            loadingView = (LoadingView) findViewById(R.id.LoadingView);
            loadingView.setText("数据加载中...");
        }

        if ((binding.getRoot()).findViewById(R.id.my_recycler_view)!=null) {
            recyclerView=binding.getRoot().findViewById(R.id.my_recycler_view);
            recyclerView.setScrollListener(this);
        }

        if (findViewById(R.id.nav_background) != null) {
            mNavBackground = (LinearLayout) findViewById(R.id.nav_background);
        }
        if (findViewById(R.id.nav_title_tv) != null) {
            mNavTitleTv = (TextView) findViewById(R.id.nav_title_tv);
        }
        if (findViewById(R.id.nav_goback_btn) != null) {
            mNavGobackBtn = (ImageView) findViewById(R.id.nav_goback_btn);
            mNavGobackBtn.setOnClickListener(v->{
               finish();
            });
        }
        if (findViewById(R.id.nav_right_tv) != null) {
            mNavRightTv = (TextView) findViewById(R.id.nav_right_tv);
        }
        if (findViewById(R.id.nav_right_iv) != null) {
            mNavRightIv = (ImageView) findViewById(R.id.nav_right_iv);
        }
        //页面数据初始化方法
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);

        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, getLayout());
        viewModelId = getBRId();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        binding.setLifecycleOwner(this);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }
    public void showDialog() {
        if (loadingView!=null&&!loadingView.isStarting) {
            loadingView.startAnimation();
        }
    }

    public void dismissDialog() {
        if (loadingView!=null&&loadingView.isStarting) {
            loadingView.stopAnimation();
        }
    }



    protected abstract int getLayout();
    protected abstract int getBRId();
    protected abstract void initView() ;
    protected abstract void initData() ;


    /**
     * 创建ViewModel
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }


    public BaseActivity startActivity(Class activity) {
        mIntent = new Intent(this, activity);
        return this;
    }

    public void go() {
        if (mIntent != null) {
            mIntent.putExtras(mBundle);
            startActivity(mIntent);
        }
    }

    public void goForResult(int requestCode) {
        if (mIntent != null) {
            mIntent.putExtras(mBundle);
            startActivityForResult(mIntent, requestCode);
        }
    }

    public void goSetResult(int resultCode) {
        mIntent = new Intent();
        mIntent.putExtras(mBundle);
        setResult(resultCode, mIntent);
        finish();
    }

    public BaseActivity withObject(@Nullable String key, @Nullable Object value) {
        String json = GsonUtils.toJson(value);
        mBundle.putString(key, json);
        return this;
    }

    public BaseActivity withString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }

    public BaseActivity withBoolean(@Nullable String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public BaseActivity withInt(@Nullable String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }
    public BaseActivity withDouble(@Nullable String key, @Nullable Double value) {
        mBundle.putDouble(key, value);
        return this;
    }
    public BaseActivity withFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }


    /**
     * TODO Roadmap
     * <p>
     * 1. ARoute 跳转
     * 2. Fragment 替换操作支持 ARoute uri地址
     */


    private void injectBundle(Bundle bundle) {
        if (bundle != null) {
            injectBundle(this, bundle);
        }

    }

    private void injectBundle(Object o, Bundle bundle) {
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean annotationPresent = field.isAnnotationPresent(AutoArg.class);
                if (annotationPresent) {
                    field.setAccessible(true);

                    Object value = bundle.get(field.getName());
                    if (value instanceof String) {
                        String str = (String) value;
                        try {
                            Object obj = GsonUtils.fromJson(str, field.getType());

                            field.set(o, obj);
                        } catch (Exception e) {
                            field.set(o, str);
                        }

                    } else {
                        field.set(o, value);
                    }

                }
            }
        } catch (Exception e) {
            Log.e("injectBundleException", e.getMessage());
        }
    }
    public void addFragment(@NonNull Fragment fragment, int containerId) {
        fragment.setArguments(getIntent().getExtras());
        FragmentUtils.add(getSupportFragmentManager(), fragment, containerId, false, true);
    }

    public void addFragment(@NonNull Fragment fragment, int containerId, boolean isAddStack) {
        fragment.setArguments(getIntent().getExtras());
        FragmentUtils.add(getSupportFragmentManager(), fragment, containerId, false, isAddStack);
    }

    public void replace(@NonNull Fragment fragment) {
        fragment.setArguments(getIntent().getExtras());
        FragmentUtils.replace(FragmentUtils.getTopShow(getSupportFragmentManager()), fragment);
    }

    public void back() {
        FragmentUtils.pop(getSupportFragmentManager());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            // 双击退出
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (FragmentUtils.getFragments(getSupportFragmentManager()).size()<=1) {
                    finish();
                }else {
                    back();
                }
                return true;
            }
        } catch (Exception ignored) {
        }
        return super.onKeyDown( keyCode, event );
    }


    private void hideStatusBar() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void startRefresh() {

    }

    @Override
    public void loadNextPage(int page) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
