package com.gap.test.view.places.list;

import android.databinding.ObservableField;
import android.location.Location;

import com.gap.test.data.model.Venue;
import com.gap.test.view.common.base.BaseViewModel;

import java.util.ArrayList;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */

public class PlacesViewModel extends BaseViewModel {
    ObservableField<String> placeToSearch = new ObservableField<>("");
    ObservableField<Location> myLocation = new ObservableField<>();
    public ObservableField<ArrayList<Venue>> venueItems = new ObservableField<>();
}
