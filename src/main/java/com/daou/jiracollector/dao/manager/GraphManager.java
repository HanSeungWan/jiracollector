package com.daou.jiracollector.dao.manager;

import com.daou.jiracollector.dao.HibernateUtil;
import com.daou.jiracollector.dao.entity.IssuetypeTransitionTbEntity;
import com.daou.jiracollector.dao.entity.MeasureStatusTbEntity;
import com.daou.jiracollector.dao.manager.tbmanager.IssuetypeTbManager;
import com.daou.jiracollector.dao.manager.tbmanager.MeasureStatussettingTbManager;
import com.daou.jiracollector.dao.manager.tbmanager.ProjectTbManager;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

public class GraphManager {

    public HashMap<String, Integer> getIssuetypeTransition(String versionName, String jiraProjectName, String date) {

        IssuetypeTbManager issuetypeTbManager = new IssuetypeTbManager();

        List issuetypeList = issuetypeTbManager.getAllIssuetypeList(jiraProjectName);

        HibernateUtil.beginTransition();
        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT issuetype.issuetypeMm , issuetypeTransition.countNo " +
                "FROM IssuetypeTransitionTbEntity as issuetypeTransition , IssuetypeTbEntity as issuetype , VersionTbEntity  as version , ProjectTbEntity  as project " +
                "WHERE issuetype.issuetypeTbId = issuetypeTransition.issuetypeId " +
                "and issuetypeTransition.versionId = version.versionTbId " +
                "and issuetypeTransition.projectId = project.projectTbId " +
                "and issuetypeTransition.dateYdm = :date " +
                "AND project.projectTbId = (select project2.projectTbId from ProjectTbEntity as project2 where project2.jiraProjectMm=:jiraProjectName) " +
                "AND version.versionTbId = (select version2.versionTbId from VersionTbEntity as version2 , ProjectTbEntity as project3 where project3.projectTbId = version2.projectId and version2.versionMm=:versionName and project3.jiraProjectMm=:jiraProjectName)";

        Query query = session.createQuery(hql);

        query.setParameter("versionName",versionName);
        query.setParameter("jiraProjectName", jiraProjectName);
        query.setParameter("date", date);

        List list = query.list();
        HibernateUtil.closeSession();

        HashMap<String, Integer> issueMeasureStatusList = new HashMap<>();

        for (Object statusName : issuetypeList) {

            issueMeasureStatusList.put(statusName.toString(), 0);
        }

        for (Object aList : list) {
            Object[] obj = (Object[]) aList;

            issueMeasureStatusList.put(String.valueOf(obj[0]), Integer.parseInt(String.valueOf(obj[1])));
        }
        return issueMeasureStatusList;
    }

    public HashMap<String, Integer> getIssueMeasureStatus(String versionName, String jiraProjectName, String date) {

        MeasureStatussettingTbManager measureStatussettingTbManager = new MeasureStatussettingTbManager();
        ProjectTbManager projectTbManager = new ProjectTbManager();

        List statusList = measureStatussettingTbManager.selectStatusName(projectTbManager.selectProjectId(jiraProjectName));

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "SELECT DISTINCT measureStatus.statusMm , measure.countNo " +
                "FROM MeasureStatusSettingTbEntity as measureStatus , MeasureStatusTbEntity as measure , VersionTbEntity  as version , ProjectTbEntity  as project " +
                "WHERE measureStatus.measureStatusSettingTbId = measure.statusId " +
                "AND measure.projectId = project.projectTbId AND measure.versionId =version.versionTbId " +
                "AND measure.dateYdm = :date " +
                "AND project.projectTbId = (select project2.projectTbId from ProjectTbEntity as project2 where project2.jiraProjectMm=:jiraProjectName) " +
                "AND version.versionTbId = (select version2.versionTbId from VersionTbEntity as version2 , ProjectTbEntity as project3 where project3.projectTbId = version2.projectId and version2.versionMm=:versionName and project3.jiraProjectMm=:jiraProjectName)";

        Query query = session.createQuery(hql);

        query.setParameter("versionName",versionName);
        query.setParameter("jiraProjectName", jiraProjectName);
        query.setParameter("date", date);

        List list = query.list();

        HibernateUtil.closeSession();

        HashMap<String, Integer> issueMeasureStatusList = new HashMap<>();

        // 초기화
        for (Object statusName : statusList) {

            issueMeasureStatusList.put(statusName.toString(), 0);
        }

        for (Object aList : list) {
            Object[] obj = (Object[]) aList;

            issueMeasureStatusList.put(String.valueOf(obj[0]), Integer.parseInt(String.valueOf(obj[1])));
        }

        return issueMeasureStatusList;
    }

    public boolean setIssuetypeTransition(int versionId, int projectId, int issuetypeId, String date,
                                               int count) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        IssuetypeTransitionTbEntity issuetypeTransition = new IssuetypeTransitionTbEntity();

        issuetypeTransition.setIssuetypeId(issuetypeId);
        issuetypeTransition.setVersionId(versionId);
        issuetypeTransition.setProjectId(projectId);
        issuetypeTransition.setDateYdm(date);
        issuetypeTransition.setCountNo(count);

        session.save(issuetypeTransition);

        session.getTransaction().commit();

        HibernateUtil.closeSession();

        return true;
    }

    public boolean setMeasureStatusTb(int versionId, int projectId, int statusId, String date,
                                         int count) {
        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        MeasureStatusTbEntity measureStatus = new MeasureStatusTbEntity();

        measureStatus.setStatusId(statusId);
        measureStatus.setVersionId(versionId);
        measureStatus.setProjectId(projectId);
        measureStatus.setDateYdm(date);
        measureStatus.setCountNo(count);

        session.save(measureStatus);

        session.getTransaction().commit();

        HibernateUtil.closeSession();

        return true;
    }

    public boolean deleteMeasureStatus(String versionName, String jiraProjectName, String date) {

        HibernateUtil.beginTransition();

        Session session = HibernateUtil.getCurrentSeesion();

        String hql = "delete FROM MeasureStatusTbEntity as measureStatus " +
                "where measureStatus.versionId = (select version.versionTbId from VersionTbEntity as version , ProjectTbEntity as project where version.versionMm =: versionName and project.jiraProjectMm =: jiraProjectName) " +
                "and measureStatus .projectId = (select proejct.projectTbId from ProjectTbEntity as proejct where proejct.jiraProjectMm=:jiraProjectName) " +
                "and measureStatus.dateYdm = :date";

        Query query = session.createQuery(hql);

        query.setParameter("versionName", versionName);
        query.setParameter("jiraProjectName", jiraProjectName);
        query.setParameter("date", date);

        int result = query.executeUpdate();

        HibernateUtil.commit();
        HibernateUtil.closeSession();

        return result > 0;
    }



}
