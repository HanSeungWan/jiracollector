package com.daou.jiracollector.webterminal.json.graph;

import java.util.ArrayList;
import java.util.HashMap;

// JSON data를 만들기 위한 패키지.
import com.daou.jiracollector.dao.manager.*;
import com.daou.jiracollector.dao.manager.tbmanager.IssuetypeTbManager;
import com.daou.jiracollector.jiradatamaker.jiraDataMakerUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * 이 클래스는 이슈 현황을 JSON으로 제공해 주는 CLASS 입니다.
 * 위한 기능을 사용 하기위해서는 getJiraData2JSON() 메소드를 사용하여 수행하면 됩니다.
 *
 * @author 한승완
 * @version 1.0.0 2016-03-02
 * @since JDK1.8
 */

public class IssueTransition2JSON {

    private Logger logger = Logger.getLogger(getClass());

    /**
     * 마지막 날짜 저장
     */
    private String endDate = null;

    /**
     * 시작 날짜 저장
     */
    private String startDate = null;

    private jiraDataMakerUtil jiraDataMakerUtil = new jiraDataMakerUtil();

    /**
     * request로 전달 받은 version 정보를 저장 하는 변수
     */
    private String version = null;

    /**
     * request로 전달 받은 project 정보를 저장 하는 변수
     */
    private String project = null;

    /**
     * 이슈 현황 data를 저장 하는 변수
     */
    HashMap<String, Integer> jsonResult = new HashMap<>();

    /**
     * version, project, 시작, 끝날짜 set 생성자.
     *
     * @param version version
     * @param project project
     * @param startDate 시작 날짜
     */
    public IssueTransition2JSON(String version, String project, String startDate) {
        this.version = version;
        this.project = project;
        this.startDate = startDate;
        this.endDate = jiraDataMakerUtil.getCurrentDate();
    }

    /**
     * 이슈현황 검색하여 JSON으로 return해 주는 메서드
     *
     * @return 이슈 현황 JSON
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public String getJiraData2JSON() throws ClassNotFoundException {

        logger.info("CALL IssueTransition2JSON...");

        JSONArray objArray = new JSONArray();

        while (!this.startDate.equals(this.endDate)) {

            objArray = getResultJSON(startDate, objArray);

            startDate = jiraDataMakerUtil.increaseDate(startDate);
        }

        objArray = getResultJSON(startDate, objArray);

        startDate = jiraDataMakerUtil.increaseDate(startDate);

        assert objArray != null;
        return objArray.toString();
    }

    /**
     * 검색 조건에 맞는 이슈 현황을 JSON 으로 만들어 return해 주는 메서드
     *
     * @param date     검색 날짜
     * @param objArray jsonArray 구성시 추가하기 위한 재귀 파라미터
     * @return 이슈 현황 jsonObject
     * @throws ClassNotFoundException
     *
     */
    @SuppressWarnings("unchecked")
    private JSONArray getResultJSON(String date, JSONArray objArray) throws ClassNotFoundException {

        IssuetypeTbManager issuetypeTbManager = new IssuetypeTbManager();
        GraphManager graphManager = new GraphManager();

        ArrayList<String> issuetypeList = (ArrayList<String>) issuetypeTbManager.getAllIssuetypeList(this.project);

        jsonResult = graphManager.getIssuetypeTransition(this.version, this.project, date);

        if (!jsonResult.isEmpty()) {
            JSONObject obj = new JSONObject();

            int total = 0;

            obj.put("날짜", date);

            for (String issuetype : issuetypeList) {
                obj.put(jiraDataMakerUtil.issuetypeFormatChange(issuetype.replace(" ", "")), jsonResult.get(issuetype));
                total += jsonResult.get(issuetype);
            }

            obj.put("전체", total);

            objArray.add(obj);

            return objArray;
        }

        return null;
    }
}
