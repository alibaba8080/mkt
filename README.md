#  Kotlin Rxjava+Retorfit


## Retorfit API

```java
interface Api {

    @GET("ff")
    fun demoGet(): Observable<String>

    @POST("action/apiv2/banner")
    fun demoPost(@Field("catalog") catalog: String): Observable<User>


    @GET("search/movie")
    fun searchMoviesAsync(@Query("query") query: String, @Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/{movie_id}/keywords")
    fun getKeywordsAsync(@Path("movie_id") id: Int): Observable<KeywordResponse>

}

```

## RetorfitClient

```java

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

```


## Rxjava

```java


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


```



## 使用1

```java


        val api = RetorfitClient.getInstance()!!.create(Api::class.java)

        RxRequest.request(api.searchMoviesAsync("a",1),{

            Toast.makeText(context, ""+it.results.size, Toast.LENGTH_SHORT).show()

        })

```


## 使用2

```java

       var room = Observable.create(ObservableOnSubscribe<ArrayList<User>> {
            var mdata = ArrayList<User>()
            autoCount = 15
            for (k in autoCount - 15..autoCount) {
                var user = User()
                user.firstName = "--$k"
                user.lastName = "$k--"
                UserDatabase.getInstance(this)!!.UserDao().add(user)
            }
            mdata = UserDatabase.getInstance(this)!!.UserDao().getAll() as ArrayList<User>
            it.onNext(mdata)
            it.onComplete()
        })

        RxRequest.request(room,{
            recycleAdapter?.setData(it)
        })

```