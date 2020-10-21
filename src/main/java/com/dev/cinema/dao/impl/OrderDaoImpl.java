package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order> implements OrderDao {
    public OrderDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<Order> getUserOrdersQuery = session.createQuery(
                    "SELECT DISTINCT o FROM Order o "
                            + "JOIN FETCH o.tickets t "
                            + "WHERE o.user.id = :userId", Order.class)
                    .setParameter("userId", user.getId());
            return getUserOrdersQuery.getResultList();
        }
    }
}
