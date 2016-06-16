package com.daou.jiracollector.webterminal.terminal.version;

import com.daou.jiracollector.dao.manager.ProjectManager;
import com.daou.jiracollector.dao.manager.VersionManager;
import com.daou.jiracollector.webterminal.WebInterFace;
import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;
import com.daou.jiracollector.webterminal.request.Data;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateVersionReleaseStatus implements CommandAction, WebInterFace{

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        logger.info("Call UpdateVersionReleaseStatus...");

        sendResponse(response, setResponse(getRequset(request)));
    }

    @Override
    public ArrayList<String> getRequset(HttpServletRequest request) throws IOException {

        Data data = new Data();

        return  data.getRequestVersionNameReleaseStatusProjectName(request);
    }

    @Override
    public String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException {

        String versionName = requestList.get(0);
        int releaseStatus;
        String projectName = requestList.get(2);

        if("true".equals(requestList.get(1))){
            releaseStatus = 1;
        } else {
            releaseStatus = 0;
        }

        VersionManager versionManager = new VersionManager();
        ProjectManager projectManager = new ProjectManager();

        boolean result = versionManager.updateRelease(projectName, versionName, releaseStatus);
        boolean resultUpdateProject = projectManager.updateProjectReleaseCount(projectName);

        if(result) {
            if(resultUpdateProject) {
                return "TRUE";
            }
            return "FALSE";
        }
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
