package com.pst.mkt.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class MyToast {
    private static final String TAG = "csx";
    private static Context mContext;
    public static void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void printLog(String message) {
        Log.e(TAG,message);
    }

}
