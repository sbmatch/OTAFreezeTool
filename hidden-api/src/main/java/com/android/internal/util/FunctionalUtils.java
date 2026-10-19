package com.android.internal.util;

import java.util.function.Consumer;

public class FunctionalUtils {
    private FunctionalUtils() {}
    public interface ThrowingConsumer<T> extends Consumer<T> {
        void acceptOrThrow(T t) throws Exception;
        @Override
        default void accept(T t) {
            throw new RuntimeException("Stub!");
        }
    }

}
