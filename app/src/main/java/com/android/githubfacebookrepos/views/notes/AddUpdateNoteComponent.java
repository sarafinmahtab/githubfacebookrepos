package com.android.githubfacebookrepos.views.notes;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import com.android.githubfacebookrepos.di.ActivityScope;

import dagger.Subcomponent;


// Scope annotation that the [AddUpdateNoteComponent] uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
@Subcomponent
public interface AddUpdateNoteComponent {

    // Factory to create instances of [MainComponent]
    @Subcomponent.Factory
    interface Factory {
        AddUpdateNoteComponent create();
    }

    // Classes that can be injected by this Component
    void inject(AddUpdateNoteActivity activity);
}
