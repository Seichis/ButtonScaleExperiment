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
    JSONObject buttonMeasumentsObj = new JSONObject();
    JSONObject sliderMeasumentsObj = new JSONObject();
    String participant;
    // Interval is the time between seeing the image and pressing the button
    JSONObject intervals = new JSONObject();
    long showImageTimestamp,pressButtonTimestamp;

    public void setShowImageTimestamp(long showImageTimestamp) {
        this.showImageTimestamp = showImageTimestamp;
    }


    public void setPressButtonTimestamp(long pressButtonTimestamp) {
        this.pressButtonTimestamp = pressButtonTimestamp;
    }

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

    public void addButtonValue(double secondsPressed_) throws JSONException {
        buttonMeasumentsObj.put(String.valueOf(round), secondsPressed_);
    }

    public void addSliderValue(float value_) throws JSONException {
        sliderMeasumentsObj.put(String.valueOf(round), value_);
    }

    public void addInterval() throws JSONException {
        intervals.put(String.valueOf(round), this.pressButtonTimestamp-this.showImageTimestamp);
    }


    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public void clear() {
        round = 0;
        this.buttonMeasumentsObj = new JSONObject();
        this.intervals = new JSONObject();
        this.sliderMeasumentsObj = new JSONObject();
        this.participant = "";
    }

    public void save(){
        ParseObject parseMeasurement=new ParseObject("Measurement");
        parseMeasurement.put("participant",participant);
        parseMeasurement.put("buttonMeasurements",buttonMeasumentsObj);
        parseMeasurement.put("sliderMeasurements",sliderMeasumentsObj);
        parseMeasurement.put("intervals",intervals);
        parseMeasurement.saveInBackground();
    }
}