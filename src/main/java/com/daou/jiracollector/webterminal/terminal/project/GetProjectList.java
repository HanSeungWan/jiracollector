package com.daou.jiracollector.webterminal.terminal.project;

import com.daou.jiracollector.webterminal.json.project.ProjectList2JSON;
import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;
import com.daou.jiracollector.webterminal.WebInterFace;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetProjectList implements CommandAction, WebInterFace {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // TODO Auto-generated method stub

        logger.info("CALL GetProjectList...");

        sendResponse(response, setResponse());
    }

    @Override
    public ArrayList<String> getRequset(HttpServletRequest request) throws IOException {
        return null;
    }

    @Override
    public String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public String setResponse() throws SQLException, IOException, ClassNotFoundException {

        ProjectList2JSON projectList2JSON = new ProjectList2JSON();

        return projectList2JSON.getJiraData2JSON();
    }

    @Override
    public void sendResponse(HttpServletResponse response, String json) {

        WebInterFaceUtil util = new WebInterFaceUtil();

        util.jsonMapper(response, json);

        logger.info(json);
    }
}
