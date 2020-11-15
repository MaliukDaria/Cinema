package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.RoleDao;
import com.dev.cinema.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends GenericDaoImpl<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Role getByName(String roleName) {
        try (Session session = sessionFactory.openSession()) {
            Query<Role> findByRoleNameQuery = session.createQuery(
                    "FROM Role WHERE roleName = :roleName", Role.class)
                    .setParameter("roleName", Role.of(roleName).getRoleName());
            return findByRoleNameQuery.getSingleResult();
        }
    }
}
