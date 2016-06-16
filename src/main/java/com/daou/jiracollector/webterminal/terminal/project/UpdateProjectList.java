package com.daou.jiracollector.webterminal.terminal.project;

import com.daou.jiracollector.dao.manager.ProjectManager;
import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;
import com.daou.jiracollector.webterminal.request.Data;
import com.daou.jiracollector.webterminal.WebInterFace;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateProjectList implements CommandAction, WebInterFace {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        logger.info("CALL UpdateProjectList...");

        sendResponse(response, setResponse(getRequset(request)));
    }

    @Override
    public ArrayList<String> getRequset(HttpServletRequest request) throws IOException {

        Data data = new Data();

        return data.getRequestProjectInfo(request);
    }

    @Override
    public String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException {

        String project = requestList.get(0);
        String jiraProject = requestList.get(1);
        String userName = requestList.get(2);
        String projectId = requestList.get(3);

        ProjectManager projectManager = new ProjectManager();

        boolean result = projectManager.updateProjectList(project, jiraProject, userName, Integer.parseInt(projectId));

        if(result) return "TRUE";
        else return "FALSE";
    }

    @Override
    public String setResponse() throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public void sendResponse(HttpServletResponse response, String json) {

        WebInterFaceUtil util = new WebInterFaceUtil();

        util.jsonMapper(response, json);

        logger.info(json);

    }
}
