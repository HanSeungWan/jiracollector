package com.daou.jiracollector.dao.manager;

import com.daou.jiracollector.dao.HibernateUtil;
import com.daou.jiracollector.dao.entity.VersionTbEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by intern on 2016-03-31.
 */
public class VersionManager {

    public List getVersionNameRelease(String jiraProjectName){

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT version.versionMm, version.releaseBoolean FROM VersionTbEntity as version , ProjectTbEntity project WHERE project.projectTbId =  version.projectId and project.jiraProjectMm = :jiraProjectName";

        Query query = session.createQuery(hql);

        query.setParameter("jiraProjectName", jiraProjectName);

        List list = query.list();

        HibernateUtil.closeSession();

        return list;
    }

    public boolean updateRelease(String jiraProjectName, String versionName, int releaseStatus) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "UPDATE VersionTbEntity set releaseBoolean = :releaseStatus WHERE versionMm = :versionName and projectId = " +
                "(select project.id from ProjectTbEntity as project where project.jiraProjectMm=:jiraProjectName)";

        Query query = session.createQuery(hql);

        if (releaseStatus == 0) {
            query.setParameter("releaseStatus", false);
        } else if (releaseStatus == 1) {
            query.setParameter("releaseStatus", true);
        }
        query.setParameter("jiraProjectName", jiraProjectName);
        query.setParameter("versionName", versionName);

        int result = query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        return result > 0;
    }

    public void setVersion(int projectId, String version, boolean release) throws Exception {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        VersionTbEntity versionTb = new VersionTbEntity();

        versionTb.setProjectId(projectId);
        versionTb.setVersionMm(version);
        versionTb.setReleaseBoolean(release);

        session.save(versionTb);
        session.getTransaction().commit();

        HibernateUtil.closeSession();
    }

    public List getVersion(int projectId) {

        HibernateUtil.beginTransition();

        //ArrayList<Object> versionList = new ArrayList<>();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT version.versionMm FROM VersionTbEntity as version WHERE version.projectId = :projectId";

        Query query = session.createQuery(hql);

        query.setParameter("projectId", projectId);

        List list = query.list();

        /*
        for (Object obj : list) {
            versionList.add(String.valueOf(obj));
        }
        */

        HibernateUtil.closeSession();

        return list;
    }

    public List getVersionNameRelease(int projectId) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT version.versionMm, version.releaseBoolean FROM VersionTbEntity as version WHERE version.projectId = :projectId";

        Query query = session.createQuery(hql);

        query.setParameter("projectId", projectId);

        List list = query.list();

        HibernateUtil.closeSession();

        return list;
    }

    public int getVersionId(String versionName) {

        int id = 0;

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT version.id FROM VersionTbEntity as version WHERE version.versionMm = :versionName";

        Query query = session.createQuery(hql);

        query.setParameter("versionName", versionName);

        List list = query.list();

        HibernateUtil.closeSession();

        for (Object aList : list) {
            id = (int) aList;
        }

        return id;
    }
}
