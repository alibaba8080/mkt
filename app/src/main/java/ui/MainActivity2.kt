package ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import basebata.dao.User
import basebata.dao.UserDatabase
import basebata.http.Api
import basebata.http.RetorfitClient
import basebata.http.RxRequest
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import java.util.*

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月06日 11:39 AM


 *描述:
--------------------------------------------------------------------------------------------*/


@SuppressLint("NewApi")
class MainActivity2 : AppCompatActivity() {

    val handler = Handler() {

        if (it.what == 1) {


        }

        false
    }
    var recyclerView: RecyclerView? = null
    var mDatas = ArrayList<User>()
    var recycleAdapter: RecycleAdapter? = null
    val recyclerScroll = RecyclerScroll()
    var autoCount = 0
    var loading = false


    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        context = this.applicationContext
        recyclerView = findViewById(R.id.recycleList)

        recyclerView?.layoutManager = GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false)
        recyclerView?.setOnScrollListener(recyclerScroll)

        recycleAdapter = RecycleAdapter(mDatas)
        recyclerView?.adapter = recycleAdapter

        loadMoreDate()
        val api = RetorfitClient.getInstance()!!.create(Api::class.java)

        RxRequest.request(api.searchMoviesAsync("a",1),{

            Toast.makeText(context, ""+it.results.size, Toast.LENGTH_SHORT).show()

        })
    }


    inner class RecyclerScroll : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val mLinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            if (dy > 0)
            //向下滚动
            {
                val visibleItemCount = mLinearLayoutManager.getChildCount()
                val totalItemCount = mLinearLayoutManager.getItemCount()
                val pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition()

                if (!loading && visibleItemCount + pastVisiblesItems >= totalItemCount) {
                    loading = true

                    Handler().postDelayed({
                        loadMoreDate()
                        loading = false
                    }, 1000)
                }
            }

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

        }
    }

    @SuppressLint("CheckResult")
    private fun loadMoreDate() {


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
    }

}


