package com.example.a3175.db;

public interface A3175ViewModel<T> {
    void insert(T... t);

    void update(T... t);

    void delete(T... t);
}
