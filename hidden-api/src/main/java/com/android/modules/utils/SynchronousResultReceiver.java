package com.android.modules.utils;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public final class SynchronousResultReceiver<T> implements Parcelable {
    ISynchronousResultReceiver mReceiver;
    private SynchronousResultReceiver(Parcel in) {
        throw new RuntimeException("Stub!");
    }
    private SynchronousResultReceiver(){
        throw new RuntimeException("Stub!");
    }

    public static <T> SynchronousResultReceiver<T> get(){
        throw new RuntimeException("Stub!");
    }

    public void send(T resultData){
        throw new RuntimeException("Stub!");
    }

    public static final Creator<SynchronousResultReceiver<?>> CREATOR = new Creator<SynchronousResultReceiver<?>>() {
        @Override
        public SynchronousResultReceiver<?> createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public SynchronousResultReceiver<?>[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }


    public static class Result<T> implements Parcelable {
        public static final Parcelable.Creator<Result<?>> CREATOR = new Parcelable.Creator<Result<?>>() {
            @Override // android.os.Parcelable.Creator
            public Result<?> createFromParcel(Parcel in) {
                throw new RuntimeException("Stub!");
            }

            @Override // android.os.Parcelable.Creator
            public Result<?>[] newArray(int size) {
                throw new RuntimeException("Stub!");
            }
        };
        private final RuntimeException mException;
        private final T mObject;

        public Result(RuntimeException exception) {
            throw new RuntimeException("Stub!");
        }

        public Result(T object) {
            throw new RuntimeException("Stub!");
        }

        public T getValue(T defaultValue) {
            throw new RuntimeException("Stub!");
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(@NonNull Parcel out, int flags) {
            throw new RuntimeException("Stub!");
        }

        private Result(Parcel parcel) {
            throw new RuntimeException("Stub!");
        }
    }

}
