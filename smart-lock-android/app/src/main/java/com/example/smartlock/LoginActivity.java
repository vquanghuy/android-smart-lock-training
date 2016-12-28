package com.example.smartlock;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

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

        TextView txtTitle = new TextView(this);
        txtTitle.setText(getString(R.string.app_name));
        txtTitle.setTextSize(30);
        txtTitle.setAllCaps(true);
        txtTitle.setGravity(Gravity.CENTER);
        txtTitle.setTypeface(null, Typeface.BOLD);
        mainLayout.addView(txtTitle);

        EditText edtPassword = new EditText(this);
        edtPassword.setHint(getString(R.string.str_login_hint_password));
        mainLayout.addView(edtPassword);

        Button btnLogin = new Button(this);
        btnLogin.setText(getString(R.string.str_login_btn_login_text));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainControlActivity.class);
                startActivity(intent);
            }
        });
        mainLayout.addView(btnLogin);
    }
}
