package com.justbring.buttonscaleexperiment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
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
    static TextView feedbackTextView;
    static List<String> imageList = new ArrayList<>();
    static boolean isButtonEnabled;
    static boolean isLastRound;
    ButtonFloat startSessionButton;
    static ButtonRectangle nextRoundButton;
    ImageView imageView;
    static Slider slider;
    Handler mHandler;
    CountDownTimer mCountDownTimer;

    public static void setIsButtonEnabled(boolean isButtonEnabled) {
        ExperimentActivity.isButtonEnabled = isButtonEnabled;
        feedbackTextView.setVisibility(View.VISIBLE);
        nextRoundButton.setEnabled(true);
        slider.setEnabled(true);
    }

    public static boolean isButtonEnabled() {
        return isButtonEnabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        isButtonEnabled = false;
        isLastRound = false;

        startSessionButton = (ButtonFloat) findViewById(R.id.start_session_btn);
        nextRoundButton = (ButtonRectangle) findViewById(R.id.next_button);
        imageView = (ImageView) findViewById(R.id.imageview_exper);
        imageView.setVisibility(View.INVISIBLE);
        feedbackTextView = (TextView) findViewById(R.id.feedback_tv);
        feedbackTextView.setVisibility(View.INVISIBLE);
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
                isButtonEnabled = true;
                nextRound();
            }
        });
        nextRoundButton.setEnabled(false);
        slider = (Slider) findViewById(R.id.slider_exper);
        slider.setEnabled(false);

        mHandler = new Handler();
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
            measurement.addSliderValue(((float)(slider.getValue()) / 1000f));
            measurement.addImageShown(imageList.get(measurement.getRound()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        feedbackTextView.setVisibility(View.INVISIBLE);
        nextRoundButton.setEnabled(false);
        slider.setEnabled(false);

        slider.setValue(0);
        if (isLastRound || imageList.size()-1==measurement.getRound()) {
            this.finish();
            return;
        }

        measurement.nextRound();
        setImageView(measurement.getRound());
        measurement.setShowImageTimestamp(System.currentTimeMillis());

    }

    private void startSession() {
        startSessionButton.setVisibility(View.GONE);
        try {
            imageList = Utils.getImageList(this.getApplicationContext());
        } catch (IOException e) {
            Toast.makeText(ExperimentActivity.this, "An error loading the image list", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        setImageView(measurement.getRound());
        imageView.setVisibility(View.VISIBLE);
        measurement.setShowImageTimestamp(System.currentTimeMillis());
        CountDownTimer mCountDownTimer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                Toast.makeText(ExperimentActivity.this, String.valueOf(millisUntilFinished/60000), Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
                isLastRound = true;
                Toast.makeText(ExperimentActivity.this, "Last round", Toast.LENGTH_SHORT).show();
            }
        };
        mCountDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        measurement.save();
        Toast.makeText(this, "Saved measurement successfully", Toast.LENGTH_LONG).show();
        measurement.clear();
        mCountDownTimer=null;
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

    public void setImageView(final int round) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Bitmap image = null;
                try {
                    // get input stream
                    String imageFile = imageList.get(round);
                    Log.i("image", imageFile + "passss 111");

                    InputStream ims = ExperimentActivity.this.getAssets().open("image/" + imageFile);
                    Log.i("image", imageFile + "passss 222");

                    // load image as Bitmap
                    image = BitmapFactory.decodeStream(ims);
                    ims.close();

                } catch (IOException ex) {
                    Log.i("imageerror", ex.toString());
                    Toast.makeText(ExperimentActivity.this, "An error occured grabbing the image", Toast.LENGTH_SHORT).show();
                }

                final Bitmap finalImage = image;
                mHandler.post((new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(finalImage);
                    }
                }));

            }
        };

        Thread mThread = new Thread(runnable);
        mThread.start();

    }

}
