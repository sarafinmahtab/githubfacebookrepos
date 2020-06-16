package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */


import android.content.Context;

import com.android.githubfacebookrepos.views.main.MainComponent;

import dagger.BindsInstance;
import dagger.Component;

// Scope annotation that the AppComponent uses
// Classes annotated with @ApplicationScope will have a unique instance in this Component
@AppScope
// Definition of a Dagger component that adds info from the different modules to the graph
@Component(
        modules = {
                ViewModelModule.class,
                AppSubComponent.class
        }
)
public interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        /**
         * @param context With @BindsInstance, the Context passed in will be available in the graph
         */
        AppComponent create(@BindsInstance Context context);
    }

    // Types that can be retrieved from the graph
    MainComponent.Factory mainComponent();
}
