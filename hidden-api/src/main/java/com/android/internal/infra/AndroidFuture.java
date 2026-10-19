package com.android.internal.infra;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.concurrent.CompletableFuture;


public class AndroidFuture<T> extends CompletableFuture<T> implements Parcelable {
    public AndroidFuture(){
        throw new RuntimeException("Stub!");
    }

    public static final Creator<AndroidFuture> CREATOR = new Creator<AndroidFuture>() {
        @Override
        public AndroidFuture createFromParcel(Parcel in) {
            throw new RuntimeException("Stub!");
        }

        @Override
        public AndroidFuture[] newArray(int size) {
            throw new RuntimeException("Stub!");
        }
    };

    @Override
    public int describeContents() {
        throw new RuntimeException("Stub!");
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        throw new RuntimeException("Stub!");
    }
}
