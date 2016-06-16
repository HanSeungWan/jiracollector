package com.daou.jiracollector.jiradatamaker.scheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.daou.jiracollector.dao.manager.VersionManager;
import com.daou.jiracollector.dao.manager.tbmanager.MeasureStatusTbManager;
import com.daou.jiracollector.dao.manager.tbmanager.ProjectTbManager;
import com.daou.jiracollector.jiradatamaker.data.JiraDataMaker;
import org.apache.http.ParseException;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.daou.jiracollector.jiradatamaker.jiraDataMakerUtil;

public class JobMakeIssueMeasureStatus implements Job {

    private Logger logger = Logger.getLogger(getClass());

    /**
     * job 실행
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub

        try {
            makedayIssueMeasureStatus();
        } catch (NumberFormatException | ParseException | org.json.simple.parser.ParseException | ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void makedayIssueMeasureStatus() throws NumberFormatException, org.apache.http.ParseException,
            org.json.simple.parser.ParseException, ClassNotFoundException, SQLException {

        jiraDataMakerUtil jiraDataMakerUtil = new jiraDataMakerUtil();

        logger.info("Start Make Issue Measure Status Data...");

        ProjectTbManager projectTbManager = new ProjectTbManager();
        VersionManager versionManager = new VersionManager();
        MeasureStatusTbManager measureStatusTbManager = new MeasureStatusTbManager();

        try {
            for (Object productName : projectTbManager.selectJiraProjectName()) {

                int projectId  = projectTbManager.selectProjectId(productName.toString());

                List versionReleaseList = versionManager.getVersionNameRelease(projectId);
                List versionList = versionManager.getVersion(projectId);

                HashMap<String, String> versionHashMap = new HashMap<>();

                for (Object aVersionReleaseList : versionReleaseList) {

                    Object[] obj = (Object[]) aVersionReleaseList;

                    versionHashMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
                }

                for (Object version : versionList) {

                    measureStatusTbManager.deleteMeasureStatusTb(versionManager.getVersionId(version.toString()), projectTbManager.selectProjectId(productName.toString()), jiraDataMakerUtil.getCurrentDate());

                    if ("true".equals(versionHashMap.get(version.toString()))) {

                        JiraDataMaker issuedataMaker = new JiraDataMaker(version.toString(), productName.toString());

                        issuedataMaker.dataMaker("IssueMeasureStatus");

                        issuedataMaker.dayIssueMeasureStatusMaker();
                    }
                }

                versionList.clear();
            }
        } catch (IllegalStateException | SQLException | ClassNotFoundException | IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            logger.info("Finished Make Issue Measure Status Data!!!");
        }
    }
}
