package com.example.abc.androidsharedpreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);
    }

    void onClickBtnSaveMessage(View v)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.message_key), ((EditText) findViewById(R.id.edtMessage)).getText().toString());
        editor.commit();
    }

    void onClickBtnSaveNumber(View v)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.number_key), Integer.parseInt(((EditText) findViewById(R.id.edtNumber)).getText().toString()));
        editor.commit();
    }

    void onClickBtnViewSavedValues(View v)
    {
        Intent intent = new Intent(this, SavedValuesActivity.class);
        startActivity(intent);
    }
}
