package ui.indexf

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import basebata.base.BaseFragment
import basebata.base.BaseViewModel
import ui.BR
import ui.R

/**
----------------------------------------------------------------------------------------------

 *项目: mkt      ui.indexf
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月14日 5:24 PM


 *描述:
--------------------------------------------------------------------------------------------*/


class TextFrament : BaseFragment<ViewDataBinding, BaseViewModel>() {
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {

        return R.layout.test
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initParam() {
    }

    override fun initData() {
    }
}