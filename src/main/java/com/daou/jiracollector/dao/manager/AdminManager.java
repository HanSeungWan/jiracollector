package com.daou.jiracollector.dao.manager;

import com.daou.jiracollector.dao.entity.AdminTbEntity;
import com.daou.jiracollector.dao.HibernateUtil;
import com.daou.jiracollector.dao.entity.IndicatorSettingTbEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class AdminManager {

    public void setStartEndDate(int versionId, int projectId, String startDate, String endDate) {

        HibernateUtil.beginTransition();
        Session session = HibernateUtil.getCurrentSeesion();

        AdminTbEntity admin = new AdminTbEntity();

        admin.setProjectId(projectId);
        admin.setVersionId(versionId);
        admin.setStartdateYmd(startDate);
        admin.setEnddateYmd(endDate);

        session.save(admin);

        HibernateUtil.commit();
        HibernateUtil.closeSession();
    }

    public List getStartEndDate(String versionName, String jiraProjectName) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT admin.startdateYmd , admin.enddateYmd FROM AdminTbEntity as admin , VersionTbEntity  as version , ProjectTbEntity as project " +
                "WHERE admin.projectId =  project.projectTbId and admin.versionId = version.versionTbId and version.versionMm = :versionName and project.jiraProjectMm = :jiraProjectName";

        Query query = session.createQuery(hql);

        query.setParameter("versionName", versionName);
        query.setParameter("jiraProjectName", jiraProjectName);

        List list = query.list();

        HibernateUtil.closeSession();

        return list;
    }

    public boolean checkHasdate(String versionName, String jiraProjectName) {

        HibernateUtil.beginTransition();
        Session session = HibernateUtil.getCurrentSeesion();

        Object count = "";

        String hql = "SELECT count(admin.adminTbId) FROM AdminTbEntity as admin , VersionTbEntity  as version , ProjectTbEntity as project " +
                "WHERE admin.projectId =  project.projectTbId and admin.versionId = version.versionTbId and version.versionMm = :versionName and project.jiraProjectMm = :jiraProjectName";

        Query query = session.createQuery(hql);

        query.setParameter("versionName", versionName);
        query.setParameter("jiraProjectName", jiraProjectName);

        java.util.List list = query.list();

        HibernateUtil.closeSession();

        for (Object aList : list) {
            count = aList;
        }

        return Integer.parseInt(count.toString()) > 0;
    }

    public boolean setIndicator(int projectId, int graphtypeId, int targetNo, String targetChar, String explanation) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        IndicatorSettingTbEntity indicatorSettingTbEntity = new IndicatorSettingTbEntity();

        indicatorSettingTbEntity.setProjectId(projectId);
        indicatorSettingTbEntity.setGraphtypeId(graphtypeId);
        indicatorSettingTbEntity.setTargetNo(targetNo);
        indicatorSettingTbEntity.setTargetCharMm(targetChar);
        indicatorSettingTbEntity.setExplanationMm(explanation);

        session.save(indicatorSettingTbEntity);

        session.getTransaction().commit();

        HibernateUtil.closeSession();

        return true;
    }

    public List getIndicator() {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT (SELECT proejct2.jiraProjectMm FROM ProjectTbEntity as proejct2 WHERE proejct2.projectTbId = project.projectTbId) , " +
                "(SELECT indicatorList2.indicatorMm FROM IndicatorTbEntity as indicatorList2 WHERE indicatorList2.indicatorId=indicator.graphtypeId ) , " +
                "indicator.targetNo , indicator.targetCharMm , indicator.explanationMm " +
                "FROM IndicatorSettingTbEntity as indicator , ProjectTbEntity as project , IndicatorTbEntity as indicatorList " +
                "WHERE indicator.projectId=project.projectTbId and indicator.graphtypeId=indicatorList.indicatorId";

       Query query = session.createQuery(hql);

        List list = query.list();

        HibernateUtil.closeSession();

        return list;
    }

    public boolean updateIndicator(int projectId, String graphtype, int targetNo, String targetChar, String explanation) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "UPDATE IndicatorSettingTbEntity as indicator SET indicator.graphtypeId=(SELECT indicatorList.indicatorId FROM IndicatorTbEntity as indicatorList WHERE indicatorList.indicatorMm=:graphtype) , indicator.targetNo=:targetNo, indicator.targetCharMm=:targetChar, indicator.explanationMm=:explanation "
                + " WHERE indicator.projectId=:projectId and indicator.graphtypeId=(SELECT indicatorList.indicatorId FROM IndicatorTbEntity as indicatorList WHERE indicatorList.indicatorMm=:graphtype)";

        Query query = session.createQuery(hql);
        query.setParameter("projectId", projectId);
        query.setParameter("graphtype", graphtype);
        query.setParameter("targetNo", targetNo);
        query.setParameter("targetChar", targetChar);
        query.setParameter("explanation", explanation);

        int result = query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        if (result > 0) return true;
        else return false;
    }

    public boolean deleteIndicator(int projectId, String graphtype) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "DELETE FROM IndicatorSettingTbEntity as indicator " +
                "WHERE indicator.projectId=:projectId and indicator.graphtypeId=(SELECT indicatorList.indicatorId FROM IndicatorTbEntity as indicatorList WHERE indicatorList.indicatorMm=:graphtype)";

        Query query = session.createQuery(hql);
        query.setParameter("projectId", projectId);
        query.setParameter("graphtype", graphtype);

        int result = query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        return result > 0;
    }
}
