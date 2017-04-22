package com.gap.test.view.places.search;

import android.databinding.ObservableField;

import com.gap.test.view.common.base.BaseViewModel;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
public class SearchViewModel extends BaseViewModel {

    public ObservableField<String> placeToFind = new ObservableField<>("");
    public ObservableField<Boolean> validate = new ObservableField<>(false);

}
