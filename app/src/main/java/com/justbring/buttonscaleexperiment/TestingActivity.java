package com.justbring.buttonscaleexperiment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

public class TestingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        HashMap<String, Object> params = new HashMap<>();
        ParseCloud.callFunctionInBackground("routeOptimizer", params, new FunctionCallback<String>() {
            @Override
            public void done(String object, ParseException e) {
                if (e == null) {
                    Toast.makeText(TestingActivity.this, object, Toast.LENGTH_LONG).show();
                    Log.i("Response", object);

                    Gson gson = new GsonBuilder().create();
                    JsonObject JSONtemp = gson.fromJson(object, JsonObject.class);

                    Log.i("Response", ""+JSONtemp.get("route").getAsJsonObject().get("0"));
                    Log.i("Response", JSONtemp.toString());


                } else {
                    Toast.makeText(TestingActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
