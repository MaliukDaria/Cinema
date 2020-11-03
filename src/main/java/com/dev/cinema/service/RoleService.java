package com.dev.cinema.service;

import com.dev.cinema.model.Role;

public interface RoleService extends GenericService<Role> {
    Role getRoleByName(String roleName);
}
