package com.example.abc.androidlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        TextView txtMessage = (TextView) findViewById(R.id.txtMessage);
        txtMessage.setText(getIntent().getStringExtra("MAIN_ACTIVITY"));
    }
}
