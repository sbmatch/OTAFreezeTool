package com.miui.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

public class MiuiStubUtil {
    @Deprecated
    public static <T> T getImpl(Class<T> cls) {
        return (T) getInstance(cls);
    }

    public static <T> T getInstance(Class<T> cls) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T getInstance(Class<T> cls, T t) {
        throw new RuntimeException("Stub!");
    }

//    public static <T> ImplSupplier<T> getImplSupplier(Class<T> clazz, ImplSupplier<T> defSupplier) {
//        throw new RuntimeException("Stub!");
//    }

    public static <T> T newInstance(Class<T> cls) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T newInstance(Class<T> cls, T t) {
        throw new RuntimeException("Stub!");
    }

    private static <T> T createDefaultImpl(Class<T> cls) {
        throw new RuntimeException("Stub!");
    }

    public static Constructor<?> getDefaultConstructor(Class<?> clazz) {
        throw new RuntimeException("Stub!");
    }

    public static Object createNewInstance(Class<?> clazz) {
        throw new RuntimeException("Stub!");
    }

    private static InvocationHandler createInvocationHandler() {
        throw new RuntimeException("Stub!");
    }

}
