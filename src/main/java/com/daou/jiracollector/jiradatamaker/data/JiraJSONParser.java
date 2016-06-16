package com.daou.jiracollector.jiradatamaker.data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 이 클래스는 Jira Rest Api를 통하여 가져온 JSON 데이터를 Parsing 하는 class 입니다.
 *
 * @author 한승완
 * @since JDK1.8
 */
public class JiraJSONParser {

    /**
     * Jira Rest Api로 가져온 JSON 데이터를 저장 하는 변수.
     */
    private String jiraData = "";

    /**
     * jsonParser 생성 변수.
     */
    private JSONParser jsonParser = new JSONParser();

    /**
     * JSONArray에서 Array 위치를 지정 하는 변수
     */
    private int issuesPoint = 0;

    /**
     * Jira Rest Api로 가져온 JSON 데이터를 set 하는 생성자.
     * @param jiraData jira 에서 수집한 data
     */
    public JiraJSONParser(String jiraData) {
        this.jiraData = jiraData;
    }

    /**
     * 검색된 전체 이슈 갯수를 찾는 메서드
     * @return 검색된 전체 이슈 갯수
     * @throws ParseException
     */
    public String getTotal() throws ParseException {

        JSONObject jsonObject = (JSONObject) this.jsonParser.parse(this.jiraData);

        return jsonObject.get("total").toString();
    }

    /**
     * version 정보를 가진 JSONArray parsing 메서드
     * @return version 정보를 가진 JSONArray
     * @throws ParseException
     */
    public JSONArray getVersions() throws ParseException {

        return (JSONArray) this.jsonParser.parse(this.jiraData);
    }

    /**
     * version 정보를 가진 JSONArray 에서 version 이름 parsing 메서드
     * @param i JSONArray에서를 탐색 하는 변수
     * @return version 이름
     * @throws ParseException
     */
    public String getVersionName(int i) throws ParseException {

        JSONObject jsonObject = (JSONObject) getVersions().get(i);

        return (String) jsonObject.get("name");
    }

    /**
     * issuetype list 를 parsing 하는 method
     * @return
     */
    public JSONArray getStatusList() throws ParseException {

        return (JSONArray) this.jsonParser.parse(this.jiraData);
    }

    /**
     * issuetype list 에서 issuetype 을 parsing 하는 method
     * @param i
     */
    public String getStatusName(int i) throws ParseException {

        JSONObject jsonObject = (JSONObject) getStatusList().get(i);

        return jsonObject.get("name").toString();
    }
}
