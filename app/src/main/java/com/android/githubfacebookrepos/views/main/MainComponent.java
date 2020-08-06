package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import com.android.githubfacebookrepos.di.ActivityScope;

import dagger.Subcomponent;


/**
 * Scope annotation that the [MainComponent] uses
 * Classes annotated with @ActivityScope will have a unique instance in this Component
 */
@ActivityScope
@Subcomponent
public interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        MainComponent create();
    }

    void inject(MainActivity activity);
}
