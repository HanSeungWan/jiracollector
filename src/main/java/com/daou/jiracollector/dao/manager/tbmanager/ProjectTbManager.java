package com.daou.jiracollector.dao.manager.tbmanager;

import com.daou.jiracollector.dao.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

/**
 * Created by intern on 2016-03-16.
 */
public class ProjectTbManager {

    public List selectJiraProjectName() {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT project.jiraProjectMm FROM ProjectTbEntity as project ";

        Query query = session.createQuery(hql);

        List<Object> list = query.list();

        HibernateUtil.closeSession();

        return list;
    }


    public String selectJiraProjectName(String projectName) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT project.projectMm FROM ProjectTbEntity as project WHERE project.jiraProjectMm=:projectName";

        Query query = session.createQuery(hql);

        query.setParameter("projectName", projectName);

        List<Object> list = query.list();

        HibernateUtil.closeSession();

        Object jiraProjectName = (Object) list.get(0);

        return jiraProjectName.toString();
    }


    public int selectProjectId(String projectName) {

        int id = 0;

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT project.projectTbId FROM ProjectTbEntity as project WHERE project.projectMm = :projectName or project.jiraProjectMm = :projectName";

        Query query = session.createQuery(hql);

        query.setParameter("projectName", projectName);

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
