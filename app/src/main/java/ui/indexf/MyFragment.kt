package ui.indexf


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import basebata.dao.User
import basebata.dao.UserDatabase
import basebata.http.RxRequest
import com.android.databinding.library.baseAdapters.BR
import com.pst.basebata.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import ui.R
import ui.databinding.FragmentTabBar2Binding

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      ui.indexf
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月10日 2:02 PM


 *描述:
--------------------------------------------------------------------------------------------*/


class MyFragment : BaseFragment<FragmentTabBar2Binding, BaseViewModel>() {

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_tab_bar_2
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initParam() {
        binding.tv.setOnClickListener(View.OnClickListener {
            var create = Observable.create(ObservableOnSubscribe<Long> {
                val add = context?.let { it1 -> UserDatabase.getInstance(it1)?.UserDao()?.add(User()) }
                if (add != null) {
                    it.onNext(add)
                    it.onComplete()
                }
            })
            RxRequest.request(create,{

            })

        })
    }

    override fun initData() {
        binding.tv
    }
}