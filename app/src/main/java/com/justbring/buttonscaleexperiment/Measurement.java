package com.justbring.buttonscaleexperiment;

import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User1 on 8/1/2016.
 */
public class Measurement{
    static Measurement measurement = new Measurement();
    int round = 0;
    JSONObject measumentsObj = new JSONObject();
    String participant;
    private Measurement() {

    }

    public static Measurement getInstance() {
        return measurement;
    }

    public int getRound() {
        return round;
    }

    public void nextRound() {
        round++;
    }

    public void add(double secondsPressed_) throws JSONException {
        measumentsObj.put(String.valueOf(round), secondsPressed_);
    }


    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public void clear() {
        round = 0;
        this.measumentsObj = new JSONObject();
        this.participant = "";
    }

    public void save(){
        ParseObject parseMeasurement=new ParseObject("Measurement");
        parseMeasurement.put("participant",participant);
        parseMeasurement.put("measurements",measumentsObj);
        parseMeasurement.saveInBackground();
    }
}