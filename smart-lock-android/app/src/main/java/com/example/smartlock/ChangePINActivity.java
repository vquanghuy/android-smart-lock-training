package com.example.smartlock;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class ChangePINActivity extends AppCompatActivity {

    private static final String TAG = "ChangePINActivity";
    Random random = new Random();

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

        EditText edtCurrentPassword = new EditText(this);
        edtCurrentPassword.setHint(getString(R.string.str_cpi_edt_current_password_text));
        mainLayout.addView(edtCurrentPassword);

        final EditText edtNewPassword = new EditText(this);
        edtNewPassword.setHint(getString(R.string.str_cpi_edt_new_password_text));
        mainLayout.addView(edtNewPassword);

        EditText edtReenterNewPassword = new EditText(this);
        edtReenterNewPassword.setHint(getString(R.string.str_cpi_edt_reenter_new_password_text));
        mainLayout.addView(edtReenterNewPassword);

        Button btnChangePassword = new Button(this);
        btnChangePassword.setText(getString(R.string.str_cpi_btn_change_password));
        btnChangePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            try {
                FirebaseHelper.setDoorPIN(edtNewPassword.getText().toString(),
                    new FirebaseHelper.Listener()
                    {
                        @Override
                        public void onFinish(Object result) {
                            if (((Integer) result) != 200)
                            {
                                Toast.makeText(ChangePINActivity.this, "Unexpected Error !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
                
            }
        });
        mainLayout.addView(btnChangePassword);
    }
}