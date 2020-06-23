package com.android.githubfacebookrepos.helpers;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.githubfacebookrepos.model.exceptions.LocalException;
import com.android.githubfacebookrepos.model.exceptions.NetworkException;
import com.android.githubfacebookrepos.model.exceptions.ServerException;

public final class CommonUtil {

    public static Exception prepareErrorResult(int errorCode, String message) {
        if (String.valueOf(errorCode).startsWith("4")) {
            return new LocalException(message, errorCode);
        } else if (String.valueOf(errorCode).startsWith("5")) {
            return new ServerException(message, errorCode);
        } else {
            return new NetworkException(message, errorCode);
        }
    }

    public static String prepareErrorMessage(Throwable throwable) {

        String errorMessage = throwable.getMessage() == null ? throwable.toString() : throwable.getMessage();

        if (throwable instanceof LocalException) {
            return errorMessage;
        } else if (throwable instanceof ServerException) {
            return errorMessage;
        } else if (throwable instanceof NetworkException) {
            return errorMessage;
        } else {
            return errorMessage;
        }
    }

    public static boolean isNetworkConnectionAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }

    public static void showSoftKeyboard(Activity activity, View view) {
        view.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void showSoftKeyboardForced(Activity activity, View view) {
        view.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            hideSoftKeyboard(activity, view);
        }
    }
}
