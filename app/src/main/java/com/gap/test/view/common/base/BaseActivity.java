package com.gap.test.view.common.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.gap.test.R;
import com.gap.test.view.common.permissions.PermissionsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S. on 22/04/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Set back arrow on tool bar
     *
     * @param showArrow show it or not
     */
    protected void setBackArrow(Boolean showArrow){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(showArrow);
        }
    }

    /**
     * Hide the key pad
     */
    public void hideKeyPad(){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Check if the app have authorization to use the permissions
     */
    public void checkPermissions(){
        PermissionsManager.askPermissions(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionsManager.PERMISSION_ALL: {
                if (grantResults.length > 0) {
                    List<Integer> indexesOfPermissionsNeededToShow = new ArrayList<>();
                    for(int i = 0; i < permissions.length; ++i) {
                        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            indexesOfPermissionsNeededToShow.add(i);
                        }
                    }

                    int size = indexesOfPermissionsNeededToShow.size();
                    if(size != 0) {
                        int i = 0;
                        boolean isPermissionGranted = true;

                        while(i < size && isPermissionGranted) {
                            isPermissionGranted = grantResults[indexesOfPermissionsNeededToShow.get(i)] == PackageManager.PERMISSION_GRANTED;
                            i++;
                        }

                        if(!isPermissionGranted) {

                            showDialogNotCancelable(getString(R.string.permission_mandatory), getString(R.string.all_permission_required),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            checkPermissions();
                                        }
                                    });
                        }
                    }
                }
            }
        }
    }

    /**
     * Display dialog asking for authorization for permissions
     *
     * @param title Dialog title
     * @param message message
     * @param okListener listener to Ok button
     */
    private void showDialogNotCancelable(String title, String message,
                                         DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, okListener)
                .setCancelable(false)
                .create()
                .show();

    }
}
