package com.daou.jiracollector.dao.manager;

import com.daou.jiracollector.dao.HibernateUtil;
import com.daou.jiracollector.dao.entity.ProjectTbEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by intern on 2016-03-31.
 */
public class ProjectManager {

    public List getAllProjectList() {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT project.projectMm , project.jiraProjectMm , project.releaseNo , project.managerId, project.activeBoolean , project.projectTbId FROM ProjectTbEntity as project";

        Query query = session.createQuery(hql);

        List<Object> list = query.list();

        HibernateUtil.closeSession();

        return list;
    }

    public List getAllManagerName() {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT user.nameMm FROM UserTbEntity as user";

        Query query = session.createQuery(hql);

        List<Object> list = query.list();

        HibernateUtil.closeSession();

        return list;
    }

    public boolean setProjectList(String projectName, String jiraProjectName, int managerId, boolean active, int releaseNo) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        ProjectTbEntity projectTbEntity = new ProjectTbEntity();

        projectTbEntity.setManagerId(managerId);
        projectTbEntity.setProjectMm(projectName);
        projectTbEntity.setJiraProjectMm(jiraProjectName);
        projectTbEntity.setActiveBoolean(active);
        projectTbEntity.setReleaseNo(releaseNo);

        session.save(projectTbEntity);

        session.getTransaction().commit();

        HibernateUtil.closeSession();

        return true;
    }

    public boolean updateProjectListActive(String jiraProjectName, int active) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "UPDATE ProjectTbEntity set activeBoolean = :active WHERE projectTbId = " +
                "(select project.projectTbId from ProjectTbEntity project where projectMm =:jiraProjectName)";

        Query query = session.createQuery(hql);

        if (active == 0) {
            query.setParameter("active", false);
        } else if (active == 1) {
            query.setParameter("active", true);
        }
        query.setParameter("jiraProjectName", jiraProjectName);

        int result = query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        if(result > 0) return true;
        else return false;
    }

    public boolean updateProjectList(String projectName, String jiraProejctName, String managerName, int projectId) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "UPDATE ProjectTbEntity as project " +
                "set project.jiraProjectMm = :jiraProejctName , project.projectMm = :projectName  , project.managerId = " +
                "(SELECT user.id FROM UserTbEntity as user WHERE user.nameMm = :managerName) " +
                "where project.id = :projectId ";

        Query query = session.createQuery(hql);

        query.setParameter("projectName",projectName);
        query.setParameter("jiraProejctName",jiraProejctName);
        query.setParameter("managerName",managerName);
        query.setParameter("projectId",projectId);

        int result = query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        if(result > 0) return true;
        else return false;
    }

    public boolean updateProjectReleaseCount(String jiraProjectName){

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "UPDATE ProjectTbEntity as project " +
                "set project.releaseNo = (SELECT count(version.versionTbId) FROM VersionTbEntity as version WHERE version.projectId = " +
                "(SELECT project.projectTbId FROM ProjectTbEntity  as project WHERE project.jiraProjectMm =:jiraProjectName) AND version.releaseBoolean=true) " +
                "where project.jiraProjectMm = :jiraProjectName";

        Query query = session.createQuery(hql);

        query.setParameter("jiraProjectName",jiraProjectName);

        int result = query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        return result > 0;
    }
}
