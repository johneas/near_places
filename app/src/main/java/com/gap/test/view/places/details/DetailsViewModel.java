package com.gap.test.view.places.details;

import android.databinding.ObservableField;

/**
 *
 */
public class DetailsViewModel {

    public final ObservableField<String> placeName = new ObservableField<>("");
    public final ObservableField<String> contactPone = new ObservableField<>("");
    public final ObservableField<String> address = new ObservableField<>("");
    public final ObservableField<String> cityState = new ObservableField<>("");

}
