package com.justbring.buttonscaleexperiment;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Konstantinos Michail on 1/16/2016.
 */
public class Utils {

    public static int randInt(int min, int max) {

        Random rand=new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static List<String> getImageList(Context context) throws IOException
    {
        AssetManager assetManager =context.getAssets();
        String[] files = assetManager.list("images");
        List<String> it= Arrays.asList(files);

        // Shuffle the list
        long seed = System.nanoTime();
        Collections.shuffle(it, new Random(seed));
        Log.i("Images","   "+ it.toString());
        return it;

    }
}
