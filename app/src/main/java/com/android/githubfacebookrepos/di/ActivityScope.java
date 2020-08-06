package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


/**
 * Identifies a type that the injector only instantiates once in application lifecycle. Not inherited.
 *
 * @see javax.inject.Scope @Scope
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScope {

}
