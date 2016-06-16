package com.daou.jiracollector.webterminal.json.admin;

import com.daou.jiracollector.dao.manager.AdminManager;
import com.daou.jiracollector.dao.manager.ProjectManager;
import com.daou.jiracollector.dao.manager.tbmanager.UserTbManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by intern on 2016-04-04.
 */
public class Indicator2JSON {

    List indicatorList = new ArrayList<String>();

    public String getJiraData2JSON() throws IOException, ClassNotFoundException, SQLException {

        AdminManager adminManager = new AdminManager();

        JSONArray objArray = new JSONArray();

        indicatorList = adminManager.getIndicator();

        for (Object indicatorInfo : indicatorList) {

            Object[] indicatorInfoArray = (Object[]) indicatorInfo;

            objArray = getResultJSON(indicatorInfoArray, objArray);
        }

        return objArray.toString();
    }

    private JSONArray getResultJSON(Object[] indicatorInfoArray, JSONArray objArray) throws ClassNotFoundException, SQLException {

        JSONObject obj = new JSONObject();

        obj.put("jiraProjectName", indicatorInfoArray[0].toString());
        obj.put("graphtype", indicatorInfoArray[1].toString());
        obj.put("targetNo", indicatorInfoArray[2].toString());
        obj.put("targetChar", (indicatorInfoArray[3].toString()));
        obj.put("explanation", indicatorInfoArray[4].toString());

        objArray.add(obj);

        return objArray;
    }
}
