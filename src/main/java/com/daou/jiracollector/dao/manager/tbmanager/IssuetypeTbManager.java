package com.daou.jiracollector.dao.manager.tbmanager;

import com.daou.jiracollector.dao.HibernateUtil;
import com.daou.jiracollector.dao.entity.IssuetypeTbEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

public class IssuetypeTbManager {

    public int getIssuetypeId(String issuetype){

        int id = 0;

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT issuetype.issuetypeTbId FROM IssuetypeTbEntity as issuetype WHERE issuetype.issuetypeMm = :issuetype";

        Query query = session.createQuery(hql);

        query.setParameter("issuetype", issuetype);

        List list = query.list();

        HibernateUtil.closeSession();

        for (Object aList : list) {
            int obj = (int) aList;
            id = obj;
        }

        return id;
    }

    public boolean setIssuetype(String name, int projectId) {

        HibernateUtil.beginTransition();
        Session session = HibernateUtil.getCurrentSeesion();

        IssuetypeTbEntity issuetypeTbEntity = new IssuetypeTbEntity();

        issuetypeTbEntity.setIssuetypeMm(name);
        issuetypeTbEntity.setProjectId(projectId);

        session.save(issuetypeTbEntity);

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        return true;
    }

    public List getAllIssuetypeList(String jiraProjectName){

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT issuetype.issuetypeMm FROM IssuetypeTbEntity as issuetype WHERE issuetype.projectId=" +
                "(SELECT project.projectTbId FROM ProjectTbEntity as project WHERE project.jiraProjectMm=:jiraProjectName)";

        Query query = session.createQuery(hql);
        query.setParameter("jiraProjectName", jiraProjectName);

        List list = query.list();

        HibernateUtil.closeSession();

        return list;
    }
}
