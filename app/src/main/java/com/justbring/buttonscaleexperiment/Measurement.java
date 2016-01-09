package com.justbring.buttonscaleexperiment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User1 on 8/1/2016.
 */
public class Measurement {

    Map<Integer, Double> measumentsMap;
    String participant;

    public Measurement(String participant_) {
        this.measumentsMap=new HashMap<>();
        this.participant=participant_;
    }
    public void addMeasurement(int round_,double secondsPressed_){
        measumentsMap.put(round_,secondsPressed_);
    }


    public Map<Integer, Double> getMeasumentsMap() {
        return measumentsMap;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}