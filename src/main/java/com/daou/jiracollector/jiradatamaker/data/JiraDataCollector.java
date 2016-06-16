package com.daou.jiracollector.jiradatamaker.data;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * 이 클래스는 Jira Rest Api URL을 생성, 호출하여 Jira JSON Data를 수집 하는 CLASS입니다.
 *
 * @author 한승완
 * @since JDK1.8
 */
public class JiraDataCollector {

    private Logger logger = Logger.getLogger(getClass());
    /**
     * 어떤 작업을 수행 할지 정하는 변 수
     * 1. IssueTypeTransition ; 이슈 현황
     * 2. IssueMeasureStatus : 이슈 추이
     * 3. Versions : version 리스트
     */
    private String function = "";

    /**
     * project, version set 변수
     */
    private String project = "";
    private String version = "";

    /**
     * version, project, function set 생서자
     *
     * @param function 수집할 기능
     * @param version 버전
     * @param project 프로젝트
     */
    public JiraDataCollector(String version, String project, String function) {

        this.version = version;
        this.project = project;
        this.function = function;
    }

    /**
     * function 따라 rest api url 을 생성하여 JIRA 이슈 데이터를 JSON 형태로 가져오는 메서드
     * --- function list ---
     * IssueTypeTransition = 이슈추이 데이터
     * IssueMeasureStatus = 이슈 조치율 데이터
     * Versions = version 데이터
     * Status = status 데이터
     * --- function list ---
     *
     * @return JIRA 이슈 JSON data
     */
    private HttpResponse getHttpResponse(String function)
            throws IOException, ClassNotFoundException, SQLException, InterruptedException {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        JiraRestApiURLMaker getURL = new JiraRestApiURLMaker(this.version, this.project);

        HttpGet getRequest = null;

        if ("IssueTypeTransition".equals(this.function)) {
            logger.info("URL : " + getURL.getIssueTypeTransitionURL(function));
            getRequest = new HttpGet(getURL.getIssueTypeTransitionURL(function));
        } else if ("IssueMeasureStatus".equals(this.function)) {
            logger.info("URL : " + getURL.getIssueMeasureStatusURL(function));
            getRequest = new HttpGet(getURL.getIssueMeasureStatusURL(function));
        } else if ("Versions".equals(this.function)) {
            logger.info("URL : " + getURL.getVersionURL());
            getRequest = new HttpGet(getURL.getVersionURL());
        } else if ("Status".equals(this.function)) {
            logger.info("URL : " + getURL.getIssuetypeURL());
            getRequest = new HttpGet(getURL.getIssuetypeURL());
        }

        assert getRequest != null;
        getRequest.addHeader("accept", "application/json");

        HttpResponse response = null;

        try {
            response = httpclient.execute(getRequest);
        } catch (Exception e) {
            logger.error("ERROR... TRY RECONNECT");
            Thread.sleep(1000);
            response = httpclient.execute(getRequest);
        }

        return response;
    }

    /**
     * JIRA 이슈 JSON Data 를 UTF-8로 인코딩하여 리턴하는 메서드
     *
     * @return UTF-8로 인코딩 된 JSON 데이터
     */
    public String getResultResponse(String pram)
            throws IllegalStateException, IOException, ParseException, ClassNotFoundException, SQLException, InterruptedException {

        String jiraJsonData;

        try {
            jiraJsonData = EntityUtils.toString(getHttpResponse(pram).getEntity(), HTTP.UTF_8);
        } catch (Exception e) {
            logger.error("ERROR... TRY RECONNECT");
            Thread.sleep(1000);
            jiraJsonData = EntityUtils.toString(getHttpResponse(pram).getEntity(), HTTP.UTF_8);
        }

        return jiraJsonData;
    }
}
