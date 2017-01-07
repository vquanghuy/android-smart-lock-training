package com.example.asyncsample;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final static int DELAY_TIME = 300;

    Button btnAsyncCounting = null;
    Button btnThreadRunOnUICounting = null;
    Button btnThreadHandlerCounting = null;

    TextView txtCounting = null;
    EditText edtCountValue = null;

    AsyncTask<Integer, Integer, Void> asyncTask = null;
    Thread threadWithRunOnUI = null;
    Thread threadWithHandler = null;
    Handler handler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAsyncCounting = (Button) findViewById(R.id.btnAsyncCounting);
        btnAsyncCounting.setOnClickListener(this);

        btnThreadRunOnUICounting = (Button) findViewById(R.id.btnThreadRunUiCounting);
        btnThreadRunOnUICounting.setOnClickListener(this);

        btnThreadHandlerCounting = (Button) findViewById(R.id.btnThreadHandlerCounting);
        btnThreadHandlerCounting.setOnClickListener(this);

        txtCounting = (TextView) findViewById(R.id.txtCount);
        edtCountValue = (EditText) findViewById(R.id.edtCountValue);

        handler = new Handler();
    }

    void executeAsyncCounting()
    {
        if (asyncTask != null)
        {
            asyncTask.cancel(true);
            asyncTask = null;
        }

        asyncTask = new AsyncTask<Integer, Integer, Void>() {
            @Override
            protected Void doInBackground(Integer... params) {
                try
                {
                    for (int i = 1; i <= params[0]; i++)
                    {
                        Thread.sleep(DELAY_TIME);
                        publishProgress(i);
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);

                MainActivity.this.txtCounting.setText(values[0].toString());
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        asyncTask.execute(Integer.parseInt(edtCountValue.getText().toString()));
    }

    void executeThreadRunOnUICounting()
    {
        if (threadWithRunOnUI != null)
        {
            threadWithRunOnUI.interrupt();
            threadWithRunOnUI = null;
        }

        final int countSize = Integer.parseInt(edtCountValue.getText().toString());
        threadWithRunOnUI = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= countSize; i++)
                {
                    final int countValue = i;
                    try
                    {
                        Thread.sleep(DELAY_TIME);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtCounting.setText(String.valueOf(countValue));
                            }
                        });
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });
        threadWithRunOnUI.start();
    }

    void executeThreadHandlerCounting()
    {
        if (threadWithHandler != null)
        {
            threadWithHandler.interrupt();
            threadWithHandler = null;
        }

        final int countSize = Integer.parseInt(edtCountValue.getText().toString());
        threadWithHandler = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= countSize; i++)
                {
                    final int countValue = i;

                    try
                    {
                        Thread.sleep(DELAY_TIME);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                txtCounting.setText(String.valueOf(countValue));
                            }
                        });
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });
        threadWithHandler.start();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnAsyncCounting: executeAsyncCounting();
                break;
            case R.id.btnThreadRunUiCounting: executeThreadRunOnUICounting();
                break;
            case R.id.btnThreadHandlerCounting: executeThreadHandlerCounting();
                break;
        }
    }
}
