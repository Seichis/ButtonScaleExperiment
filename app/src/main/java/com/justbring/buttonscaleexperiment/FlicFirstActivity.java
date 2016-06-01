package com.justbring.buttonscaleexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.Slider;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.flic.lib.FlicAppNotInstalledException;
import io.flic.lib.FlicBroadcastReceiverFlags;
import io.flic.lib.FlicButton;
import io.flic.lib.FlicManager;
import io.flic.lib.FlicManagerInitializedCallback;

public class FlicFirstActivity extends AppCompatActivity {

    static Measurement measurement = Measurement.getInstance();
    static boolean isButtonEnabled;
    static boolean isLastRound;
    @Bind(R.id.feedback_tv)
    TextView feedbackTextView;
    @Bind(R.id.next_button)
    ButtonRectangle nextRoundButton;
    @Bind(R.id.slider_exper)
    Slider slider;
    @Bind(R.id.start_session_btn)
    Button startSessionButton;
    @Bind(R.id.tv_exper)
    TextView tvShowIntensity;
    Handler mHandler;
    CountDownTimer mCountDownTimer;
    FlicButton button;
    ArrayList<String> intensities = new ArrayList<>(Arrays.asList(new String[]{"moderate", "mild", "severe", "very severe", "worst"}));
    ArrayList<String> intensitiesForExperiment;

    public static boolean isButtonEnabled() {
        return isButtonEnabled;
    }

    public void setIsButtonEnabled(boolean isButtonEnabled) {
        FlicFirstActivity.isButtonEnabled = isButtonEnabled;
        feedbackTextView.setVisibility(View.VISIBLE);
        nextRoundButton.setEnabled(true);
        slider.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flic_first);
        ButterKnife.bind(this);
        isButtonEnabled = false;
        isLastRound = false;
        intensitiesForExperiment = getRandomIntensities(25);

        tvShowIntensity.setVisibility(View.INVISIBLE);
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
        slider.setEnabled(false);

        mHandler = new Handler();
        init();

        FlicManager.setAppCredentials("59eab426-39a4-4457-8e7d-2f67f9733d54", "d0ef92f6-a494-4f3d-96c0-841c6b434909", "ScaleMeasurement");
        try {
            FlicManager.getInstance(this, new FlicManagerInitializedCallback() {
                @Override
                public void onInitialized(FlicManager manager) {
                    manager.initiateGrabButton(FlicFirstActivity.this);
                }
            });
        } catch (FlicAppNotInstalledException err) {
            Toast.makeText(this, "Flic App is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void nextRound() {
        try {
            measurement.addSliderValue(((float) (slider.getValue()) / 1000f));
            measurement.addImageShown(intensitiesForExperiment.get(measurement.getRound()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        feedbackTextView.setVisibility(View.INVISIBLE);
        nextRoundButton.setEnabled(false);
        slider.setEnabled(false);

        slider.setValue(0);
        if (isLastRound || intensitiesForExperiment.size() - 1 == measurement.getRound()) {
            this.finish();
            return;
        }

        measurement.nextRound();
        setTvExper(measurement.getRound());
        measurement.setShowImageTimestamp(System.currentTimeMillis());

    }

    private void startSession() {
        int time = getIntent().getIntExtra("time", 0);
        startSessionButton.setVisibility(View.GONE);

        setTvExper(measurement.getRound());
        tvShowIntensity.setVisibility(View.VISIBLE);
        measurement.setShowImageTimestamp(System.currentTimeMillis());
        CountDownTimer mCountDownTimer = new CountDownTimer(600000, 30000) {

            public void onTick(long millisUntilFinished) {
//                Toast.makeText(FlicFirstActivity.this, String.valueOf(millisUntilFinished/60000), Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
                isLastRound = true;
                Toast.makeText(FlicFirstActivity.this, "Last round", Toast.LENGTH_SHORT).show();
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
        mCountDownTimer = null;
        button.setActiveMode(false);
        FlicManager.destroyInstance();
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
                button = manager.completeGrabButton(requestCode, resultCode, data);
                if (button != null) {
                    button.registerListenForBroadcast(FlicBroadcastReceiverFlags.UP_OR_DOWN | FlicBroadcastReceiverFlags.REMOVED);
                    button.setActiveMode(true);
                    Toast.makeText(FlicFirstActivity.this, "Grabbed a button", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FlicFirstActivity.this, "Did not grab any button", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public ArrayList<String> getRandomIntensities(int rounds) {
        ArrayList<String> list = new ArrayList<>();
        Random randomizer = new Random();
        for (int i = 0; i < rounds; i++) {
            list.add(intensities.get(randomizer.nextInt(intensities.size())));
        }
        return list;
    }

    public void setTvExper(final int round) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mHandler.post((new Runnable() {
                    @Override
                    public void run() {
                        tvShowIntensity.setText(getString(R.string.pain_intenity, intensitiesForExperiment.get(round)));
                    }
                }));
            }
        };

        Thread mThread = new Thread(runnable);
        mThread.start();

    }
}
