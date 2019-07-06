package ui;

import android.widget.AbsListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pst.basebata.base.BaseFragment;
import com.pst.basebata.base.BaseViewModel;
import com.pst.basebata.view.MyRecyclerView;
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

    @Override
    public int getLayout() {
        return R.layout.recycler_fragment;
    }

    @Override
    public void initView() {
        List<String> stringList=new ArrayList<>();
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");
        stringList.add("54545");

        MyRecyclerView viewById = binding.getRoot().findViewById(R.id.MyTableView);
        viewById.setMyAdapter(mAdaper);
        mAdaper.addData(stringList);
        mAdaper.addData(stringList);
        mAdaper.addData(stringList);
        mAdaper.addData(stringList);
        viewById.setScrollListener(new MyRecyclerView.ScrollListener() {
            @Override
            public void startRefresh() {
                mAdaper.replaceData(stringList);
                viewById.finishRefresh();
            }

            @Override
            public void loadNextPage(int page) {
                mAdaper.addData(stringList);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public void initData() {

    }

    BaseQuickAdapter<String, BaseViewHolder> mAdaper=new BaseQuickAdapter<String, BaseViewHolder>(R.layout.recyclerview) {
        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    };
}
