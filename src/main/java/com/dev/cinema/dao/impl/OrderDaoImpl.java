package com.dev.cinema.dao.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.lib.Dao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.User;
import com.dev.cinema.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Dao
public class OrderDaoImpl extends GenericDaoImpl<Order> implements OrderDao {
    @Override
    public List<Order> getUserOrders(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Order> getUserOrdersQuery = session.createQuery(
                    "SELECT DISTINCT o FROM Order o "
                            + "JOIN FETCH o.tickets t "
                            + "WHERE o.user.id = :userId", Order.class)
                    .setParameter("userId", user.getId());
            return getUserOrdersQuery.getResultList();
        }
    }
}
