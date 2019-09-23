package com.pst.mkt.ui.indexf


import android.view.View
import basebata.dao.User
import basebata.dao.UserDatabase
import basebata.http.RxRequest
import com.pst.basebata.base.BaseFragment
import com.pst.basebata.base.BaseViewModel
import com.pst.mkt.R
import com.pst.mkt.databinding.FragmentTabBar2Binding
import com.pst.mkt.util.ToastUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      ui.indexf
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月10日 2:02 PM


 *描述:
--------------------------------------------------------------------------------------------*/


class MyFragment : BaseFragment<FragmentTabBar2Binding, BaseViewModel>() {
    override fun getLayout(): Int {
        return R.layout.fragment_tab_bar_2

    }

    override fun initView() {
        binding.tv.setOnClickListener(View.OnClickListener {
            var create = Observable.create(ObservableOnSubscribe<Long> {
                val add = context?.let { it1 -> UserDatabase.getInstance(it1)?.UserDao()?.add(User()) }
                if (add != null) {
                    it.onNext(add)
                    it.onComplete()
                }
            })
            RxRequest.request(create,{
                ToastUtils.normal(it.toString())
            })

        })
    }


    override fun initData() {
        binding.tv
    }
}