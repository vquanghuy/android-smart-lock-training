package com.example.smartlock;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by huyvu on 12/30/2016.
 */

public class FirebaseHelper {
    final public static String FB_DB_URL = "https://fb-sample-b312e.firebaseio.com/";
    final public static String FB_DOOR_PIN = FB_DB_URL + "system/door_pin.json";
    final public static String FB_TRACKING = FB_DB_URL + "system/tracking.json";
    private static final String TAG = "FirebaseHelper";
    public static Random random = new Random();

    public static String getDoorPIN() throws Exception
    {
        return getData(FB_DOOR_PIN);
    }

    public static int setDoorPIN(String pin) throws Exception
    {

        return setData(FB_DOOR_PIN, pin, "PUT");
    }

    public static int simulateDoorOpen() throws Exception
    {
        String status = random.nextBoolean() ? "SUCCESS" : "FAILURE";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        JSONObject jsonRecord = new JSONObject();
        jsonRecord.put("status", status);
        jsonRecord.put("timestamp", timestamp);

        return setData(FB_TRACKING, jsonRecord.toString(), "POST");
    }

    private static String getData(String strUrl) throws Exception
    {
        URL url = new URL(strUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
        StringBuffer fileContent = new StringBuffer("");
        int read_bytes = 0;

        byte[] buffer = new byte[1024];
        while ((read_bytes = inputStream.read(buffer)) != -1)
        {
            fileContent.append(new String(buffer, 0, read_bytes));
        }

        return fileContent.toString();
    }

    private static int setData(String strURL, String data, String method) throws Exception
    {
        URL url = new URL(strURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Accept", "application/json");

        OutputStreamWriter osw = new OutputStreamWriter(urlConnection.getOutputStream());
        osw.write(data);
        osw.flush();
        osw.close();

        return urlConnection.getResponseCode();
    }
}
