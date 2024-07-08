package com.digitalhorizons.indiamapapp.marketplace.itemdetails;

import android.annotation.SuppressLint;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.digitalhorizons.indiamapapp.R;

public class MySpannable extends ClickableSpan {

    private boolean isUnderline = true;

    public MySpannable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(isUnderline);
//        ds.setColor(Color.parseColor("#1b76d3"));
        ds.setColor(R.color.orange);
    }

    @Override
    public void onClick(View widget) {

    }
}
