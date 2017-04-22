package com.gap.test.view;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.stetho.Stetho;
import com.gap.test.BuildConfig;
import com.gap.test.R;
import com.squareup.leakcanary.LeakCanary;


/**
 * Demo GAP - Search Near places
 * Created by John Arboleda S on 22/04/2017.
 */

public class TestApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if (BuildConfig.DEBUG) {

            Stetho.initializeWithDefaults(this);

            if (this.getResources().getBoolean(R.bool.support_canary_leaks)) {
                // ode to catch leaks
                if (LeakCanary.isInAnalyzerProcess(this)) {
                    // This process is dedicated to LeakCanary for heap analysis.
                    // You should not init your app in this process.
                    return;
                }
                LeakCanary.install(this);
            }
        }
    }
}
