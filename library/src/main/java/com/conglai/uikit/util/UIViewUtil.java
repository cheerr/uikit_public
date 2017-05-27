package com.conglai.uikit.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chenwei on 15/1/5.
 */
public class UIViewUtil {

    public static void setVisible(View view, int resId) {
        if (view != null && view.findViewById(resId) != null) {
            view.findViewById(resId).setVisibility(View.VISIBLE);
        }
    }

    public static void setGone(View view, int resId) {
        if (view != null && view.findViewById(resId) != null) {
            view.findViewById(resId).setVisibility(View.GONE);
        }
    }

    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight,
                    View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0,
                    View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }
}