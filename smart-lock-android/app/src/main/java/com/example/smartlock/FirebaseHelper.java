package com.example.smartlock;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import static android.R.attr.data;

/**
 * Created by huyvu on 12/30/2016.
 */

public class FirebaseHelper {
    final public static String FB_DB_URL = "https://fb-sample-b312e.firebaseio.com/";
    final public static String FB_DOOR_PIN = FB_DB_URL + "system/door_pin.json";
    final public static String FB_TRACKING = FB_DB_URL + "system/tracking.json";
    private static final String TAG = "FirebaseHelper";
    public static Random random = new Random();

    public static interface Listener
    {
        void onFinish(Object result);
    }

    private static class GetDataTask extends AsyncTask<String, Void, String>
    {
        Listener    mListener = null;

        public GetDataTask(Listener listener)
        {
            mListener = listener;
        }
        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                URL url = new URL(params[0]);
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
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mListener.onFinish(s);
        }
    }

    private static class SetDataTask extends AsyncTask<String, Void, Integer>
    {
        Listener mListener = null;
        public SetDataTask(Listener listener)
        {
            mListener = listener;
        }

        @Override
        protected Integer doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod(params[1]);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                OutputStreamWriter osw = new OutputStreamWriter(urlConnection.getOutputStream());
                osw.write(params[2]);
                osw.flush();
                osw.close();

                return urlConnection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mListener.onFinish(integer);
        }
    }


    public static void getDoorPIN(Listener callback)
    {
        new GetDataTask(callback).execute(FB_DOOR_PIN);
    }

    public static void setDoorPIN(String pin, Listener callback)
    {
        new SetDataTask(callback).execute(FB_DOOR_PIN, "PUT", pin);
    }

    public static void simulateDoorOpen(Listener callback) throws Exception
    {
        String status = random.nextBoolean() ? "SUCCESS" : "FAILURE";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        JSONObject jsonRecord = new JSONObject();
        jsonRecord.put("status", status);
        jsonRecord.put("timestamp", timestamp);

        new SetDataTask(callback).execute(FB_TRACKING, "POST", jsonRecord.toString());
    }

}
