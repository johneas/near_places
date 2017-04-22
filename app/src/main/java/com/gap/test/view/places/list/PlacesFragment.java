package com.gap.test.view.places.list;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gap.test.R;
import com.gap.test.data.model.Venue;
import com.gap.test.databinding.FragmentPlacesListBinding;
import com.gap.test.view.common.base.BaseFragment;
import com.gap.test.view.common.location.GPSTracker;
import com.gap.test.view.common.utils.Constants;
import com.gap.test.view.main.MainActivity;
import com.gap.test.view.places.details.DetailsDialog;
import com.gap.test.view.places.list.adapter.PlaceAdapter;
import com.gap.test.view.places.list.interfaces.OnItemClickListener;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */

public class PlacesFragment extends BaseFragment implements PLacesContract.view {

    private FragmentPlacesListBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentPlacesListBinding.inflate(inflater);
        binding.setModelView(new PlacesViewModel());
        binding.setPresenter(new PlacesPresenter(this));

        String searchPlace = getArguments().getString(Constants.PLACE_TO_SEARCH);
        binding.getModelView().placeToSearch.set(searchPlace);
        binding.getPresenter().getFilterPlaces();

        setTitle(String.format("%s %s", getString(R.string.near_places), searchPlace));
        setBacArrow(true);

        return binding.getRoot();

    }

    @Override
    public PlacesViewModel getViewModel() {
        return binding.getModelView();
    }

    @Override
    public void setViewModel(PlacesViewModel viewModel) {
        binding.setModelView(viewModel);
    }

    @Override
    public void setAdapter(PlaceAdapter adapter) {

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Venue place) {
                DetailsDialog dialog = DetailsDialog.newInstance(place);
                dialog.show(PlacesFragment.this.getFragmentManager(), PlacesFragment.class.getSimpleName());
            }
        });

        binding.recyclerList.setAdapter(adapter);
    }

    @Override
    public void initRecyclerView() {
        binding.recyclerList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.recyclerList.setLayoutManager(layoutManager);
    }

    @Override
    public void displayMessageToUser(String message) {
        displayMessage(message);
    }

    @Override
    public void displayNoPlaces(){
        displayMessageToUser(String.format("%s : %s", getString(R.string.no_data), getViewModel().placeToSearch.get()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.getPresenter().getCompositeDisposable().clear();
    }

    @Override
    public void getCurrentLocation(){

        GPSTracker gps = ((MainActivity)getActivity()).getGps();

        if(gps.canGetLocation()){
            Location myLocation = new Location(Constants.MY_LOCATION);
            myLocation.setLatitude(gps.getLatitude());
            myLocation.setLongitude(gps.getLongitude());

            getViewModel().myLocation.set(myLocation);
        }
    }

}
