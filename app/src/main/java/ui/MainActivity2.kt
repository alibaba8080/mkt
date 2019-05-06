package ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast


/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      habit.mvvm.koltlin1
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月06日 11:39 AM


 *描述:
--------------------------------------------------------------------------------------------*/


@SuppressLint("NewApi")
class MainActivity2 : AppCompatActivity() {


    var recyclerView: RecyclerView? = null
    var mDatas = ArrayList<String>()
    var recycleAdapter: RecycleAdapter? = null
    val recyclerScroll = RecyclerScroll()
    var autoCount=0
    lateinit var context:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(habit.mvvm.koltlin1.R.layout.activity_main2)
        context=this.applicationContext
        recyclerView = findViewById(habit.mvvm.koltlin1.R.id.recycleList)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.setOnScrollListener(recyclerScroll)

        recycleAdapter = RecycleAdapter(mDatas)
        recyclerView?.adapter = recycleAdapter
        loadMoreDate()
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

                val loading = false
                if (!loading && visibleItemCount + pastVisiblesItems >= totalItemCount) {
                    Toast.makeText(context,"正在加载....",Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({ loadMoreDate() }, 1000)
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

        }
    }

    private fun loadMoreDate() {
        autoCount+=15
        for (k in autoCount-15..autoCount) {
            mDatas.add("12345646---$k")
        }
        recycleAdapter?.setData(mDatas)
    }

}


