package com.daou.jiracollector.dao.manager.tbmanager;

import com.daou.jiracollector.dao.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * Created by intern on 2016-03-16.
 */
public class MeasureStatussettingTbManager {

    public List selectProjectStatus(int projectId, String measrue) {

        HibernateUtil.beginTransition();
        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT setting.statusMm FROM MeasureStatusSettingTbEntity as setting WHERE setting.projectId = :projectId AND setting.measureMm = :measure";

        Query query = session.createQuery(hql);

        query.setParameter("measure", measrue);
        query.setParameter("projectId", projectId);

        List list = query.list();
        HibernateUtil.closeSession();

        return list;

    }

    public List selectJiraStatusName(int projectId){

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql ="SELECT setting.jirastatusMm FROM MeasureStatusSettingTbEntity as setting WHERE setting.projectId=:projectId";

        Query query = session.createQuery(hql);
        query.setParameter("projectId",projectId);

        List list = query.list();

        HibernateUtil.closeSession();

        return list;
    }

    public List selectStatusName(int projectId){

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql ="SELECT setting.statusMm FROM MeasureStatusSettingTbEntity as setting WHERE setting.projectId=:projectId";

        Query query = session.createQuery(hql);
        query.setParameter("projectId",projectId);

        List list = query.list();

        HibernateUtil.closeSession();

        return list;
    }

    public int getMeasureStatusId(String jiraStatus){

        int id = 0;

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT measureStatus.measureStatusSettingTbId FROM MeasureStatusSettingTbEntity as measureStatus WHERE measureStatus.jirastatusMm = :jiraStatus";

        Query query = session.createQuery(hql);

        query.setParameter("jiraStatus", jiraStatus);

        List<Object> list = query.list();

        HibernateUtil.closeSession();

        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            int obj = (int) itr.next();
            id = obj;
        }

        return id;
    }
}
