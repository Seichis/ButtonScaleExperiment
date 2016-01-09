package com.justbring.buttonscaleexperiment;

import android.content.Context;
import android.util.Log;

import io.flic.lib.FlicBroadcastReceiver;
import io.flic.lib.FlicButton;

/**
 * Created by User1 on 8/1/2016.
 */
public class MyFlicBroadcastReceiver extends FlicBroadcastReceiver {
    static long tStart;
    static long tEnd;
    static Measurement measurement=new Measurement("Kotsos");
    @Override
    protected void onRequestAppCredentials(Context context) {
        // Set app credentials by calling FlicManager.setAppCredentials here
    }

    @Override
    public void onButtonUpOrDown(Context context, FlicButton button, boolean wasQueued, int timeDiff, boolean isUp, boolean isDown) {
        if (isDown) {
            // Code for button up event here
            tStart=System.currentTimeMillis();
            Log.i("time","  " + tStart);
        } else if (isUp){
            tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            double elapsedSeconds = tDelta / 1000.0;
            Log.i("time","  " + elapsedSeconds);
            Log.i("time","  " + tStart);
            Log.i("time","  " + tEnd);
            tStart=0;
        }
    }

    @Override
    public void onButtonRemoved(Context context, FlicButton button) {
        // Button was removed
    }
}
