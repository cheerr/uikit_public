package com.conglai.uikit.config;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenwei on 14/12/30.
 */
public class UITypeface {

    /**
     * 默认为系统字体
     */
    public static Typeface DEFAULT = Typeface.DEFAULT;

    /**
     * 将字体缓存起来作为全局变量
     */
    private static Map<String, Typeface> map = new HashMap<>();

    /**
     * 字体放置于assets文件夹下
     *
     * @param tf
     * @return
     */
    public static Typeface getTypeFace(Context context, String tf) {
        putTypeFace(context, tf);
        return map.get(tf) == null ? DEFAULT : map.get(tf);
    }

    private static void putTypeFace(Context context, String tf) {
        if (map.get(tf) == null)
            map.put(tf, Typeface.createFromAsset(context.getAssets(), tf));
    }

    /**
     * 为应用设置默认字体
     *
     * @param tf
     */
    public static void setDefault(Context context, String tf) {
        DEFAULT = getTypeFace(context, tf);
    }
}
