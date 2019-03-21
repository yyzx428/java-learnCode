package com.example.extract;

import com.example.factory.SqlExtractFactory;


public abstract class AbstractExtract<T, V> implements Extract<T, V> {
    protected SqlExtractFactory<T, V> factory;

    public AbstractExtract(SqlExtractFactory<T, V> factory) {
        this.factory = factory;
    }
}
