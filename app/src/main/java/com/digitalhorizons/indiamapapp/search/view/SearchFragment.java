package com.digitalhorizons.indiamapapp.search.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.home.interfaces.ChangeFragmentListener;
import com.digitalhorizons.indiamapapp.search.viewmodel.SearchItemViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private SearchItemViewModel searchItemViewModel;
    private ChangeFragmentListener changeFragmentListener;
    private AppCompatEditText etSearch;
    private AppCompatImageView ivCancel;
    private RecyclerView rvSearchItems;
    private View view;
    private boolean isFirstView = false;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
            view = inflater.inflate(R.layout.fragment_search, container, false);
            registerViews(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFirstView){
            addListeners();
        }
        isFirstView = false;
    }

    public void registerViews(View view){
        etSearch = view.findViewById(R.id.et_search);
        ivCancel = view.findViewById(R.id.iv_cancel);
        rvSearchItems = view.findViewById(R.id.rv_search_items);

        searchItemViewModel = new ViewModelProvider(this).get(SearchItemViewModel.class);
    }

    public void addListeners(){
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setUpRecyclerView();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ivCancel.setOnClickListener(view1 -> {
            etSearch.setText("");
            searchItemViewModel.clearSearch();
        });
    }

    public void setChangeFragmentListener(ChangeFragmentListener changeFragmentListener){
        this.changeFragmentListener = changeFragmentListener;
    }


    public void setUpRecyclerView() {
        searchItemViewModel.getDataFromLocal();
        searchItemViewModel.setUpRecyclerView(rvSearchItems, LayoutInflater.from(requireContext()), this);
    }
}