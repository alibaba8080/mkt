package ui.indexf


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import basebata.base.BaseFragment
import basebata.base.BaseViewModel
import com.android.databinding.library.baseAdapters.BR
import ui.R

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      ui.indexf
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月10日 2:02 PM


 *描述:
--------------------------------------------------------------------------------------------*/


class MyFragment : BaseFragment<ui.databinding.FragmentTabBar2Binding, BaseViewModel>() {

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_tab_bar_2
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initParam() {
    }

    override fun initData() {
    }
}