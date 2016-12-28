package com.example.abc.androidlayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button      btnShowMessage;
    TextView    txtMessage;
    EditText    edtMessage;
    MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        // Create layout
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create button
        txtMessage = new TextView(this);
        txtMessage.setText("Message");
        layout.addView(txtMessage);

        edtMessage = new EditText(this);
        edtMessage.setText("");
        layout.addView(edtMessage);

        btnShowMessage = new Button(this);
        btnShowMessage.setText("Show Message");
        layout.addView(btnShowMessage);
        btnShowMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MessageActivity.class);
                intent.putExtra("MAIN_ACTIVITY", edtMessage.getText().toString());
                startActivity(intent);
            }
        });

        setContentView(layout, params);
    }
}
