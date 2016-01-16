package com.justbring.buttonscaleexperiment;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Konstantinos Michail on 1/16/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
    }
}
