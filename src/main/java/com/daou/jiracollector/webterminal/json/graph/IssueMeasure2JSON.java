package com.daou.jiracollector.webterminal.json.graph;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

// JSON data를 만들기 위한 패키지.
import com.daou.jiracollector.dao.manager.*;
import com.daou.jiracollector.dao.manager.tbmanager.MeasureStatussettingTbManager;
import com.daou.jiracollector.dao.manager.tbmanager.ProjectTbManager;
import com.daou.jiracollector.jiradatamaker.jiraDataMakerUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * 이 클래스는 이슈 조치율을 JSON으로 제공해 주는 CLASS 입니다.
 * 위한 기능을 사용 하기위해서는 getJiraData2JSON() 메소드를 사용하여 수행하면 됩니다.
 *
 * @author 한승완
 * @since JDK1.8
 */
public class IssueMeasure2JSON {


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
     * request로 전달 받은 시작날짜 정보를 저장 하는 변수
     */
    private String startDate = null;

    /**
     * request로 전달 받은 끝 날짜 정보를 저장 하는 변수
     */
    private String endDate = null;

    /**
     * 날짜에 발새한 이슈를 저장하는 변수 이다. 최종 적으로 전달 되는 변수 이다.
     */
    HashMap<String, Integer> jsonResult = new HashMap<>();

    private Logger logger = Logger.getLogger(getClass());

    /**
     * 시작일과 끝날짜 지정시 생성자.
     *
     * @param version version
     * @param project project
     * @param startDate 시작 날짜
     */
    public IssueMeasure2JSON(String version, String project, String startDate) {
        this.version = version;
        this.project = project;
        this.startDate = startDate;
        this.endDate = jiraDataMakerUtil.getCurrentDate();
    }


    /**
     * 이슈 조치율을 검색하여 JSON으로 return해 주는 메서드
     *
     * @return 이슈 조치율 JSON
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public String getJiraData2JSON() throws ClassNotFoundException, SQLException {

        logger.info("CALL IssueMeasure2JSON...");

        String json;

        JSONArray objArray = new JSONArray();

        while (!this.startDate.equals(this.endDate)) {

            objArray = getResultJSON(startDate, objArray);

            startDate = jiraDataMakerUtil.increaseDate(startDate);
        }

        objArray = getResultJSON(startDate, objArray);

        startDate = jiraDataMakerUtil.increaseDate(startDate);

        assert objArray != null;
        json = objArray.toString();

        return json;
    }

    /**
     * 검색 조건에 맞는 이슈 조치율을 JSON 으로 만들어 return해 주는 메서드
     *
     * @param date     검색 날짜
     * @param objArray jsonArray 구성시 추가하기 위한 재귀 파라미터
     * @return 이슈 조치율 jsonArray
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private JSONArray getResultJSON(String date, JSONArray objArray) throws ClassNotFoundException, SQLException {

        ProjectTbManager projectTbManager = new ProjectTbManager();
        GraphManager graphManager = new GraphManager();
        MeasureStatussettingTbManager measureStatussettingTbManager = new MeasureStatussettingTbManager();

        ArrayList<String> okMeasureStatusList = (ArrayList<String>) measureStatussettingTbManager.selectProjectStatus(projectTbManager.selectProjectId(this.project), "조치");
        ArrayList<String> noMeasureStatusList = (ArrayList<String>) measureStatussettingTbManager.selectProjectStatus(projectTbManager.selectProjectId(this.project), "미조치");

        jsonResult = graphManager.getIssueMeasureStatus(this.version, this.project, date);

        if (!jsonResult.isEmpty()) {
            JSONObject obj = new JSONObject();

            JSONObject subObjMea = new JSONObject();
            JSONObject subObjBefMea = new JSONObject();

            int total = 0;

            for (String statusName : okMeasureStatusList) {
                subObjMea.put(statusName.replace(" ", ""), jsonResult.get(statusName));
                total += jsonResult.get(statusName);
            }

            subObjMea.put("TOTAL", total);

            obj.put("조치", subObjMea);

            total = 0;

            for (String statusName : noMeasureStatusList) {
                subObjBefMea.put(statusName.replace(" ", ""), jsonResult.get(statusName));
                total += jsonResult.get(statusName);
            }

            subObjBefMea.put("TOTAL", total);

            obj.put("미조치", subObjBefMea);

            obj.put("날짜", date);

            objArray.add(obj);

            return objArray;
        }

        return null;
    }
}
