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

public class ChangePasswordActivity extends AppCompatActivity {

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
        edtCurrentPassword.setHint(getString(R.string.str_cpw_edt_current_password_text));
        mainLayout.addView(edtCurrentPassword);

        EditText edtNewPassword = new EditText(this);
        edtNewPassword.setHint(getString(R.string.str_cpw_edt_new_password_text));
        mainLayout.addView(edtNewPassword);

        EditText edtReenterNewPassword = new EditText(this);
        edtReenterNewPassword.setHint(getString(R.string.str_cpw_edt_reenter_new_password_text));
        mainLayout.addView(edtReenterNewPassword);

        Button btnChangePassword = new Button(this);
        btnChangePassword.setText(getString(R.string.str_cpw_btn_change_password));
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mainLayout.addView(btnChangePassword);
    }
}
