package com.example.abc.androidsharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SavedValuesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_values);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
        String message = sharedPreferences.getString(getString(R.string.message_key), "");
        int number = sharedPreferences.getInt(getString(R.string.number_key), 0);

        TextView txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtMessage.setText(txtMessage.getText() + message);

        TextView txtNumber = (TextView) findViewById(R.id.txtNumber);
        txtNumber.setText(txtNumber.getText() + String.valueOf(number));
    }

    void onClickBtnGoBack(View v)
    {
        finish();
    }

}
