package com.example.abc.androidfilesample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    final public static String FILE_NAME = "sample_content.txt";
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    EditText edtContent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtContent = (EditText) findViewById(R.id.edtFileContent);

        verifyStoragePermissions(this);
    }

    void onClickBtnSaveInternal(View v)
    {
        try {
            String strContent = edtContent.getText().toString();
            FileOutputStream outputStream;

            outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(strContent.getBytes());
            outputStream.close();
            Toast.makeText(this, "Internal file saved successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Internal file unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    void onClickBtnLoadInternal(View v)
    {
        try {
            FileInputStream inputStream;
            inputStream = openFileInput(FILE_NAME);
            StringBuffer fileContent = new StringBuffer("");
            int read_bytes = 0;

            byte[] buffer = new byte[1024];
            while ((read_bytes = inputStream.read(buffer)) != -1)
            {
                fileContent.append(new String(buffer, 0, read_bytes));
            }

            edtContent.setText(fileContent.toString());
            Toast.makeText(this, "Internal file loaded successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Internal file unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    void onClickBtnSaveExternal(View v)
    {
        if (!isExternalStorageWritable())
        {
            Log.d("TAG", "Couldn't write to external storage !");
            return;
        }

        try
        {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + File.separator + FILE_NAME;
            FileOutputStream outputStream = new FileOutputStream(path);

            String strContent = edtContent.getText().toString();

            outputStream.write(strContent.getBytes());
            outputStream.close();
            Toast.makeText(this, "External file read successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void onClickBtnLoadExternal(View v)
    {
        if (!isExternalStorageReadable())
        {
            Log.d("TAG", "Couldn't read from external storage !");
            return;
        }

        try
        {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + FILE_NAME;
            FileInputStream inputStream = new FileInputStream(path);

            StringBuffer fileContent = new StringBuffer("");
            int read_bytes = 0;

            byte[] buffer = new byte[1024];
            while ((read_bytes = inputStream.read(buffer)) != -1)
            {
                fileContent.append(new String(buffer, 0, read_bytes));
            }

            edtContent.setText(fileContent.toString());
            Toast.makeText(this, "External file loaded successfully", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void onClickBtnClear(View v)
    {
        edtContent.setText("");
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
