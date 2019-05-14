package ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import basebata.dao.User

/**
----------------------------------------------------------------------------------------------

 *项目: Koltlin1      ui
 *创建: panshengtao  邮箱：1274218999@lecent.com
 *时间: 2019年05月06日 11:50 AM


 *描述:
--------------------------------------------------------------------------------------------*/


class RecycleAdapter(var mDatas: ArrayList<User>) : RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    var viewId: Int = R.layout.recyclerview
    override fun getItemViewType(position: Int): Int {
        if (position in 2..4) {
            viewId = R.layout.recyclerview
            return viewId
        } else {
            viewId = R.layout.recyclerview2
            return viewId
        }
    }

    fun setData( mDatas: ArrayList<User>){
        this.mDatas=mDatas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        var v = LayoutInflater.from(p0.context).inflate(viewId as Int, p0, false)
        return ViewHolder(v, p1)
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if(mDatas.size==0)return
        p0.textView?.text = mDatas.get(p1).uid.toString()
    }

    class ViewHolder(itemView: View, p1: Int) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null

        init {
            textView = itemView.findViewById(R.id.textv)
        }
    }


}