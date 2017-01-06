package com.example.abc.androidfragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements TitleFragment.OnTitleSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            TitleFragment firstFragment = new TitleFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onTitleSelected(int position) {
        ContentFragment contentFragment = (ContentFragment)
                getSupportFragmentManager().findFragmentById(R.id.content_fragment);

        if (contentFragment != null) {
            contentFragment.updateContent(position);
        } else {
            ContentFragment newFragment = new ContentFragment();
            Bundle args = new Bundle();
            args.putInt(ContentFragment.POSITION_KEY, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        }
    }
}
