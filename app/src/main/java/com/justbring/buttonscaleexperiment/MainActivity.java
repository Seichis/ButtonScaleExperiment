package com.justbring.buttonscaleexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.parse.Parse;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity {
    Button startExperimentButton;
    EditText inputParticipantText,inputMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startExperimentButton = (Button) findViewById(R.id.start_experiment_btn);
        inputParticipantText = (EditText) findViewById(R.id.input_participant_et);
        inputMinutes = (EditText) findViewById(R.id.choose_time_exp);

        startExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = inputParticipantText.getText().toString();
                String minutes=inputMinutes.getText().toString();
                if (tmp!=null){
                    Intent startExperimentIntent=new Intent(MainActivity.this,FlicFirstActivity.class);
                    startExperimentIntent.putExtra("participant",tmp);
                    startExperimentIntent.putExtra("time",Integer.valueOf(minutes)*60*1000);
                    startActivity(startExperimentIntent);
                }else{
                    Toast.makeText(MainActivity.this, "Add a participant name pls", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        startActivity(new Intent(MainActivity.this,GraphsActivity.class));

    }


}
