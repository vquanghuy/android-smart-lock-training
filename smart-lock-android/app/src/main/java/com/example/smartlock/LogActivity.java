package com.example.smartlock;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class LogActivity extends AppCompatActivity implements FirebaseHelper.Listener {
    ProgressDialog  progressDialog = null;
    ListView        listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.str_dlg_loading));

        FirebaseHelper.getLog(this);

        listView = new ListView(this);
        ((FrameLayout) findViewById(android.R.id.content)).addView(listView);

        progressDialog.show();
    }

    @Override
    public void onFinish(Object result) {
        try
        {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z");

            JSONObject jsonObject = new JSONObject(result.toString());

            ArrayList<String> values = new ArrayList<String>();

            for (Iterator<String> iter = jsonObject.keys(); iter.hasNext(); )
            {
                JSONObject childObject = new JSONObject(jsonObject.getString(iter.next()));
                String datetime = formatter.format(new Date(Long.parseLong(childObject.getString("timestamp")) * 1000L));
                values.add(childObject.getString("status") + " - " + datetime);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);

            // Assign adapter to ListView
            listView.setAdapter(adapter);

            progressDialog.dismiss();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
