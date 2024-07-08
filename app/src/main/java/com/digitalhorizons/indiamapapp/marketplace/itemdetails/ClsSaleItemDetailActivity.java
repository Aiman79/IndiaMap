package com.digitalhorizons.indiamapapp.marketplace.itemdetails;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.digitalhorizons.indiamapapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ClsSaleItemDetailActivity extends AppCompatActivity implements ImageSliderAdapter.ClickedListener{
    private ViewPager image_view_pager;
    private TabLayout tabLayout;
    private AppCompatTextView tv_buy,tv_rent; //tv_description
    private ExpandableTextviewLines tv_description;
    private RecyclerView rv_property;
    private ConstraintLayout cl;
    private AppCompatImageView iv_slider, iv_back;
    private List<PropertyPojo> propertyPojoList;
    private PropertyAdapter propertyAdapter;
    private ImageSliderAdapter.ClickedListener clickedListener;
    String demoText ="Lorem ipsum dolor sit amet, consectetur" +
            " Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor " +
            "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
            "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_sale_item_detail);
        initializeAll();
        setImageSlider();
        setPropertyRecyclerView();
        onClick();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favorite) {
            item.setIcon(R.drawable.ic_favorite_red);
        }
        return super.onOptionsItemSelected(item);
    }
    private void setImageSlider(){
        try {
            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(this,  this);
            image_view_pager.setAdapter(imageSliderAdapter);
            tabLayout.setupWithViewPager(image_view_pager,true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    void setPropertyRecyclerView(){
        try {
            propertyPojoList = new ArrayList<>();
            propertyPojoList.add(new PropertyPojo("Restaurant"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Restaurant"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Restaurant"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Restaurant"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Restaurant"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Restaurant"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            propertyPojoList.add(new PropertyPojo("Restaurant"));
            propertyPojoList.add(new PropertyPojo("Apartment"));
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rv_property.setLayoutManager(layoutManager);
            rv_property.setHasFixedSize(true);
            propertyAdapter = new PropertyAdapter( this,propertyPojoList);
            rv_property.setAdapter(propertyAdapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    private void onClick(){
        tv_description.setText(demoText);
        makeTextViewResizable(tv_description, 3, "More", true);
//        if(tv_description.getLineCount()>3){
//            tv_description.setText(demoText.length()-6 + "...more");
//        }
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_buy.setBackgroundResource(R.drawable.bg_btn_clicked);
                Toast.makeText(ClsSaleItemDetailActivity.this, "Buy Clicked and Changed Color !", Toast.LENGTH_SHORT).show();
            }
        });
        tv_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_rent.setBackgroundResource(R.drawable.bg_btn_clicked);
                Toast.makeText(ClsSaleItemDetailActivity.this, "Rent Clicked and Changed Color !", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initializeAll(){
        try {
            image_view_pager = findViewById(R.id.image_view_pager);
            tabLayout = findViewById(R.id.tabLayout);
            tv_description = findViewById(R.id.tv_description);
            rv_property = findViewById(R.id.rv_property);
            tv_buy = findViewById(R.id.tv_buy);
            tv_rent = findViewById(R.id.tv_rent);
            cl = findViewById(R.id.cl);
            iv_slider = findViewById(R.id.iv_slider);
            iv_back = findViewById(R.id.iv_back);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onPictureClicked(int position) {
        cl.setVisibility(View.VISIBLE);
        iv_slider.setImageResource(ImageSliderAdapter.imageArray[position]);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cl.setVisibility(View.GONE);
            }
        });
    }
    public static void makeTextViewResizable(TextView tv, int maxLine,String expandText,boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()),
                            tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                } else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore), TextView.BufferType.SPANNABLE);
                }
            }
        });
    }

    private static SpannableStringBuilder addClickablePartTextViewResizable( Spanned strSpanned, final TextView tv,
                                                         int maxLine, final String spannableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spannableText)) {
            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, " Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 3, "... More", true);
                    }
                }
            }, str.indexOf(spannableText), str.indexOf(spannableText) + spannableText.length(), 0);
        }
        return ssb;
    }
}