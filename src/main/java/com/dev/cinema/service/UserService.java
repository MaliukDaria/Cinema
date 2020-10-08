package com.dev.cinema.service;

import com.dev.cinema.model.User;
import java.util.Optional;

public interface UserService extends GenericService<User> {
    Optional<User> findByEmail(String email);
}
