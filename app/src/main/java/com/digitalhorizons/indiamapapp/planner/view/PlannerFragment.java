package com.digitalhorizons.indiamapapp.planner.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.common.customrecycler.interfaces.OnItemClickedListener;
import com.digitalhorizons.indiamapapp.home.interfaces.ChangeFragmentListener;
import com.digitalhorizons.indiamapapp.planner.model.TripModel;
import com.digitalhorizons.indiamapapp.planner.viewmodel.PlannerFragmentViewModel;
import com.digitalhorizons.indiamapapp.common.utils.AppUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlannerFragment extends Fragment {
    //viewpager banners
    private RecyclerView rvTrips;
    private FloatingActionButton fabAdd;
    private ChangeFragmentListener changeFragmentListener;
    private PlannerFragmentViewModel plannerFragmentViewModel;
    private View view;
    private boolean isFirstView = false;

    public PlannerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static PlannerFragment newInstance() {
        PlannerFragment fragment = new PlannerFragment();
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
            view = inflater.inflate(R.layout.fragment_planner, container, false);
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
        rvTrips = view.findViewById(R.id.rv_trips);
        fabAdd = view.findViewById(R.id.fab_add);

        plannerFragmentViewModel = new ViewModelProvider(this).get(PlannerFragmentViewModel.class);
        plannerFragmentViewModel.setLayoutInflater(LayoutInflater.from(requireContext()));
        plannerFragmentViewModel.setLifecycleOwner(this);
        plannerFragmentViewModel.getDataFromLocal();
    }

    public void setChangeFragmentListener(ChangeFragmentListener changeFragmentListener) {
        this.changeFragmentListener = changeFragmentListener;
    }


    public void init() {
        setUpRecyclerView();
    }

    public void addListeners(){
        fabAdd.setOnClickListener(view -> startAddTripActivity());
    }

    public void startAddTripActivity(){
        Intent intent = new Intent(requireContext(), ClsAddTripActivity.class);
        activityResultLauncherAddTrip.launch(intent);
    }

    public ActivityResultLauncher<Intent> activityResultLauncherAddTrip = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result != null){
                    Intent intent = result.getData();
                    if (intent != null){
                        if (intent.getExtras() != null){
                            TripModel tripModel = (TripModel) intent.getExtras().getParcelable(AppUtils.DATA_TRIP);
                            if (tripModel != null){
                                plannerFragmentViewModel.addItem(tripModel);
                            }
                        }
                    }
                }
            });

    public void startTripDetailActivity(TripModel tripModel){
        Intent intent = new Intent(requireContext(), ClsTripDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppUtils.DATA_TRIP, tripModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setUpRecyclerView(){
        plannerFragmentViewModel.setUpRecyclerView(rvTrips);
        plannerFragmentViewModel.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void OnItemClicked(RecyclerView.ViewHolder holder) {

            }

            @Override
            public void OnItemClicked(int pos) {
                startTripDetailActivity(plannerFragmentViewModel.getTripsWithPos(pos));
            }
        });
    }
}