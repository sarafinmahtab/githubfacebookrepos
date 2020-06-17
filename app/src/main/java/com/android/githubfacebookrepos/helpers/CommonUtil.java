package com.android.githubfacebookrepos.helpers;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

        if (throwable instanceof LocalException) {
            return ((LocalException) throwable).getMessage();
        } else if (throwable instanceof ServerException) {
            return ((ServerException) throwable).getMessage();
        } else if (throwable instanceof NetworkException) {
            return ((NetworkException) throwable).getMessage();
        } else {
            return throwable.getMessage();
        }
    }

    public static boolean isNetworkConnectionAvailable(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
