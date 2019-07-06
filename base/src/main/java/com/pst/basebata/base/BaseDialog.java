package com.pst.basebata.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

public abstract class BaseDialog<Binding extends ViewDataBinding, ViewMode extends BaseViewModel> extends DialogFragment  {
    protected Binding bind;
    protected ViewMode vm;

    private Bundle mBundle;
    private int mFlags = -1;

    /**
     * 布局文件
     *
     * @return layoutId
     */
    public abstract @LayoutRes int myView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        bind = DataBindingUtil.inflate(inflater, myView(), container, false);

        vm = createModel();

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }




    /**
     * 创建ViewMode
     *
     * @return ViewMode
     */
    private ViewMode createModel() {
        try {
            ParameterizedType ptype = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class clazz = (Class<ViewMode>) ptype.getActualTypeArguments()[1];


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }


}
