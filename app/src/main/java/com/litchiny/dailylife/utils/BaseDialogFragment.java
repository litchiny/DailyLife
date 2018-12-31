package com.litchiny.dailylife.utils;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.lang.reflect.Field;

/**
 * author: LL .
 * date:   On 2018/6/6
 * caption:
 */


public class BaseDialogFragment extends DialogFragment {
    private static final String TAG = "BaseDialogFragment";

    public void show(Activity activity) {
        show(activity.getFragmentManager(), TAG);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        Class<?> cls = DialogFragment.class;
        try {
            Field f = cls.getDeclaredField("mDismissed");
            f.setAccessible(true);//为 true 则表示反射的对象在使用时取消 Java 语言访问检查
            f.set(this, false);
            f = cls.getDeclaredField("mShownByMe");
            f.setAccessible(true);//为 true 则表示反射的对象在使用时取消 Java 语言访问检查
            f.set(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.remove(this);
        ft.add(this, tag);
        // 这里吧原来的commit()方法换成了commitAllowingStateLoss()
        ft.commitAllowingStateLoss();
    }
}
