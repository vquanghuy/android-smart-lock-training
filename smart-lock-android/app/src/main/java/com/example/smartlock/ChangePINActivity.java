package com.example.smartlock;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChangePINActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayoutAndControl();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

        EditText edtNewPassword = new EditText(this);
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
                    URL url = new URL("https://fb-sample-b312e.firebaseio.com/system.json");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    StringBuffer fileContent = new StringBuffer("");
                    int read_bytes = 0;

                    byte[] buffer = new byte[1024];
                    while ((read_bytes = inputStream.read(buffer)) != -1)
                    {
                        fileContent.append(new String(buffer, 0, read_bytes));
                    }

                    Toast.makeText(ChangePINActivity.this, fileContent.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        mainLayout.addView(btnChangePassword);
    }
}