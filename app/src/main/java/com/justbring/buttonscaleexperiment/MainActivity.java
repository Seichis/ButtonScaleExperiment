package com.justbring.buttonscaleexperiment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;

public class MainActivity extends AppCompatActivity {
    Button startExperimentButton;
    EditText inputParticipantText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startExperimentButton = (Button) findViewById(R.id.start_experiment_btn);
        inputParticipantText = (EditText) findViewById(R.id.input_participant_et);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this);
        startExperimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = inputParticipantText.getText().toString();
                if (tmp!=null){
                    Intent startExperimentIntent=new Intent(MainActivity.this,ExperimentActivity.class);
                    startExperimentIntent.putExtra("participant",tmp);
                    startActivity(startExperimentIntent);
                }else{
                    Toast.makeText(MainActivity.this, "Add a participant name pls", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
