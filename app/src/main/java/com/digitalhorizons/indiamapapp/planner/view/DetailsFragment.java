package com.digitalhorizons.indiamapapp.planner.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.planner.model.TripModel;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class DetailsFragment extends Fragment {

    private View view;
    private Spinner spn_country, spn_city;
    private AppCompatTextView tvStart, tvEnd;
    private AppCompatEditText etTitle, etDesc;// etCity;
    private  String[] city = {"Select city","Araria", "Supaul","Patna","Gaya","Aara","Chhapra","Others"};
    private  String[] country = {"Select country","India", "Nepal","China","America","Japan","Austreliya","Others"};

    private MaterialDatePicker<Pair<Long, Long>> materialDatePicker;
    private MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder;

    //values
    private String startDate, endDate, title, desc, selectedCountry, selectedCity;

    public DetailsFragment() {
        // Required empty public constructor
    }
    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeAll();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_details, container, false);
        registerViews();
        return view;
    }

    public void registerViews(){
        try {
            spn_country = view.findViewById(R.id.spn_country);
            spn_city = view.findViewById(R.id.spn_city);
            tvStart = view.findViewById(R.id.tv_start);
            tvEnd = view.findViewById(R.id.tv_end);
            etTitle = view.findViewById(R.id.et_trip_title);
            etDesc = view.findViewById(R.id.et_trip_description);
//            etCity = view.findViewById(R.id.et_city);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void initializeAll(){
       setUpCountrySpinner();
       setUpDatePicker();
    }

    public void setUpCountrySpinner(){
        // spinner country
        ArrayAdapter adapter= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, country);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_country.setAdapter(adapter);

        // spinner city
        ArrayAdapter adapterCity= new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, city);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_city.setAdapter(adapterCity);
    }

    public void setUpDatePicker(){
//        MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        // handle select date button which opens the
        // material design date picker

        materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDatePicker = materialDateBuilder.build();

        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder.setTitleText("SELECT A DATE");

        tvStart.setOnClickListener(
                v -> materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER"));

        tvEnd.setOnClickListener(
                v -> materialDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER"));

        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            Pair<Long, Long> dates = (Pair) selection;
            startDate = AppUtils.getDateInFormat(dates.first, AppUtils.DATE_FORMAT_DD_MM_YYYY);
            endDate = AppUtils.getDateInFormat(dates.second, AppUtils.DATE_FORMAT_DD_MM_YYYY);

            tvStart.setText(getString(R.string.start_date).concat(startDate));
            tvEnd.setText(getString(R.string.start_date).concat(endDate));
        });
    }

    public TripModel getValues(){
        TripModel tripModel = null;
        title = etTitle.getText().toString();
        desc = etDesc.getText().toString();
        selectedCountry = spn_country.getSelectedItem().toString();
        selectedCity = spn_city.getSelectedItem().toString();
//        city = etCity.getText().toString();

       if (title.isEmpty()){
           etTitle.setError(getString(R.string.error_title));
       } else if (desc.isEmpty()){
           etDesc.setError(getString(R.string.error_desc));
       } else if (selectedCountry.isEmpty()){
           Snackbar.make(spn_country, getString(R.string.error_country), BaseTransientBottomBar.LENGTH_SHORT).show();
       } else if (selectedCity.isEmpty()){
           Snackbar.make(spn_city, getString(R.string.error_city), BaseTransientBottomBar.LENGTH_SHORT).show();
//           etCity.setError(getString(R.string.error_city));
       } else {
           tripModel = new TripModel();
           tripModel.setTitle(title);
           tripModel.setDesc(desc);
           tripModel.setCountry(selectedCountry);
           tripModel.setCity(selectedCity);
           tripModel.setStartDate(startDate);
           tripModel.setEndDate(endDate);
       }

        return tripModel;
    }
}