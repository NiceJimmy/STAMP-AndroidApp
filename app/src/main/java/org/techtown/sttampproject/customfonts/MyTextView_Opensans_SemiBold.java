package org.techtown.sttampproject.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class MyTextView_Opensans_SemiBold extends TextView {

    public MyTextView_Opensans_SemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView_Opensans_SemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView_Opensans_SemiBold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Semibold.ttf");
            setTypeface(tf);
        }
    }

}