package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */


import android.content.Context;

import com.android.githubfacebookrepos.views.main.MainComponent;
import com.android.githubfacebookrepos.views.notes.AddUpdateNoteComponent;

import dagger.BindsInstance;
import dagger.Component;


/**
 * Scope annotation that the AppComponent uses
 * Classes annotated with @ApplicationScope will have a unique instance in this Component
 */
@AppScope
@Component(
        modules = {
                DatabaseModule.class,
                NetworkModule.class,
                RepositoryModule.class,
                ViewModelModule.class,
                AppModule.class,
                AppSubComponent.class
        }
)
public interface AppComponent {

    @Component.Factory
    interface Factory {
        /**
         * @param context With @BindsInstance, the Context passed in will be available in the graph
         */
        AppComponent create(@BindsInstance Context context);
    }

    // Component Graph
    MainComponent.Factory mainComponent();

    AddUpdateNoteComponent.Factory addUpdateNoteComponent();
}
