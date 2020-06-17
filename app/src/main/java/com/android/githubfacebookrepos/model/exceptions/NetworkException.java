package com.android.githubfacebookrepos.model.exceptions;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

/**
 * LocalException used for readability of network error
 */
public class NetworkException extends Exception {

    private int errorCode;

    public NetworkException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public NetworkException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
