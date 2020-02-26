package com.innovation.piazza.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

public class BackButton extends ImageButton {

    public BackButton(final Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
    }
}
