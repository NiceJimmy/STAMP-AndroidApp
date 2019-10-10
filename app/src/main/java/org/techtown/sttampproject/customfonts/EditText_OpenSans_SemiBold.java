package org.techtown.sttampproject.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditText_OpenSans_SemiBold extends EditText {

    public EditText_OpenSans_SemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditText_OpenSans_SemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_OpenSans_SemiBold(Context context) {
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