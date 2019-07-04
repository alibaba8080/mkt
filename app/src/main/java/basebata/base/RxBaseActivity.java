package basebata.base;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import com.andview.refreshview.XRefreshView;
import com.dream.zrdx.dream.R;
import com.dream.zrdx.dream.annotation.AutoArg;
import com.dream.zrdx.dream.util.GsonUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import view.LoadingView;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by goldze on 2017/6/15.
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity, 但是需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 */
public abstract class RxBaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements  XRefreshView.XRefreshViewListener {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private LoadingView loadingView=null;
    private Intent mIntent;
    private Bundle mBundle = new Bundle();
    private  long lastRefreshTime=new Date().getTime();

    public ImageView mBackImg;
    public ImageView mImgPicture;
    public TextView mTitleText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectBundle(getIntent().getExtras());

        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);

        if (findViewById(R.id.LoadingView) != null) {
            loadingView = (LoadingView) findViewById(R.id.LoadingView);
            loadingView.setText("数据加载中...");
        }
        if (findViewById(R.id.title_text) != null) {
            mTitleText = (TextView) findViewById(R.id.title_text);
        }
        if (findViewById(R.id.Img_picture) != null) {
            mImgPicture = (ImageView) findViewById(R.id.Img_picture);
        }
        if (findViewById(R.id.backImg) != null) {
            mBackImg = (ImageView) findViewById(R.id.backImg);
            mBackImg.setOnClickListener(v->{finish();});
        }
        if (findViewById(R.id.refresh) != null) {
            refresh = (XRefreshView) findViewById(R.id.refresh);
            refresh.setPullLoadEnable( false ); //上拉
            refresh.setPullRefreshEnable( true ); //下拉
            refresh.setPinnedContent( false ); //列表不滚动
            refresh.setAutoLoadMore( false ); //自动加载
            //设置刷新完成以后，headerview固定的时间
            refresh.setPinnedTime( 200 );
            // 设置上次刷新的时间
            refresh.restoreLastRefreshTime( lastRefreshTime );
            refresh.setXRefreshViewListener( this);
        }
        //页面数据初始化方法
        initView();
        initData() ;
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
        viewModelId = getViewModelId();
        viewModel = getViewModel();
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



    public abstract int getLayout();
    public abstract int getViewModelId();
    public abstract void initView() ;
    public abstract void initData() ;

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM getViewModel() {
        return null;
    }



    /**
     * 创建ViewModel
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }





    public RxBaseActivity startActivity(Class activity) {
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

    public RxBaseActivity withObject(@Nullable String key, @Nullable Object value) {
        String json = GsonUtils.toJson(value);
        mBundle.putString(key, json);
        return this;
    }

    public RxBaseActivity withString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }

    public RxBaseActivity withBoolean(@Nullable String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public RxBaseActivity withInt(@Nullable String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public RxBaseActivity withFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public RxBaseActivity withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public RxBaseActivity withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
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
        FragmentUtils.add(getSupportFragmentManager(), fragment, containerId, false, true);
    }

    public void addFragment(@NonNull Fragment fragment, int containerId, boolean isAddStack) {
        FragmentUtils.add(getSupportFragmentManager(), fragment, containerId, false, isAddStack);
    }

    public void replace(@NonNull Fragment fragment) {
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
    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefresh(boolean isPullDown) {
        lastRefreshTime=new Date().getTime();
        showDialog();
    }

    @Override
    public void onLoadMore(boolean isSilence) {

    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }
}
