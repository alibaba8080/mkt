package basebata.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basebata.util.GsonUtils;
import basebata.util.annotation.AutoArg;
import com.trello.rxlifecycle2.components.support.RxFragment;
import ui.R;
import view.LoadingView;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * ----------------------------------------------------------------------------------------------
 * <p>
 * 项目: Koltlin1      PACKAGE_NAME
 * 创建: panshengtao  邮箱：1274218999@lecent.com
 * 时间: 2019年05月10日 10:06 AM
 * <p>
 * <p>
 * 描述:
 * --------------------------------------------------------------------------------------------
 */


public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment implements IBaseActivity {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    //    private MaterialDialog dialog;
    private LoadingView loadingView;
    private Intent mIntent;
    private Bundle mBundle = new Bundle();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        if ((binding.getRoot()).findViewById(R.id.LoadingView) != null) {
            loadingView = (LoadingView) (binding.getRoot()).findViewById(R.id.LoadingView);
            loadingView.setText("数据加载中...");
        }
        return binding.getRoot();
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
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
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectBundle(getArguments());
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();
        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);
        if (binding != null) {
            binding.unbind();
        }
    }
    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(android.support.v4.app.Fragment fragment, Class<T> cls) {
        return ViewModelProviders.of(fragment).get(cls);
    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

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
    public VM initViewModel() { return null;}

    public abstract void initParam();

    public abstract void initData();

    public  void initViewObservable(){};

    public BaseFragment startActivity(Class activity) {
        mIntent = new Intent(getContext(), activity);
        return this;
    }

    public void go() {
        if (mIntent != null) {
            mIntent.putExtras(mBundle);
            startActivity(mIntent);
        }
    }

    public BaseFragment withObject(@Nullable String key, @Nullable Object value) {
        String json = GsonUtils.toJson(value);
        mBundle.putString(key, json);
        return this;
    }

    public BaseFragment withString(@Nullable String key, @Nullable String value) {
        mBundle.putString(key, value);
        return this;
    }

    public BaseFragment withBoolean(@Nullable String key, boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    public BaseFragment withInt(@Nullable String key, int value) {
        mBundle.putInt(key, value);
        return this;
    }

    public BaseFragment withFloat(@Nullable String key, float value) {
        mBundle.putFloat(key, value);
        return this;
    }

    public BaseFragment withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
        mBundle.putIntegerArrayList(key, value);
        return this;
    }

    public BaseFragment withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
        mBundle.putStringArrayList(key, value);
        return this;
    }

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
//            logw(e.getMessage());
        }
    }
}
