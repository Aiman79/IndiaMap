package com.digitalhorizons.indiamapapp.common.calendar;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CalenderSqureButton extends AppCompatButton {


    public CalenderSqureButton(Context context) {
        super(context);
    }

    public CalenderSqureButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public CalenderSqureButton(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
		/*int lWidth = getMeasuredWidth();

		int lHight = lWidth -  (lWidth/3);
		setMeasuredDimension(lWidth, lHight);*/
    }
}
