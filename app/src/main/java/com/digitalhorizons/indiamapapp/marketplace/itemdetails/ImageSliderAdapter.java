package com.digitalhorizons.indiamapapp.marketplace.itemdetails;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.digitalhorizons.indiamapapp.R;

public class ImageSliderAdapter extends PagerAdapter {
    Context context;
    ClickedListener clickedListener;
    public static int[] imageArray = new int[]{R.drawable.img1,R.drawable.food,R.drawable.img,R.drawable.food};
    public ImageSliderAdapter(Context context, ClickedListener clickedListener) {
        this.context = context;
        this.clickedListener = clickedListener;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setContentDescription("ImageView" +position);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(imageArray[position]);
        container.addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedListener.onPictureClicked(position);
//                Toast.makeText(context, position + "nd image clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return imageView;
    }
    public void setOnClickListener(ClickedListener clickedListener){
        this.clickedListener = clickedListener;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
    @Override
    public int getCount() {
        return imageArray.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public interface ClickedListener {
        void onPictureClicked(int position);
    }
}
