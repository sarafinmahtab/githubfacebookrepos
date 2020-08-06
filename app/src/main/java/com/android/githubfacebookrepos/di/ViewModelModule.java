package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.githubfacebookrepos.views.main.MainViewModel;
import com.android.githubfacebookrepos.views.notes.AddUpdateNoteViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;


@Module
public abstract class ViewModelModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddUpdateNoteViewModel.class)
    public abstract ViewModel bindAddUpdateNoteViewModel(AddUpdateNoteViewModel addUpdateNoteViewModel);
}
