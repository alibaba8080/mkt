package basebata.http

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      BaseData.http
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月08日 3:22 PM


 *描述:
--------------------------------------------------------------------------------------------*/


class RxRequest {


    companion object {
        @SuppressLint("CheckResult")
        fun <T> request(
            service: Observable<T>,
            c: ((T) -> Unit),
            e: ((Throwable) -> Unit)? = null,//可不传
            n: (() -> Unit)? = null//可不传
        ) {
            service.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    //回调数据
                    c.invoke(it)
                }, {
                    //错误回调
                    e?.invoke(it)
                }, {
                    //完成时回调
                    n?.invoke()
                })
        }
    }
}