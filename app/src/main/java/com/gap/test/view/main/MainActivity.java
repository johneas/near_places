package com.gap.test.view.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gap.test.R;
import com.gap.test.view.common.base.BaseActivity;
import com.gap.test.view.common.base.BaseFragment;
import com.gap.test.view.common.location.GPSTracker;
import com.gap.test.view.common.utils.Constants;
import com.gap.test.view.places.search.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */
public class MainActivity extends BaseActivity{

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinator_layout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Snackbar snackbar;
    private GPSTracker gps;

    public GPSTracker getGps() {
        return gps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        gps = new GPSTracker(this);

        setSupportActionBar(toolbar);

        goToSearchPlaces();
        checkPermissions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gps.stopUsingGPS();
        gps = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Launch search fragment
     */
    private void goToSearchPlaces(){
        loadFragment(new SearchFragment(), Constants.FRAG_SEARCH_PLACES);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(Constants.FRAG_RESULT_PLACES);
        if(fragment != null){
            goToSearchPlaces();
        }else{
            super.onBackPressed();
        }
    }

    /**
     * Launch fragment view
     *
     * @param fragment fragment to display
     * @param tagFragment tag in order to get the fragment from stack
     */
    public void loadFragment(BaseFragment fragment, String tagFragment) {
        try {

            FragmentManager fragmentManager = getSupportFragmentManager();
            boolean fragmentPopped = fragmentManager.popBackStackImmediate(tagFragment, 0);

            if (!fragmentPopped){
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                transaction.replace(R.id.container, fragment, tagFragment);
                transaction.addToBackStack(tagFragment);
                transaction.commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideSnackBar(){
        if(snackbar != null && snackbar.isShown()){
            snackbar.dismiss();
        }
    }
    /**
     * Show a message to user
     *
     * @param message text to display
     */
    public void displayMessage(String message){

        snackbar = Snackbar.make(coordinator_layout,
                message, Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }
}
