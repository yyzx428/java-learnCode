package com.example.factory;

public interface SqlExtractFactory<T, V> {
    V choose(T source) throws Exception;
}
