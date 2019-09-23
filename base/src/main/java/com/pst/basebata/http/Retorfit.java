package com.pst.basebata.http;

import android.support.annotation.NonNull;
import okhttp3.*;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Retorfit {
    private static final Retorfit ourInstance = new Retorfit();

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .retryOnConnectionFailure( true ) //链接失败时重试
            .addInterceptor( sLoggingInterceptor )  //添加拦截器
            .connectTimeout( 10, TimeUnit.SECONDS ) //链接超时
            .readTimeout( 10, TimeUnit.SECONDS )  //设置读取超时
            .build();
    private Retrofit retrofit = new Retrofit.Builder()
            .client( okHttpClient )
            .baseUrl("")
            .addConverterFactory( GsonConverterFactory.create() ) //支持Gson解析
            .addCallAdapterFactory( RxJava2CallAdapterFactory.create() ) //异步拓展
            .build();
    public static Retorfit getInstance() {
        return ourInstance;
    }

    private Retorfit() {
    }
    public static ApiService getRetrofit() {
        return  getInstance().retrofit.create( ApiService.class );
    }

    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            final Request request = chain.request();
            Buffer requestBuffer = new Buffer();
            if (request.body() != null) {
                request.body().writeTo( requestBuffer );
            }
            Response response = chain.proceed( request );
            assert response.body() != null;
            String content = response.body().string();

            String urlStr = (request.body() != null ? _parseParams( request.body(), requestBuffer ) : "");
            MediaType mediaType = response.body().contentType();
            return response.newBuilder()
                    .body( ResponseBody.create( mediaType, content ) )
                    .build();
        }
    };
    /**
     * 解析参数
     */
    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !Objects.requireNonNull( body.contentType() ).toString().contains( "multipart" )) {
            return URLDecoder.decode( requestBuffer.readUtf8(), "UTF-8" );
        }
        return "null";
    }
}
