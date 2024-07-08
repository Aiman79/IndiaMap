package com.digitalhorizons.indiamapapp.common.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class CalendarGridView extends GridView {
    public CalendarGridView(Context context) {
        super(context);
    }

    public CalendarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
    }
}
