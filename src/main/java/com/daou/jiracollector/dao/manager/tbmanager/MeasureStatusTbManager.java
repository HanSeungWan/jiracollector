package com.daou.jiracollector.dao.manager.tbmanager;

import com.daou.jiracollector.dao.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by intern on 2016-03-16.
 */
public class MeasureStatusTbManager {

    public boolean deleteMeasureStatusTb(int versionId, int projectId, String date) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "delete FROM MeasureStatusTbEntity as measureStatus where measureStatus.versionId = :versionId and measureStatus .projectId = :projectId and measureStatus.dateYdm = :date";

        Query query = session.createQuery(hql);

        query.setParameter("versionId", versionId);
        query.setParameter("projectId", projectId);
        query.setParameter("date", date);

        int result = query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        if (result > 0) return true;
        else return false;
    }
}
