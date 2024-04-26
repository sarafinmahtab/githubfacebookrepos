package com.android.githubfacebookrepos.model.exceptions;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */


public class ServerException extends Exception {

    private int errorCode = 500;

    public ServerException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServerException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
