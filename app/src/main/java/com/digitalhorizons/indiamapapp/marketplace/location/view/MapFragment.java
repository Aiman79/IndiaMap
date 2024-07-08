package com.digitalhorizons.indiamapapp.marketplace.location.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.home.interfaces.ChangeFragmentListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    private ChangeFragmentListener changeFragmentListener;
    private View view;
    private boolean isFirstView = false;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            isFirstView = true;
            view = inflater.inflate(R.layout.fragment_map, container, false);
            registerViews(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        init();
        if (isFirstView){
            addListeners();
            init();
        }
        isFirstView = false;
    }

    public void registerViews(View view) {
//        rvItems = view.findViewById(R.id.rv_items);
    }

    public void setChangeFragmentListener(ChangeFragmentListener changeFragmentListener) {
        this.changeFragmentListener = changeFragmentListener;
    }


    public void init() {
    }

    public void addListeners(){

    }
}