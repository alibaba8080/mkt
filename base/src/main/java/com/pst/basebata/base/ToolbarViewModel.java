package com.pst.basebata.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Create Author：goldze
 * Create Date：2019/01/03
 * Description： 对应include标题的ToolbarViewModel
 * Toolbar的封装方式有很多种，具体封装需根据项目实际业务和习惯来编写
 * 所有例子仅做参考,业务多种多样,可能我这里写的例子和你的需求不同，理解如何使用才最重要。
 */

public class ToolbarViewModel extends BaseViewModel {
    //标题文字
    public ObservableField<String> titleText = new ObservableField<>("");
    //右边文字
    public ObservableField<String> rightText = new ObservableField<>("更多");
    //右边文字的观察者
    public ObservableInt leftIconVisibleObservable = new ObservableInt(View.GONE);
    //右边文字的观察者
    public ObservableInt rightTextVisibleObservable = new ObservableInt(View.GONE);
    //右边图标的观察者
    public ObservableInt rightIconVisibleObservable = new ObservableInt(View.GONE);
    //右边图标
    public ObservableField<Drawable> rightIcon = new ObservableField<>();

    private Application application;
    private BaseFragment baseFragment;
    @SuppressLint("NewApi")
    public ToolbarViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void initData(BaseFragment baseFragment){
        this.baseFragment=baseFragment;
    };
    /**
     * 设置标题
     *
     * @param text 标题文字
     */
    public void setTitleText(String text) {
        titleText.set(text);
    }


    /**
     * 设置右边文字
     *
     * @param text 右边文字
     */
    public void setRightText(String text) {
        rightTextVisibleObservable.set(View.VISIBLE);
        rightText.set(text);
        rightText.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {

            }
        });
    }

    /**
     * 设置右边文字的显示和隐藏
     *
     * @param visibility
     */
    public void setRightTextVisible(int visibility) {
        rightTextVisibleObservable.set(visibility);
    }

    /**
     * 设置右边文字的显示和隐藏
     *
     * @param visibility
     */
    public void setLeftIcon(int visibility) {
        leftIconVisibleObservable.set(visibility);
    }

    /**
     * 设置右边图标
     */
    @SuppressLint("NewApi")
    public void setRightIcon(int id) {
        rightIconVisibleObservable.set(View.VISIBLE);
        rightIcon.set(application.getDrawable(id));
    }

    /**
     * 设置右边图标的显示和隐藏
     *
     * @param visibility
     */
    public void setRightIconVisible(int visibility) {
        rightIconVisibleObservable.set(visibility);
    }

    /**
     * 返回按钮的点击事件
     */
    public void backOnClick() {
//        baseFragment.finish();
    }

    /**
     * 右边文字的点击事件，子类可重写
     */
    public void rightTextOnClick() {
    }

    /**
     * 右边图标的点击事件，子类可重写
     */
    public void rightIconOnClick() {
    }


}
