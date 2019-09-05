package com.pst.basebata.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.pst.base.R;
import com.pst.basebata.util.ScreenUtil;

/**
 * ----------------------------------------------------------------------------------------------
 * <p>
 * 项目: smart_door_user_android_app      com.dream.zrdx.dream.view
 * 创建: pst  邮箱：1274218999@lecent.com
 * 时间: 19-7-22 下午2:44
 * <p>
 * <p>
 * 描述:
 * ----------------------------------------------------------------------------------------------
 */
public class PeoplePopuWindow extends BasePopupWindow{
    private  String defaultValue;
    private Context mContext;
    private RecyclerView leftRecycler;
    private RecyclerView rightRecycler;
    private int selectedPosition = 0;
    public PeoplePopuWindow(int width, Context context, String defaultValue) {

        super(width, ScreenUtil.dp2px(context,150), R.layout.public_title_layout, context);
        this.defaultValue=defaultValue;
        initView();
        initData();
    }


    @Override
    protected void initView() {
        setAnimationStyle(R.style.popuwindow_middle_style);
        setFocusable(true);
//        AppCompatTextView householdPopulation = mLayoutView.findViewById(R.id.popuwinddow_householdPopulation);
//        AppCompatTextView permanentResidents = mLayoutView.findViewById(R.id.popuwinddow_permanentResidents);
//        AppCompatTextView floatingPopulation = mLayoutView.findViewById(R.id.popuwinddow_floatingPopulation);


    }

    @Override
    protected void initData() { }




}
