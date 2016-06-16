package com.daou.jiracollector.webterminal.terminal.graph;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daou.jiracollector.dao.manager.AdminManager;
import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;

import com.daou.jiracollector.webterminal.json.graph.IssueMeasure2JSON;
import com.daou.jiracollector.webterminal.request.Data;
import com.daou.jiracollector.webterminal.WebInterFace;
import org.apache.log4j.Logger;

/**
 * 이 클래스는 이슈 조치율을 웹으로 response하는 class이다.
 *
 * @author 한승완
 * @version 1.0.0 2016-03-02
 * @since JDK1.8
 */

public class GetIssueMeasure implements CommandAction, WebInterFace {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // TODO Auto-generated method stub

        logger.info("CALL etIssueMeasure...");

        sendResponse(response, setResponse(getRequset(request)));
    }

    @Override
    public ArrayList<String> getRequset(HttpServletRequest request) throws IOException {

        Data data = new Data();

        return data.getRequestProjectVersion(request);
    }

    @Override
    public String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException {

        String version = requestList.get(0);
        String project = requestList.get(1);
        String startDate = "";
        String endDate = "";

        AdminManager adminManager = new AdminManager();

        List list = adminManager.getStartEndDate(version, project);

        for (Object aList : list) {
            Object[] obj = (Object[]) aList;
            startDate = String.valueOf(obj[0]);
            endDate = String.valueOf(obj[1]);
        }

        logger.info("recive project : " + project + " recive version : " + version + " recive startDate : "
                + startDate + " recive endDate : " + endDate);

        IssueMeasure2JSON graphData = new IssueMeasure2JSON(version, project, startDate);

        return graphData.getJiraData2JSON();
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
