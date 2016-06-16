package com.daou.jiracollector.jiradatamaker.data;

import com.daou.jiracollector.dao.manager.tbmanager.ProjectTbManager;
import com.daou.jiracollector.jiradatamaker.PropertiesController;
import com.daou.jiracollector.jiradatamaker.jiraDataMakerUtil;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * 이 클래스는 Jira Rest Api URL을 생성하는 class 입니다.
 *
 * @author 한승완
 * @since JDK1.8
 */
public class JiraRestApiURLMaker {


    private jiraDataMakerUtil jiraDataMakerUtil = new jiraDataMakerUtil();
    /**
     * jiraccllector properties를 제어 하는 class 생성
     */
    private PropertiesController procon = new PropertiesController();

    /**
     * Jira Rest Api 인증을 위한 id, pw를 set
     */
    private String id = procon.getProperty("id");
    private String pw = procon.getProperty("pw");

    /**
     * Jira Rest Api 호출시 한번에 불러올 JSON 데이터 max 값 설정
     * 최대 : 1000
     */
    private String maxResults = procon.getProperty("maxResults");

    /**
     * project, version set 변수
     */
    private String project = "";
    private String version = "";

    public JiraRestApiURLMaker(String version, String project) {
        this.version = version;
        this.project = project;
    }

    /**
     * 이슈 추이 Jira Rest Api Url 생성
     *
     * @return 이슈 추이 Jira Rest Api Url
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String getIssueTypeTransitionURL(String issueType) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {

        ProjectTbManager projectTbManager = new ProjectTbManager();

        issueType = jiraDataMakerUtil.filterSpecialChar(issueType);

        issueType = jiraDataMakerUtil.encodeUTF8(issueType);

        String url = "https://issue.daou.co.kr/rest/api/2/search?jql=project=" + projectTbManager.selectJiraProjectName(this.project)
                + "%20and%20" + "fixVersion=" + "'" + this.version + "'" + "%20and%20" + "issuetype=" + "'" + issueType + "'" + "&os_username=" + this.id + "&os_password="
                + this.pw;

        return url;
    }

    /**
     * 이슈 조치율 Jira Rest Api Url 생성
     *
     * @return 이슈 조치율 Jira Rest Api Url
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String getIssueMeasureStatusURL(String status) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {

        ProjectTbManager projectTbManager = new ProjectTbManager();

        status = jiraDataMakerUtil.encodeUTF8(status);

        String url = "https://issue.daou.co.kr/rest/api/2/search?jql=project=" + projectTbManager.selectJiraProjectName(this.project)
                + "%20and%20" + "fixVersion=" + "'" + this.version + "'" + "%20and%20" + "status=" + "'" + status + "'" + "&os_username=" + this.id + "&os_password="
                + this.pw;

        return url;
    }

    /**
     * Version Jira Rest Api 요청 URL 생성
     *
     * @return Version Jira Rest Api 요청 URL
     */
    public String getVersionURL() {

        String url = "https://issue.daou.co.kr/rest/api/2/project/" + this.project + "/versions?" + "os_username=" + this.id
                + "&os_password=" + this.pw + "&maxResults=" + this.maxResults;

        return url;
    }

    /**
     * Issuetype Jira Rest Api 요청 URL 생성
     *
     * @return
     */
    public String getIssuetypeURL() {

        String url = "https://issue.daou.co.kr/rest/api/2/project/"+this.project+"/statuses?os_username="+this.id+"&os_password="+this.pw;

        return url;
    }

    public String gettest() {

        String url = "https://issue.daou.co.kr/rest/api/2/issuetype?os_username=quality-intern2&os_password=quality-intern2";

        return url;
    }
}
