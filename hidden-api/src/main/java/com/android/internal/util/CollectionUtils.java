package com.android.internal.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> boolean contains(Collection<T> collection, T element) {
        throw new RuntimeException("Stub!");
    }

    public static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        throw new RuntimeException("Stub!");
    }

    public static <T> Set<T> filter(Set<T> set, Predicate<? super T> predicate) {
        throw new RuntimeException("Stub!");
    }

    public static <T> void addIf(List<T> source, Collection<? super T> dest, Predicate<? super T> predicate) {
        throw new RuntimeException("Stub!");
    }

    public static <I, O> List<O> map(List<I> cur, Function<? super I, ? extends O> f) {
        throw new RuntimeException("Stub!");
    }

    public static <I, O> Set<O> map(Set<I> set, Function<? super I, ? extends O> function) {
        throw new RuntimeException("Stub!");
    }

    public static <I, O> List<O> mapNotNull(List<I> cur, Function<? super I, ? extends O> f) {
        throw new RuntimeException("Stub!");
    }

    public static <T> List<T> emptyIfNull(List<T> cur) {
        throw new RuntimeException("Stub!");
    }

    public static <T> Set<T> emptyIfNull(Set<T> cur) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> Map<K, V> emptyIfNull(Map<K, V> cur) {
        throw new RuntimeException("Stub!");
    }

    public static int size(Collection<?> cur) {
        throw new RuntimeException("Stub!");
    }

    public static int size(Map<?, ?> cur) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isEmpty(Collection<?> cur) {
        throw new RuntimeException("Stub!");
    }

    public static boolean isEmpty(Map<?, ?> cur) {
        throw new RuntimeException("Stub!");
    }

    public static <T> List<T> filter(List<?> list, Class<T> c) {
        throw new RuntimeException("Stub!");
    }

    public static <T> boolean any(List<T> items, Predicate<T> predicate) {
        throw new RuntimeException("Stub!");
    }

    public static <T> boolean any(Set<T> items, Predicate<T> predicate) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T find(List<T> items, Predicate<T> predicate) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T find(Set<T> cur, Predicate<T> predicate) {
        throw new RuntimeException("Stub!");
    }

    public static <T> List<T> add(List<T> cur, T val) {
        throw new RuntimeException("Stub!");
    }

    public static <T> List<T> add(List<T> cur, int index, T val) {
        throw new RuntimeException("Stub!");
    }

    public static <T> Set<T> addAll(Set<T> cur, Collection<T> collection) {
        throw new RuntimeException("Stub!");
    }

    public static <T> Set<T> add(Set<T> cur, T val) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> Map<K, V> add(Map<K, V> map, K key, V value) {
        throw new RuntimeException("Stub!");
    }

    public static <T> List<T> remove(List<T> cur, T val) {
        throw new RuntimeException("Stub!");
    }

    public static <T> Set<T> remove(Set<T> cur, T val) {
        throw new RuntimeException("Stub!");
    }

    public static <T> List<T> copyOf(List<T> cur) {
        throw new RuntimeException("Stub!");
    }

    public static <T> Set<T> copyOf(Set<T> cur) {
        throw new RuntimeException("Stub!");
    }

    public static <T> Set<T> toSet(Collection<T> cur) {
        throw new RuntimeException("Stub!");
    }

    public static <K, V> void forEach(Map<K, V> cur, BiConsumer<K, V> biConsumer) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T firstOrNull(List<T> cur) {
        throw new RuntimeException("Stub!");
    }

    public static <T> T firstOrNull(Collection<T> cur) {
        throw new RuntimeException("Stub!");
    }

    public static <T> List<T> singletonOrEmpty(T item) {
        throw new RuntimeException("Stub!");
    }
}

