package basebata.http

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      BaseData.http
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月08日 3:01 PM


 *描述:
--------------------------------------------------------------------------------------------*/


class RetorfitClient {

    val retrofit: Retrofit? = Retrofit.Builder()
        .client(provideHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://api.themoviedb.org/3/")
        .build()

    companion object {
        var INSTANCE: RetorfitClient? = null
        fun getInstance(): RetorfitClient? {
            if (INSTANCE == null)
                synchronized(RetorfitClient::class) {
                    INSTANCE = RetorfitClient()
                }
            return INSTANCE!!
        }
    }


    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)!!
    }


    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(StethoInterceptor())
            .addInterceptor(HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger { message -> Timber.e(message) }).apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}


