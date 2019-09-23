package com.pst.basebata.http;

import android.support.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public abstract class Converter<T extends Result> implements Callback<T> {
    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        try {
            T  t = response.body();
            assert t != null;
            if (t.code == 0) {
                onSuccess(t);
            } else {
                onError(t.message, response);
            }
        } catch (Exception e) {
            onError("返回数据异常请联系管理员处理", response);
        }

    }

    @Override
    public  void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
//        onError("请求异常请联系管理员处理\n(" + throwable.toString() + ")", null);
        onError("网络请求异常",null);
    }

    public abstract void onSuccess(T t);

    protected abstract void onError(String err, Response <T> resp);
}
