package com.gap.test.data.service;


import com.gap.test.data.model.ResponseVenues;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */

public interface PlacesService {

    @GET("/v2/venues/search")
    Observable<ResponseVenues> getVenues(
            @Query("near") String pkCountry,
            @Query("oauth_token") String pkRegion,
            @Query("v") String version);
}
