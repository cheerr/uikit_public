package com.conglai.uikit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by chenwei on 15/11/1.
 */
public class FeatureEditText extends EditText {
    public FeatureEditText(Context context) {
        super(context);
        init();
    }

    public FeatureEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FeatureEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //setTypeface(UITypeface.getTypeFace(getContext(), UITypeface.TypeFace.Lantinghei));
    }
}
