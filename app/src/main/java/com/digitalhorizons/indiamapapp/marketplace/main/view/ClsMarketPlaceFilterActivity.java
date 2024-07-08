package com.digitalhorizons.indiamapapp.marketplace.main.view;

import static com.google.android.material.slider.LabelFormatter.LABEL_GONE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.marketplace.main.viewmodel.FilterViewModel;
import com.google.android.material.slider.RangeSlider;

import java.util.List;

public class ClsMarketPlaceFilterActivity extends AppCompatActivity {

    private RecyclerView rvColors;
    private FilterViewModel filterViewModel;

    //range slider for price
    private RangeSlider rangeSliderPrice;
    private AppCompatTextView tvMaxPrice, tvMinPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_market_place_filter);

        registerViews();
        setUpRangeSliderForCostDistance();
        addListener();
        setUpRecyclerView();
    }

    public void registerViews() {
        rvColors = findViewById(R.id.rv_colors);
        tvMaxPrice = findViewById(R.id.tv_min_price);
        tvMinPrice = findViewById(R.id.tv_max_price);
        rangeSliderPrice = findViewById(R.id.rang_price);

        filterViewModel = new ViewModelProvider(this).get(FilterViewModel.class);
        filterViewModel.getDataFromLocal();
    }

    public void setUpRangeSliderForCostDistance() {
        rangeSliderPrice.setCustomThumbDrawable(ContextCompat.getDrawable(this, R.drawable.circle_dark_primary_holo));
        rangeSliderPrice.setThumbRadius(20);
        rangeSliderPrice.setTrackInactiveTintList(ContextCompat.getColorStateList(this, R.color.colorGrayLight));
        rangeSliderPrice.setTrackHeight(10);
        rangeSliderPrice.setLabelBehavior(LABEL_GONE);
    }

    public void addListener() {
        rangeSliderPrice.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> values = slider.getValues();
                int min = values.get(0).intValue();
                int max = values.get(1).intValue();

                tvMaxPrice.setText("max: $" + max);
                tvMinPrice.setText("min: $" + min);
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> values = slider.getValues();
                int min = values.get(0).intValue();
                int max = values.get(1).intValue();

                tvMaxPrice.setText("max: $" + max);
                tvMinPrice.setText("min: $" + min);
            }
        });
    }

    public void setUpRecyclerView(){
        filterViewModel.setUpRecyclerView(rvColors, this);
        filterViewModel.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {
//                startMarketPlaceDetailActivity(marketplaceFragmentViewModel.getMarketPlaceItemWithPos(pos));
            }
        });
    }
}