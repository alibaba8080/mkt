package basebata.binding;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import basebata.base.BaseFragment;

/**
 * @Description: fragment替换
 * @Author: 卢凤淦
 * @CreateDate: 19-1-16 下午2:45
 */
public class CommonOperations {
    private CommonOperations() {
    }



    /**
     * 加载Fragment,可以指定参数,不添加到回退栈
     *
     * @param manager
     * @param canonicalName
     * @param containerID
     * @param argBundle
     */
    public static void loadFragmentNoBackStackByReflection(FragmentManager manager, String canonicalName,
                                                           int containerID, Bundle argBundle) {
        try {
            Class<?> fragmentClass = Class.forName(canonicalName);
            BaseFragment fragment = (BaseFragment) fragmentClass.newInstance();
            if (argBundle != null) {
                fragment.setArguments(argBundle);
            }
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(containerID, fragment);
            transaction.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载Fragment,可以指定参数
     *
     * @param manager
     * @param canonicalName
     * @param containerID
     * @param argBundle
     */
    public static void loadFragmentByReflection(FragmentManager manager, String canonicalName,
                                                int containerID, Bundle argBundle) {
        try {
            Class<?> fragmentClass = Class.forName(canonicalName);
            BaseFragment fragment = (BaseFragment) fragmentClass.newInstance();
            if (argBundle != null) {
                fragment.setArguments(argBundle);
            }
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(containerID, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
