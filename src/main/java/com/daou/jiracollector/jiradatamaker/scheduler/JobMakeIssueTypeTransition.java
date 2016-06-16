package com.daou.jiracollector.jiradatamaker.scheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.daou.jiracollector.dao.manager.VersionManager;
import com.daou.jiracollector.dao.manager.tbmanager.ProjectTbManager;
import com.daou.jiracollector.jiradatamaker.data.JiraDataMaker;
import org.apache.http.ParseException;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobMakeIssueTypeTransition implements Job {

    private Logger logger = Logger.getLogger(getClass());

    /**
     * job 실행
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub

        try {
            makedayIssuetypeTransition();
        } catch (NumberFormatException | ParseException | org.json.simple.parser.ParseException | SQLException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void makedayIssuetypeTransition() throws NumberFormatException, org.apache.http.ParseException,
            org.json.simple.parser.ParseException, ClassNotFoundException, SQLException {

        logger.info("Start Make Issue Type Transition Data...");

        ProjectTbManager projectTbManager = new ProjectTbManager();
        VersionManager versionManager = new VersionManager();

        try {
            for (Object productName : projectTbManager.selectJiraProjectName()) {

                int projectId  = projectTbManager.selectProjectId(productName.toString());

                List versionList = versionManager.getVersion(projectTbManager.selectProjectId(productName.toString()));
                List versionList2 = versionManager.getVersionNameRelease(projectId);

                HashMap<String, String> versionHashMap = new HashMap<>();

                for (Object aVersionList2 : versionList2) {
                    Object[] obj = (Object[]) aVersionList2;

                    versionHashMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
                }

                for (Object version : versionList) {

                    if ("true".equals(versionHashMap.get(version))) {

                        JiraDataMaker issuedataMaker = new JiraDataMaker(version.toString(), productName.toString());

                        issuedataMaker.dataMaker("IssueTypeTransition");

                        issuedataMaker.dayIssuetypeTransitionMaker();
                    }
                }

                versionList.clear();
            }
        } catch (IllegalStateException | SQLException | ClassNotFoundException | IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            logger.info("Finished Make Issue Type Transition Data!!!");
        }
    }
}
