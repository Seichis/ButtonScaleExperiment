package com.justbring.buttonscaleexperiment;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User1 on 8/1/2016.
 */
public class DataOperations {
    private static DataOperations dataOPS = new DataOperations();

    private DataOperations() {
    }

    /**
     * @return DataOperations
     * Returns the DataOperations singleton instance
     */
    public static DataOperations getInstance() {
        return dataOPS;
    }

    /**
     * @param content Writes JSON data to measurement file
     */
    public void writeToMeasurementFile(String content) {
        try {
            File file = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/measurement.json");
            if (!file.exists())
                file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content + "\n");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * @return JSONlist
     * @throws IOException Reads all the JSON data from the measurement file to a list of strings
     */
    public List<String> readFromMeasurementFile() {

        BufferedReader br;
        List<String> JSONlist = new ArrayList<>();
        String line;
        try {
            String fpath = Environment.getExternalStorageDirectory().getPath()
                    + "/measurements.json";
            br = new BufferedReader(new FileReader(fpath));
            while ((line = br.readLine()) != null) {
                JSONlist.add(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSONlist;
    }

    /**
     * @param EX
     * @return JSON
     * Converts an Measurement object to a JSON string
     */
    public String measurementToJSON(Measurement EX) {
        Gson gson = new GsonBuilder().create();
        String JSONtemp = (gson.toJson(EX));
        return JSONtemp;
    }

}
