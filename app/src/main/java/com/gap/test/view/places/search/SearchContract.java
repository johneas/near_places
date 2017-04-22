package com.gap.test.view.places.search;

import com.gap.test.view.common.base.BaseFragment;
import com.gap.test.view.common.interfaces.ViewModelView;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
class SearchContract {

    public interface view extends ViewModelView<SearchViewModel> {
        void launchView(BaseFragment baseFragment);
        void hideSnackBarOnActivity();
    }

    public interface presenter{
        void searchPlaceClicked();
        void onPlaceNameChanged(CharSequence s);
    }
}
