package ui;

import android.os.Handler;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pst.basebata.base.BaseFragment;
import com.pst.basebata.base.BaseViewModel;
import ui.databinding.RecyclerFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述：
 * 作者：pst
 * 邮箱：1274218999@qq.com
 * 创建时间：19-7-6 下午3:23
 * 更改时间：19-7-6
 * 版本号：1
 */
public class RecyclerFragment extends BaseFragment<RecyclerFragmentBinding, BaseViewModel> {
    List<String> stringList;

    @Override
    public int getLayout() {
        return R.layout.recycler_fragment;
    }

    @Override
    public void initView() {
        mNavGobackBtn.setVisibility(View.GONE);
        mNavTitleTv.setText("HOME");
        stringList = new ArrayList<>();
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        recyclerView.setBaseAdapter(mAdaper);
        mAdaper.replaceData(stringList);
    }

    public void loadNextPage(int page) {
        showDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdaper.addData(stringList);
                recyclerView.finishRefresh();
                dismissDialog();
            }
        }, 500);
    }

    public void startRefresh() {
        showDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdaper.replaceData(stringList);
                recyclerView.finishRefresh();
                dismissDialog();
            }
        }, 1000);
    }

    @Override
    public void initData() {

    }

    BaseQuickAdapter<String, BaseViewHolder> mAdaper = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.recyclerview) {
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.textv, "          " + helper.getPosition());
        }
    };
}
