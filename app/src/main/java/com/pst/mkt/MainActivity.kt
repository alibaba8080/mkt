package com.pst.mkt

import android.graphics.Color
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import com.android.databinding.library.baseAdapters.BR
import com.pst.basebata.base.BaseActivity
import com.pst.basebata.base.BaseViewModel
import com.pst.mkt.databinding.ActivityMainBinding
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener
import com.pst.mkt.ui.indexf.RecyclerFragment
import com.pst.mkt.ui.indexf.ChangeSkinActivity
import com.pst.mkt.ui.indexf.MyFragment


@Suppress("UNREACHABLE_CODE")
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(){
    override fun getBRId(): Int {
        return BR.viewModel
    }

    override fun initView() {
        var changeskin = binding.root.findViewById<CardView>(R.id.menu_left_layout_cd_changeskin)
        changeskin.setOnClickListener { startActivity(ChangeSkinActivity::class.java).go()}
    }

    override fun getLayout(): Int {
        return R.layout.activity_main

    }

    private var mFragments: MutableList<Fragment>? = null


    override fun initData() {
        //初始化Fragment
        initFragment()
        //初始化底部Button
        initBottomTab()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        mFragments!!.add(RecyclerFragment())
        mFragments!!.add(MyFragment())
        mFragments!!.add(Fragment())
        mFragments!!.add(MyFragment())
        mFragments!!.add(MyFragment())
        //默认选中第一个
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, mFragments!![0])
        transaction.add(R.id.frameLayout, mFragments!![1])
        transaction.add(R.id.frameLayout, mFragments!![2])
        transaction.add(R.id.frameLayout, mFragments!![3])
        transaction.add(R.id.frameLayout, mFragments!![4])

        transaction.hide(mFragments!![1])
        transaction.hide(mFragments!![2])
        transaction.hide(mFragments!![3])
        transaction.hide(mFragments!![4])
        transaction.commitAllowingStateLoss()
    }

    private fun initBottomTab() {
        val navigationController = binding.pagerBottomTab.material()
            .addItem(R.mipmap.yingyong, "应用")
            .addItem(R.mipmap.huanzhe, "工作")
            .addItem(R.mipmap.ic_back_white, "")
            .addItem(R.mipmap.xiaoxi_select, "消息")
            .addItem(R.mipmap.wode_select, "我的")
            .setDefaultColor(Color.GRAY)
            .build()
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
                if (index==2)return;
                swithFragment(index)
            }

            override fun onRepeat(index: Int) {}
        })
    }

    private fun swithFragment(id: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        for (k in mFragments!!.indices) {
            if (k == id) {
                transaction.show(mFragments!![id])
            } else {
                transaction.hide(mFragments!![k])
            }
        }
        transaction.commitAllowingStateLoss()
    }
}
