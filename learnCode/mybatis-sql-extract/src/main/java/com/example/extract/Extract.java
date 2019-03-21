package com.example.extract;

public interface Extract<T, V> {

    Class<?> getMatchClass();

    V getSql(T source) throws Exception;
}
