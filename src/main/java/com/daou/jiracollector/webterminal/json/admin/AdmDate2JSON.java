package com.daou.jiracollector.webterminal.json.admin;

import com.daou.jiracollector.dao.manager.AdminManager;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 이 클래스는 관리자가 설정한 시작날짜와 끝날짜를 JSON으로 만들어 주는 CLASS 이다.
 *
 * @author 한승완
 * @since JDK1.8
 */
public class AdmDate2JSON {

    private String project = "";
    private String version = "";

    public AdmDate2JSON(String version, String project) {
        this.version = version;
        this.project = project;
    }

    /**
     * 관리자가 지정한 시작 날짜와 끝 날짜를 JOSN으로 만들어 주는 메서드
     *
     * @return JSON 시작날짜, 끝날짜
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String getJiraData2JSON() throws IOException, ClassNotFoundException, SQLException {

        String json;
        String startDate = "";
        String endDate = "";

        AdminManager adminManager = new AdminManager();

        List list = adminManager.getStartEndDate(this.version, this.project);

        for (Object aList : list) {
            Object[] obj = (Object[]) aList;
            startDate = String.valueOf(obj[0]);
            endDate = String.valueOf(obj[1]);
        }

        JSONObject obj = new JSONObject();

        obj.put("startDate", startDate);
        obj.put("endDate", endDate);
        json = obj.toString();

        return json;
    }

}
