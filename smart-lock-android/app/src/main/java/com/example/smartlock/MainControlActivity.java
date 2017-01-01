package com.example.smartlock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.Exchanger;

public class MainControlActivity extends AppCompatActivity {
    Random random = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutAndControl();
    }

    void initLayoutAndControl()
    {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT );
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(mainLayout, layoutParams);

        Button btnLog = new Button(this);
        btnLog.setText(getString(R.string.str_control_btn_log_text));
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainControlActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });
        mainLayout.addView(btnLog);

        Button btnUpdatePassword = new Button(this);
        btnUpdatePassword.setText(getString(R.string.str_control_btn_update_password_text));
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainControlActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        mainLayout.addView(btnUpdatePassword);

        Button btnUpdatePIN = new Button(this);
        btnUpdatePIN.setText(getString(R.string.str_control_btn_update_pin_text));
        btnUpdatePIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainControlActivity.this, ChangePINActivity.class);
                startActivity(intent);
            }
        });
        mainLayout.addView(btnUpdatePIN);

        Button btnSimulateOpenDoor = new Button(this);
        btnSimulateOpenDoor.setText(getString(R.string.str_control_btn_simulate_open_door_text));
        btnSimulateOpenDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    FirebaseHelper.simulateDoorOpen();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        });
        mainLayout.addView(btnSimulateOpenDoor);
    }
}
