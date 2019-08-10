package com.catchu.builders;

public abstract class XDBBuilder<T> {

    public abstract void build(T params);

    public void build() {
        build(null);
    }
}
