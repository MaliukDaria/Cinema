package com.dev.cinema.service;

import java.util.List;

public interface GenericService<T> {
    T add(T item);

    List<T> getAll();
}
