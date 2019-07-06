package ui.index

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.android.databinding.library.baseAdapters.BR
import com.pst.basebata.base.BaseActivity
import com.pst.basebata.base.BaseViewModel
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener
import ui.R
import ui.databinding.ActivityMainBinding
import ui.indexf.MyFragment


@Suppress("UNREACHABLE_CODE")
class MainActivity : BaseActivity<ActivityMainBinding, BaseViewModel>(){
    override fun getLayout(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mFragments: MutableList<Fragment>? = null


    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initParam() {
    }

    override fun initData() {
        //初始化Fragment
        initFragment()
        //初始化底部Button
        initBottomTab()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        mFragments!!.add(MyFragment())
        mFragments!!.add(MyFragment())
        mFragments!!.add(MyFragment())
        mFragments!!.add(MyFragment())
        //默认选中第一个
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frameLayout, mFragments!![0])
        transaction.add(R.id.frameLayout, mFragments!![1])
        transaction.add(R.id.frameLayout, mFragments!![2])
        transaction.add(R.id.frameLayout, mFragments!![3])

        transaction.hide(mFragments!![1])
        transaction.hide(mFragments!![2])
        transaction.hide(mFragments!![3])
        transaction.commitAllowingStateLoss()
    }

    private fun initBottomTab() {
        val navigationController = binding.pagerBottomTab.material()
            .addItem(R.mipmap.yingyong, "应用")
            .addItem(R.mipmap.huanzhe, "工作")
            .addItem(R.mipmap.xiaoxi_select, "消息")
            .addItem(R.mipmap.wode_select, "我的")
            .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
            .build()
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
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
