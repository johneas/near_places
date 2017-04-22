package com.gap.test.view.places.list;

import com.gap.test.view.places.list.adapter.PlaceAdapter;
import com.gap.test.view.common.interfaces.ViewModelView;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
class PLacesContract {

    public interface view extends ViewModelView<PlacesViewModel> {
        void setAdapter(PlaceAdapter adapter);
        void initRecyclerView();
        void displayMessageToUser(String message);
        void displayNoPlaces();
        void getCurrentLocation();
    }

    public interface presenter{
        void getFilterPlaces();
    }
}
