package com.daou.jiracollector.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by intern on 2016-03-14.
 */
public class HibernateUtil {

    public static SessionFactory sessionFactory = null;
    static ServiceRegistry serviceRegistry;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Session getCurrentSeesion() {
        return sessionFactory.getCurrentSession();
    }

    public static Transaction beginTransition() {
        return getCurrentSeesion().beginTransaction();
    }

    public static void commit() {
        getCurrentSeesion().getTransaction().commit();
    }

    public static void closeSession() {
        getCurrentSeesion().close();
    }

    public static void rollBack() {
        if (getCurrentSeesion().isOpen()) {
            Transaction tx = getCurrentSeesion().getTransaction();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }
}
