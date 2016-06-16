package com.daou.jiracollector.jiradatamaker.scheduler;

import java.io.IOException;
import java.sql.SQLException;

import com.daou.jiracollector.dao.manager.tbmanager.ProjectTbManager;
import com.daou.jiracollector.jiradatamaker.data.JiraDataMaker;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobMakeVersions implements Job {

    private Logger logger = Logger.getLogger(getClass());

    /**
     * job 실행
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        // TODO Auto-generated method stub

        try {
            makedayVersions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makedayVersions()
            throws Exception {

        logger.info("Start Make Version Data...");

        ProjectTbManager projectTbManager = new ProjectTbManager();

        try {

            for (Object productName : projectTbManager.selectJiraProjectName()) {

                JiraDataMaker dataMaker = new JiraDataMaker(productName.toString());

                dataMaker.dataMaker("Versions");

                dataMaker.versionMaker();
            }

        } catch (
                IllegalStateException | SQLException | ClassNotFoundException | IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            logger.info("Finished Make Version Data!!!");
        }
    }
}
