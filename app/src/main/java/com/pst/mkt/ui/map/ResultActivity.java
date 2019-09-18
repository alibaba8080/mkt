package com.pst.mkt.ui.map;


import com.pst.basebata.annotation.AutoArg;
import com.pst.basebata.base.BaseActivity;
import com.pst.basebata.base.BaseViewModel;
import com.pst.mkt.R;
import com.pst.mkt.databinding.ResultActivityBinding;

/**
 * 文件描述：扫一扫 查一查 结果界面
 * 作者：pst
 * 邮箱：1274218999@qq.com
 * 创建时间：19-7-3 下午2:49
 * 更改时间：19-7-3
 * 版本号：1
 */
public class ResultActivity extends BaseActivity<ResultActivityBinding, BaseViewModel> {

    @AutoArg
    private String type;//1，默认，2 地图
    @Override
    public void initView() {

    }

    @Override
    public void initData() {

//          addFragment(new ResultMapNavigationFragment(), R.id.frame);

    }

    @Override
    public int getLayout() {
        return R.layout.result_activity;
    }

    @Override
    public int getBRId() {
        return 0;
    }


}
