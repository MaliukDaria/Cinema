package com.dev.cinema.service;

public interface GenericService<T> {
    T add(T item);

    T get(Long id);
}
