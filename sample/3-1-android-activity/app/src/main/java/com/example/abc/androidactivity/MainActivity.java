package com.example.abc.androidactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView    txtTitle = null;
    TextView    txtPassword = null;
    EditText    edtPassword = null;
    Button      btnLogin = null;
    Button      btnReset = null;

    MainActivity activity = null;

    static final String PASSWORD_KEY = "USER_PASSWORD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_main);
        initControls();
    }

    private void initControls()
    {
        LinearLayout layout = (LinearLayout) findViewById(R.id.activity_main);

        txtTitle = new TextView(this);
        txtTitle.setText(R.string.activity_login_title);
        txtTitle.setTextSize(40);
        layout.addView(txtTitle);

        txtPassword = new TextView(this);
        txtPassword.setText("Password");
        layout.addView(txtPassword);

        edtPassword = new EditText(this);
        edtPassword.setText("");
        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(edtPassword);

        btnLogin = new Button(this);
        btnLogin.setText("Login");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ControlActivity.class);
                intent.putExtra(PASSWORD_KEY, edtPassword.getText().toString());
                startActivity(intent);
            }
        });
        layout.addView(btnLogin);

        btnReset = new Button(this);
        btnReset.setText("Reset Password");
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ANDROID_TAG", "Reset password pressed !");
            }
        });
        layout.addView(btnReset);
    }
}
