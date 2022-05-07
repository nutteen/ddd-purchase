package com.example.ddd.purchase.domain.common;

public interface Aggregate<I, S> {
    I getId();
    S getState();
}
