package com.daou.jiracollector.dao.manager;

import com.daou.jiracollector.dao.HibernateUtil;
import com.daou.jiracollector.dao.entity.UserTbEntity;
import com.daou.jiracollector.dao.manager.tbmanager.UserTbManager;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class UserManager {

    public boolean setUser(String userName){

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        UserTbEntity userTbEntity = new UserTbEntity();

        userTbEntity.setNameMm(userName);

        session.save(userTbEntity);

        session.getTransaction().commit();
        HibernateUtil.closeSession();

        return true;
    }

    public String getUser(int id){

        String userName = "";

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT user.nameMm FROM UserTbEntity as user WHERE user.userTbId = :id";

        Query query = session.createQuery(hql);

        query.setParameter("id", id);

        List<Object> list = query.list();

        HibernateUtil.closeSession();

        for (Object aList : list) {
            String obj = (String) aList;
            userName = obj;
        }

        return userName;
    }
}
