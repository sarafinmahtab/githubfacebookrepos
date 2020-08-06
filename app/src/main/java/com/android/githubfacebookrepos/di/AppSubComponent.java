package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import com.android.githubfacebookrepos.views.main.MainComponent;
import com.android.githubfacebookrepos.views.notes.AddUpdateNoteComponent;

import dagger.Module;


/**
 * This module tells a Component which are its sub-components
 */
@Module(
        subcomponents = {
                MainComponent.class,
                AddUpdateNoteComponent.class
        }
)
public class AppSubComponent {
}
