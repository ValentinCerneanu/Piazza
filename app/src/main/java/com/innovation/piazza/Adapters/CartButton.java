package com.innovation.piazza.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.innovation.piazza.Activities.SplashActivity;

public class CartButton extends ImageButton {

    public CartButton(final Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity;
                nextActivity = new Intent(context, SplashActivity.class);
                ((Activity) context).startActivity(nextActivity);
            }
        });
    }
}

