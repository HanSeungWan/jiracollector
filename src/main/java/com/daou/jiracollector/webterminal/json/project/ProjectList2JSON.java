package com.daou.jiracollector.webterminal.json.project;

import com.daou.jiracollector.dao.manager.*;
import com.daou.jiracollector.dao.manager.tbmanager.UserTbManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProjectList2JSON {

    List projectInfo = new ArrayList<String>();

    public String getJiraData2JSON() throws IOException, ClassNotFoundException, SQLException {

        ProjectManager projectManager = new ProjectManager();

        JSONArray objArray = new JSONArray();

        projectInfo = projectManager.getAllProjectList();

        for (Object aProjectInfo : projectInfo) {

            Object[] projectInfoArray = (Object[]) aProjectInfo;

            objArray = getResultJSON(projectInfoArray, objArray);
        }

        return objArray.toString();
    }

    private JSONArray getResultJSON(Object[] projectInfoArray, JSONArray objArray) throws ClassNotFoundException, SQLException {

        UserTbManager userTbManager = new UserTbManager();

        JSONObject obj = new JSONObject();

        obj.put("project", projectInfoArray[0].toString());
        obj.put("jiraProject", projectInfoArray[1].toString());
        obj.put("releaseNo", projectInfoArray[2].toString());
        obj.put("managerName", userTbManager.selectUserName(Integer.parseInt(projectInfoArray[3].toString())));
        obj.put("active", projectInfoArray[4].toString());
        obj.put("projectId",  projectInfoArray[5].toString());

        objArray.add(obj);

        return objArray;
    }
}
