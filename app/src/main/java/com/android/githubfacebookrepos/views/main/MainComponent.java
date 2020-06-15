package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import com.android.githubfacebookrepos.di.ActivityScope;

import dagger.Subcomponent;


// Scope annotation that the [MainComponent] uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
@Subcomponent
public interface MainComponent {

    // Factory to create instances of [MainComponent]
    @Subcomponent.Factory
    interface Factory {
        MainComponent create();
    }

    // Classes that can be injected by this Component
    void inject(MainActivity activity);
}
