package com.dev.cinema.dao;

import java.util.Optional;

public interface GenericDao<T> {
    T add(T item);

    Optional<T> get(Long id, Class<T> clazz);
}
