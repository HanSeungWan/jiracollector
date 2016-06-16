package com.daou.jiracollector.jiradatamaker.data;

import com.daou.jiracollector.dao.manager.GraphManager;
import com.daou.jiracollector.dao.manager.VersionManager;
import com.daou.jiracollector.dao.manager.tbmanager.*;
import com.daou.jiracollector.jiradatamaker.jiraDataMakerUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 이 클래스는 JiraDataCollector가 수집한 JIRA JSON 데이터를 parsing 하여 DB에 저장 하는 CLASS 입니다.
 *
 * @author 한승완
 * @since JDK1.8
 */
public class JiraDataMaker {

    private Logger logger = Logger.getLogger(getClass());

    private jiraDataMakerUtil jiraDataMakerUtil = new jiraDataMakerUtil();

    /**
     * 이슈 타입, 이슈 타입 개수를 저장 하는 hash map
     */
    HashMap<String, Integer> dayIssuetypeTransition = new HashMap<>();
    /**
     * 상태, 상태 타입 개수를 저장 하는 hash map
     */
    HashMap<String, Integer> dayIssueMeasureStatus = new HashMap<>();
    /**
     * version 저장하는 ArrayList
     */
    private ArrayList<String> versionData = new ArrayList<>();
    /**
     * 상태를 저장 하는 ArrayList
     */
    private ArrayList<String> statusData = new ArrayList<>();

    private String jiraProjectName = "";
    private String version = "";
    /**
     * jira rest api 를 통하여 얻는 json 데이터.
     */
    private String jiraData = "";

    public JiraDataMaker(String version, String jiraProjectName) {
        this.jiraProjectName = jiraProjectName;
        this.version = version;
    }

    public JiraDataMaker(String jiraProjectName) {
        this.jiraProjectName = jiraProjectName;
    }

    /**
     * function 에 따라 달라 Data 를 수집 하는 기능이 달라진다.
     * --- function list ---
     * IssueTypeTransition = 이슈추이 데이터
     * IssueMeasureStatus = 이슈 조치율 데이터
     * Versions = version 데이터
     * Status = status 데이터
     * --- function list ---
     *
     * @param function
     */
    public void dataMaker(String function) throws NumberFormatException, ParseException, IOException,
            IllegalStateException, org.apache.http.ParseException, ClassNotFoundException, SQLException, InterruptedException {

        JiraDataCollector jiraDataCollector = new JiraDataCollector(version, jiraProjectName, function);

        if ("IssueTypeTransition".equals(function)) {

            doMakeIssuetypeTransition(jiraDataCollector);

        } else if ("IssueMeasureStatus".equals(function)) {

            doMakeIssueMeasureStatus(jiraDataCollector);

        } else if ("Versions".equals(function)) {

            doMakeVersion(jiraDataCollector);

        } else if ("Status".equals(function)) {

            doIssuetypeMaker(jiraDataCollector);
        }

    }

    /**
     * 이슈 추이 데이터를 수집 하는 method
     *
     * @return
     */
    public boolean dayIssuetypeTransitionMaker()
            throws IOException, ClassNotFoundException, SQLException, InterruptedException {

        GraphManager graphManager = new GraphManager();
        ProjectTbManager projectTbManager = new ProjectTbManager();
        VersionManager versionManager = new VersionManager();
        IssuetypeTbManager issuetypeTbManager = new IssuetypeTbManager();

        for (Object issueType : dayIssuetypeTransition.keySet()) {
            int count = dayIssuetypeTransition.get(issueType.toString());

            if (count != 0) {
                graphManager.setIssuetypeTransition(versionManager.getVersionId(version), projectTbManager.selectProjectId(jiraProjectName), issuetypeTbManager.getIssuetypeId(issueType.toString()), jiraDataMakerUtil.getCurrentDate(), count);
            }
        }

        dayIssuetypeTransition.clear();

        return true;
    }

    /**
     * 이슈 조치율 데이터를 수집 하는 method
     *
     * @return
     */
    public boolean dayIssueMeasureStatusMaker()
            throws IOException, ClassNotFoundException, SQLException, InterruptedException {

        GraphManager graphManager = new GraphManager();
        ProjectTbManager projectTbManager = new ProjectTbManager();
        VersionManager versionManager = new VersionManager();
        MeasureStatussettingTbManager measureStatussettingTbManager = new MeasureStatussettingTbManager();

        for (Object issueStatus : dayIssueMeasureStatus.keySet()) {

            int count = dayIssueMeasureStatus.get(issueStatus.toString());

            if (count != 0) {
                graphManager.setMeasureStatusTb(versionManager.getVersionId(version), projectTbManager.selectProjectId(jiraProjectName), measureStatussettingTbManager.getMeasureStatusId(issueStatus.toString()), jiraDataMakerUtil.getCurrentDate(), count);
            }
        }

        dayIssueMeasureStatus.clear();

        return true;
    }

    /**
     * version 데이터를 수집 하는 method
     *
     * @return
     */
    public boolean versionMaker() throws Exception {

        VersionManager versionManager = new VersionManager();
        ProjectTbManager projectTbManager = new ProjectTbManager();

        int checkCount = 0;

        List oldVersionList = versionManager.getVersion(projectTbManager.selectProjectId(jiraProjectName));

        if (oldVersionList.isEmpty()) {
            for (String version : versionData) {
                versionManager.setVersion(projectTbManager.selectProjectId(jiraProjectName), version, false);
            }
        } else {
            for (String version : versionData) {
                checkCount = 0;
                for (Object oldVersion : oldVersionList) {
                    if (version.equals(oldVersion.toString())) {
                        break;
                    }
                    checkCount++;
                }
                if (checkCount == oldVersionList.size()) {
                    versionManager.setVersion(projectTbManager.selectProjectId(jiraProjectName), version, false);
                }
            }
        }

        versionData.clear();

        return true;
    }

    /**
     * issuetype data 를 만드는 method
     *
     * */
    public boolean issuetypeMaker() throws Exception {

        IssuetypeTbManager issuetypeTbManager = new IssuetypeTbManager();
        ProjectTbManager projectTbManager = new ProjectTbManager();

        int checkCount = 0;

        List oldStatuaList = issuetypeTbManager.getAllIssuetypeList(jiraProjectName);

        if (oldStatuaList.isEmpty()) {
            for (String status : statusData) {
                issuetypeTbManager.setIssuetype(status, projectTbManager.selectProjectId(jiraProjectName));
            }
        } else {
            for (String status : statusData) {
                checkCount = 0;
                for (Object oldStatus : oldStatuaList) {
                    if (status.equals(oldStatus.toString())) {
                        break;
                    }
                    checkCount++;
                }
                if (checkCount == oldStatuaList.size()) {
                    issuetypeTbManager.setIssuetype(status, projectTbManager.selectProjectId(jiraProjectName));
                }
            }
        }

        versionData.clear();


        return true;
    }

    /**
     * 이슈 추이율 수집 로직 method
     * @param jiraDataCollector
     */
    public void doMakeIssuetypeTransition(JiraDataCollector jiraDataCollector) throws ClassNotFoundException, SQLException, InterruptedException, IOException, ParseException {

        IssuetypeTbManager issuetypeTbManager = new IssuetypeTbManager();
        List issuetypeList = issuetypeTbManager.getAllIssuetypeList(this.jiraProjectName);

        for (Object issuetype : issuetypeList) {

            this.jiraData = jiraDataCollector.getResultResponse(issuetype.toString());

            JiraJSONParser jsonParser = new JiraJSONParser(this.jiraData);

            try {
                dayIssuetypeTransition.put(issuetype.toString(), Integer.parseInt(jsonParser.getTotal()));
                dayIssuetypeTransitionMaker();
            } catch (NullPointerException e) {
                logger.info("NO ISSUETYPE : " + issuetype.toString());
            }
        }
    }

    /**
     * 이슈 추이율 수집 로직 method
     * @param jiraDataCollector
     */
    public void doMakeIssueMeasureStatus(JiraDataCollector jiraDataCollector) throws ClassNotFoundException, SQLException, InterruptedException, IOException, ParseException {

        MeasureStatussettingTbManager measureStatussettingTbManager = new MeasureStatussettingTbManager();
        ProjectTbManager projectTbManager = new ProjectTbManager();

        List statusList = measureStatussettingTbManager.selectJiraStatusName(projectTbManager.selectProjectId(jiraProjectName));

        for (Object status : statusList) {

            this.jiraData = jiraDataCollector.getResultResponse(status.toString());

            JiraJSONParser jsonParser = new JiraJSONParser(this.jiraData);

            try {
                dayIssueMeasureStatus.put(status.toString(), Integer.parseInt(jsonParser.getTotal()));
                dayIssueMeasureStatusMaker();
            } catch (NullPointerException e) {
                logger.info("NO STATUS : " + status.toString());
            }
        }
    }

    /**
     * version 데이터 수집 로직 method
     * @param jiraDataCollector
     */
    public void doMakeVersion(JiraDataCollector jiraDataCollector) throws ClassNotFoundException, SQLException, InterruptedException, IOException, ParseException {

        this.jiraData = jiraDataCollector.getResultResponse(null);
        JiraJSONParser jsonParser = new JiraJSONParser(this.jiraData);

        for (int i = 0; i < jsonParser.getVersions().size(); i++) {
            versionData.add(jsonParser.getVersionName(i));
        }
    }

    /**
     * 이슈타입 생성 로직 method
     * @param jiraDataCollector
     */
    public void doIssuetypeMaker(JiraDataCollector jiraDataCollector) throws ClassNotFoundException, SQLException, InterruptedException, IOException, ParseException {

        this.jiraData = jiraDataCollector.getResultResponse(null);
        JiraJSONParser jsonParser = new JiraJSONParser(this.jiraData);

        for (int i = 0; i < jsonParser.getStatusList().size(); i++) {
            statusData.add(jsonParser.getStatusName(i));
        }
    }
}