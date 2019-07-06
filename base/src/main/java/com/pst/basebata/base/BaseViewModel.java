package com.pst.basebata.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleObserver;
import android.support.annotation.NonNull;

/**
 * ----------------------------------------------------------------------------------------------
 * <p>
 * 项目: Koltlin1      BaseData.base
 * 创建: panshengtao  邮箱：1274218999@lecent.com
 * 时间: 2019年05月10日 10:28 AM
 * <p>
 * <p>
 * 描述:
 * --------------------------------------------------------------------------------------------
 */


public class BaseViewModel extends AndroidViewModel implements LifecycleObserver {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }
}
