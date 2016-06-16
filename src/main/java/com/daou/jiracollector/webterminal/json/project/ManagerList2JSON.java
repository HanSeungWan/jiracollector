package com.daou.jiracollector.webterminal.json.project;

import com.daou.jiracollector.dao.manager.ProjectManager;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerList2JSON {

    List managerList = new ArrayList<String>();

    public String getJiraData2JSON() throws IOException, ClassNotFoundException, SQLException {

        ProjectManager projectManager = new ProjectManager();

        JSONArray objArray = new JSONArray();

        managerList = projectManager.getAllManagerName();

        for(Object manager : managerList){
            objArray.add(manager.toString());
        }

        managerList.clear();

        return objArray.toString();
    }
}
