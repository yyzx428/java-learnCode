package com.example.factory;

import com.example.extract.Extract;

import java.util.List;
import java.util.Optional;

public abstract class AbstractSqlExtractFactory<E extends Extract<T, V>, T, V> implements SqlExtractFactory<T, V> {

    private final List<E> extracts;

    protected AbstractSqlExtractFactory() {
        this.extracts = initExacts();
    }

    @Override
    @SuppressWarnings("unchecked")
    public V choose(T source) throws Exception {
        if (source == null) {
            return (V) "";
        }

        Optional<E> match = extracts.stream().filter(x -> (source.getClass().isAssignableFrom(x.getMatchClass()))).findFirst();

        if (match.isPresent()) {
            return (V) match.get().getSql(source);
        } else {
            return (V) "";
        }
    }

    protected abstract List<E> initExacts();
}
