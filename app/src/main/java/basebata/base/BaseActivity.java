package basebata.base;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import basebata.util.GsonUtils;
import basebata.util.annotation.AutoArg;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import ui.R;
import view.LoadingView;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by goldze on 2017/6/15.
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity, 但是需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseActivity {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private LoadingView loadingView;
    private Intent mIntent;
    private Bundle mBundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectBundle(getIntent().getExtras());
        //页面接受的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        //注册RxBus
        if (findViewById(R.id.LoadingView) != null) {
            loadingView = (LoadingView) findViewById(R.id.LoadingView);
            loadingView.setText("数据加载中...");
        }
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
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
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
    public void showDialog(String title) {
        if (loadingView.isStarting) {
            return;
        }
        // 开起加载动画
        loadingView.startAnimation();
    }

    public void dismissDialog() {
        loadingView.stopAnimation();
    }

    /**
     * 视图初始化
     **/
    @Override
    public void initParam() {

    }

    /**
     * 数据初始化
     */
    @Override
    public void initData() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    @Override
    public void initViewObservable() {

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


    protected abstract int getLayout();



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

    public BaseActivity withFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public BaseActivity withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public BaseActivity withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
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
}
