package basebata.binding;


import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ViewBindingAdapter {


//    /**
//     * 设置上拉加载  下拉刷新
//     * @param layout
//     * @param onRefreshCommand
//     * @param onLoadMoreCommand
//     */
//    @BindingAdapter(value = {"android:onRefreshCommand", "android:onLoadMoreCommand"}, requireAll = false)
//    public static void onRefreshAndLoadMoreCommands(SmartRefreshLayout layout, SwipeRefreshLayout.OnRefreshListener onRefreshCommand, OnLoadMoreListener onLoadMoreCommand) {
//
//        if (onRefreshCommand != null) {
//            layout.setOnRefreshListener(onRefreshCommand);
//        }
//
//        if (onLoadMoreCommand != null) {
//            layout.setOnLoadMoreListener(onLoadMoreCommand);
//        }
//
//    }
//


    @BindingAdapter({"render"})
    public static void loadHtml(WebView webView, final String html) {
        if (!TextUtils.isEmpty(html)) {
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }

    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .into(imageView);
        }
    }



}
