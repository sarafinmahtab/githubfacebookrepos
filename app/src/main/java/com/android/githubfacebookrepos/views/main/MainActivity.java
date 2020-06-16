package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.githubfacebookrepos.MainApplication;
import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.di.ViewModelFactory;

import javax.inject.Inject;


/**
 * Activity which will show the list of Github facebook repos
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    private MainViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainApplication application = (MainApplication) getApplicationContext();
        application.getAppComponent().mainComponent().create().inject(this);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
    }
}
