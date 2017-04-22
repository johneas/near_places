package com.gap.test.view.places.search;

import android.os.Bundle;

import com.gap.test.view.common.base.BasePresenter;
import com.gap.test.view.common.utils.Constants;
import com.gap.test.view.places.list.PlacesFragment;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */

public class SearchPresenter extends BasePresenter implements SearchContract.presenter {

    private SearchContract.view view;

    SearchPresenter(SearchContract.view view) {
        this.view = view;
        view.getViewModel().validate.set(false);
    }

    @Override
    public void searchPlaceClicked() {
        view.getViewModel().isLoading.set(true);
        view.getViewModel().validate.set(true);

        if (view.getViewModel().placeToFind.get().isEmpty()) {
            return;
        }

        PlacesFragment searchPlacesFragment = new PlacesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PLACE_TO_SEARCH, view.getViewModel().placeToFind.get());
        searchPlacesFragment.setArguments(bundle);

        view.launchView(searchPlacesFragment);
    }

    @Override
    public void onPlaceNameChanged(CharSequence s) {
        if (s.length() > 0) {
            view.hideSnackBarOnActivity();
        }
    }
}
