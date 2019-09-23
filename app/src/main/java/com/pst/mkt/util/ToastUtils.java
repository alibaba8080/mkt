package com.pst.mkt.util;

import android.content.Context;
import android.support.annotation.NonNull;
import com.pst.basebata.util.Toasty;
import com.pst.mkt.MyApplication;

/**
 * <pre>
 *     author : ylw
 *     e-mail : bigoatsm@gmail.com
 *     time   : 18-11-15
 *     desc   : Toast工具
 * </pre>
 */
public class ToastUtils {
    // Toast类型
    public final static int NORMAL = 0x1;
    public final static int WARNING = 0x2;
    public final static int INFO = 0x3;
    public final static int SUCCESS = 0x4;
    public final static int ERROR = 0x5;

    /**
     * Toast消息, 重载即可自定义消息显示
     *
     * @param type 消息类型
     * @param msg 消息内容
     */
    public static void showToast( @NonNull int type, @NonNull String msg) {
         Context context=MyApplication.getInstance();
        switch (type) {
            case NORMAL:
                Toasty.normal(context, msg).show();
                break;

            case WARNING:
                Toasty.warning(context, msg).show();
                break;

            case INFO:
                Toasty.info(context, msg).show();
                break;

            case SUCCESS:
                Toasty.success(context, msg).show();
                break;

            case ERROR:
                Toasty.error(context, msg).show();
                break;

            default:
                Toasty.normal(context, msg).show();
                break;
        }
    }

    public static void normal( @NonNull String msg){
        Toasty.normal(MyApplication.getInstance(), msg).show();
    }
}
