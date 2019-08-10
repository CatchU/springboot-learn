package com.catchu.routers;


public abstract class XDBRouter<T, O> {

    public abstract O getRouterIndexW(T params);
}
