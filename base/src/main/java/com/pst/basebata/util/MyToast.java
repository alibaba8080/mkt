package com.pst.basebata.util;

import android.util.Log;
import android.widget.Toast;

public class MyToast {
    private static final String TAG = "csx";

//    public static void showToast(String message) {
//        Toast.makeText(InitApp.getContext(), message, Toast.LENGTH_SHORT).show();
//    }

    public static void printLog(String message) {
        Log.e(TAG,message);
    }

}
