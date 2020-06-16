package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelMap;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelMap) {
        this.viewModelMap = viewModelMap;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        Provider<ViewModel> creator = viewModelMap.get(modelClass);
        if (creator == null) {
            // if the viewmodel has not been created
            try {
                // iterate through the allowable keys (aka allowed classes with the @ViewModelKey)
                creator = viewModelMap.entrySet()
                        .stream()
                        .filter(entry -> modelClass.isAssignableFrom(entry.getKey()))
                        .findFirst()
                        .orElseThrow(() -> new Throwable(""))
                        .getValue();

                return (T) creator.get();
            } catch (Throwable throwable) {
                throw new IllegalArgumentException("Unknown model class " + modelClass);
            }
        }

        // return the Provider
        try {
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
