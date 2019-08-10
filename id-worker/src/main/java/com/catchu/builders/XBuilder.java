package com.catchu.builders;

public abstract class XBuilder<T, O> {

    public abstract O build(T params);

    public O build() {
        return build(null);
    }
}
