package com.daou.jiracollector.dao.manager.tbmanager;

import com.daou.jiracollector.dao.HibernateUtil;
import com.daou.jiracollector.dao.entity.UserTbEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * Created by intern on 2016-03-24.
 */
public class UserTbManager {

    public boolean insertUser(String name) {

        HibernateUtil.beginTransition();
        Session session = HibernateUtil.getCurrentSeesion();

        UserTbEntity userTbEntity = new UserTbEntity();

        userTbEntity.setNameMm(name);

        session.save(userTbEntity);

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        return true;
    }

    public int selectUserTbId(String userName) {

        int id = 0;

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT user.userTbId FROM UserTbEntity as user WHERE user.nameMm = :userName";

        Query query = session.createQuery(hql);

        query.setParameter("userName", userName);

        List<Object> list = query.list();

        HibernateUtil.closeSession();

        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            int obj = (int) itr.next();
            id = obj;
        }

        return id;
    }

    public String selectUserName(int userId) {

        String userName = "";

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT user.nameMm FROM UserTbEntity as user where user.userTbId=:userId";
        Query query = session.createQuery(hql);
        query.setParameter("userId",userId);

        List<Object> list = query.list();

        HibernateUtil.closeSession();

        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            String  obj = (String) itr.next();
            userName = obj;
        }

        return userName;
    }
}
