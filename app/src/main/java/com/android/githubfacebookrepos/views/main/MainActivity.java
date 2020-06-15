package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.githubfacebookrepos.MainApplication;
import com.android.githubfacebookrepos.R;


/**
 * Activity which will show the list of Github facebook repos
 */
public class MainActivity extends AppCompatActivity {

    private MainApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (MainApplication) getApplicationContext();
        application.getAppComponent().mainComponent().create().inject(this);


    }
}
