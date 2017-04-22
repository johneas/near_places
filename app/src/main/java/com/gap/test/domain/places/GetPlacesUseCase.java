package com.gap.test.domain.places;

import com.gap.test.data.service.PlacesService;
import com.gap.test.domain.UseCase;
import com.gap.test.view.common.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 21/04/2017.
 */
public class GetPlacesUseCase implements UseCase<String> {

    private String placeToSearch;

    public GetPlacesUseCase(String placeToSearch) {
        this.placeToSearch = placeToSearch;
    }


    PlacesService requestInterface = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PlacesService.class);

    @Override
    public String perform() {
        return null;
    }
}
