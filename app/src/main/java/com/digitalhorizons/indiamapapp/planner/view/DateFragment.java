package com.digitalhorizons.indiamapapp.planner.view;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;

import java.util.Calendar;

public class DateFragment extends Fragment {
    private CalendarView calendarView;
    private AppCompatTextView tvStartDate, tvEndDate;
    private View view;

    public DateFragment() {
        // Required empty public constructor
    }
    public static DateFragment newInstance() {
        DateFragment fragment = new DateFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view =  inflater.inflate(R.layout.fragment_date, container, false);
            registerViews(view);
            setUpCalendarView();
        }
        return view;
    }

    public void registerViews(View view){
        calendarView = view.findViewById(R.id.calendar_view);
        tvEndDate = view.findViewById(R.id.tv_end);
        tvStartDate = view.findViewById(R.id.tv_start);
    }

    public void setUpCalendarView(){
        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis();
        tvStartDate.setText(AppUtils.getDateInFormat(start, AppUtils.DATE_FORMAT_DD_MM_YYYY));

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        long end = calendar.getTimeInMillis();
        tvEndDate.setText(AppUtils.getDateInFormat(end, AppUtils.DATE_FORMAT_DD_MM_YYYY));

//        calendarView.s
    }
}