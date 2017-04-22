package com.gap.test.view.places.list;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.gap.test.BuildConfig;
import com.gap.test.data.model.ResponseVenues;
import com.gap.test.data.model.Venue;
import com.gap.test.data.service.PlacesService;
import com.gap.test.view.common.base.BasePresenter;
import com.gap.test.view.common.utils.Constants;
import com.gap.test.view.places.list.adapter.PlaceAdapter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
public class PlacesPresenter extends BasePresenter implements PLacesContract.presenter {

    private static final String TAG = PlacesPresenter.class.getSimpleName();

    private PLacesContract.view view;
    private CompositeDisposable compositeDisposable;

    CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    PlacesPresenter(PLacesContract.view view) {
        this.view = view;
        view.initRecyclerView();
        compositeDisposable = new CompositeDisposable();
        view.getCurrentLocation();
    }

    @Override
    public void getFilterPlaces() {
        view.getViewModel().isLoading.set(true);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        /*This part must be in a generic retrofit factory class*/

        if (BuildConfig.DEBUG) {
            okHttpClient.networkInterceptors().add(new StethoInterceptor());
        }

        PlacesService requestInterface = new Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PlacesService.class);

        //********************************************************

        compositeDisposable.add(requestInterface.getVenues(view.getViewModel().placeToSearch.get(), Constants.TOKEN, Constants.VERSION )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseVenues>() {
                    @Override
                    public void accept(ResponseVenues responseVenues) throws Exception {
                        PlacesPresenter.this.handleResponse(responseVenues);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable error) throws Exception {
                        PlacesPresenter.this.handleError(error);
                        view.getViewModel().isLoading.set(false);
                    }
                }));

    }

    /**
     * Manage the result, create a list with places or display a message with no data
     *
     * @param responseVenues Retrofit response
     */
    private void handleResponse(ResponseVenues responseVenues) {
        if(responseVenues.getResponse().getVenues().size() == 0){
            view.displayNoPlaces();
            return;
        }

        ArrayList<Venue> mAndroidArrayList = new ArrayList<>(responseVenues.getResponse()
                .getVenues());
        PlaceAdapter mAdapter = new PlaceAdapter(mAndroidArrayList, view.getViewModel().myLocation.get());
        view.setAdapter(mAdapter);
        view.getViewModel().isLoading.set(false);
    }

    private void handleError(Throwable error) {
        view.displayMessageToUser(error.getMessage());
        Log.e(TAG, error.getMessage());
    }

}
