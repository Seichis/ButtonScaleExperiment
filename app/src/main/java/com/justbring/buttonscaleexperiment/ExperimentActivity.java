package com.justbring.buttonscaleexperiment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.gc.materialdesign.views.Slider;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.flic.lib.FlicAppNotInstalledException;
import io.flic.lib.FlicBroadcastReceiverFlags;
import io.flic.lib.FlicButton;
import io.flic.lib.FlicManager;
import io.flic.lib.FlicManagerInitializedCallback;

public class ExperimentActivity extends AppCompatActivity {
    static Measurement measurement = Measurement.getInstance();
    Button startSessionButton, nextRoundButton;
    ImageView imageView;
    Slider slider;
    static List<String> imageList = new ArrayList<>();

    public static void setIsButtonEnabled(boolean isButtonEnabled) {
        ExperimentActivity.isButtonEnabled = isButtonEnabled;
    }

    static boolean isButtonEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        startSessionButton = (Button) findViewById(R.id.start_session_btn);
        nextRoundButton = (Button) findViewById(R.id.next_button);
        imageView = (ImageView) findViewById(R.id.imageview_exper);
        imageView.setVisibility(View.INVISIBLE);
        startSessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isButtonEnabled = true;
                startSession();
            }
        });
        nextRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isButtonEnabled = false;
                nextRound();
            }
        });
        slider=(Slider)findViewById(R.id.slider_exper);
        slider.setActivated(false);

        init();

        FlicManager.setAppCredentials("59eab426-39a4-4457-8e7d-2f67f9733d54", "d0ef92f6-a494-4f3d-96c0-841c6b434909", "ScaleMeasurement");
        try {
            FlicManager.getInstance(this, new FlicManagerInitializedCallback() {
                @Override
                public void onInitialized(FlicManager manager) {
                    manager.initiateGrabButton(ExperimentActivity.this);
                }
            });
        } catch (FlicAppNotInstalledException err) {
            Toast.makeText(this, "Flic App is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextRound() {

        try {
            measurement.addSliderValue(slider.getValue()/1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageView.setImageDrawable(getImage(measurement.getRound()));
        measurement.setShowImageTimestamp(System.currentTimeMillis());
        measurement.nextRound();
    }

    private void startSession() {
        startSessionButton.setVisibility(View.GONE);
        try {
            imageList = Utils.getImageList(this.getApplicationContext());
        } catch (IOException e) {
            Toast.makeText(ExperimentActivity.this, "An error loading the image list", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        slider.setActivated(true);
        imageView.setImageDrawable(getImage(measurement.getRound()));
        measurement.setShowImageTimestamp(System.currentTimeMillis());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        measurement.save();
        Toast.makeText(this, "Saved measurement successfully", Toast.LENGTH_LONG).show();
        measurement.clear();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        String participant = extras.getString("participant");
        measurement.setParticipant(participant);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        FlicManager.getInstance(this, new FlicManagerInitializedCallback() {
            @Override
            public void onInitialized(FlicManager manager) {
                FlicButton button = manager.completeGrabButton(requestCode, resultCode, data);
                if (button != null) {
                    button.registerListenForBroadcast(FlicBroadcastReceiverFlags.UP_OR_DOWN | FlicBroadcastReceiverFlags.REMOVED);
                    Toast.makeText(ExperimentActivity.this, "Grabbed a button", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExperimentActivity.this, "Did not grab any button", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Drawable getImage(int round) {
        Drawable image = ContextCompat.getDrawable(getApplicationContext(), R.drawable.background_button);
        // load image
        try {
            // get input stream
            String imageFile=imageList.get(round);
            InputStream ims = getAssets().open(imageFile);
            // load image as Drawable
            image = Drawable.createFromStream(ims, null);
            // set image to ImageView
        } catch (IOException ex) {
            Toast.makeText(ExperimentActivity.this, "An error occured grabbing the image", Toast.LENGTH_SHORT).show();
        }
        return image;
    }

    public static boolean isButtonEnabled() {
        return isButtonEnabled;
    }

}
