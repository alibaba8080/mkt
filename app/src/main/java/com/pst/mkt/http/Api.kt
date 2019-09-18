package basebata.http

import basebata.dao.User
import com.enesgemci.mymovies.data.model.KeywordResponse
import io.reactivex.Observable
import com.pst.mkt.model.MovieResponse
import retrofit2.http.*

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      BaseData.http
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月08日 3:14 PM


 *描述:
--------------------------------------------------------------------------------------------*/


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