package com.daou.jiracollector.dao.manager.tbmanager;

import com.daou.jiracollector.dao.HibernateUtil;
import com.daou.jiracollector.dao.entity.IndicatorTbEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by intern on 2016-04-06.
 */
public class IndicatorTbManager {

    public int getIndicatorId(String indicator){

        int id = 0;

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT indicator.indicatorId FROM IndicatorTbEntity as indicator WHERE indicator.indicatorMm = :indicator";

        Query query = session.createQuery(hql);

        query.setParameter("indicator", indicator);

        List list = query.list();

        HibernateUtil.closeSession();

        for (Object aList : list) {
            int obj = (int) aList;
            id = obj;
        }

        return id;
    }

    public boolean setIndicator(String indicator){

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        IndicatorTbEntity indicatorTbEntity = new IndicatorTbEntity();

        indicatorTbEntity.setIndicatorMm(indicator);

        session.save(indicatorTbEntity);

        session.getTransaction().commit();

        HibernateUtil.closeSession();

        return true;
    }
}
