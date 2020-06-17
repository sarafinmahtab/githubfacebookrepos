package com.android.githubfacebookrepos.helpers;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import androidx.annotation.NonNull;


/**
 * Response holder provided to the UI
 */
public final class ResponseHolder<T> {


    private Status status;
    private T data;
    private Throwable error;


    private ResponseHolder(Status status, T data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }


    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    @NonNull
    @Override

    public String toString() {

        if (status == Status.SUCCESS) {
            return "Success[data=" + getData() + "]";
        } else if (status == Status.ERROR) {
            return "Error[exception=" + getError() + "]";
        } else {
            return super.toString();
        }
    }

    public static <T> ResponseHolder<T> loading() {
        return new ResponseHolder<T>(
                Status.LOADING,
                null,
                null);
    }

    public static <T> ResponseHolder<T> success(T data) {
        return new ResponseHolder<T>(
                Status.SUCCESS,
                data,
                null
        );
    }

    public static <T> ResponseHolder<T> error(Throwable error) {
        return new ResponseHolder<T>(
                Status.ERROR,
                null,
                error
        );
    }

    /**
     * Possible status types of a response provided to the UI
     */
    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
