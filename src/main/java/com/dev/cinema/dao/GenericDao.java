package com.dev.cinema.dao;

import java.util.List;

public interface GenericDao<T> {
    T add(T item);

    List<T> getAll();
}
