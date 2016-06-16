package com.daou.jiracollector.webterminal.terminal.admin;

import com.daou.jiracollector.dao.manager.AdminManager;
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

public class UpdateIndicator implements CommandAction, WebInterFace {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        logger.info("Call UpdateIndicator...");

        sendResponse(response, setResponse(getRequset(request)));
    }

    @Override
    public ArrayList<String> getRequset(HttpServletRequest request) throws IOException {

        Data data = new Data();
        return data.getRequestIndicatorData(request);
    }

    @Override
    public String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException {

        int proejctId = Integer.parseInt(requestList.get(0));
        String graphtype = requestList.get(1);
        int targetNo = Integer.parseInt(requestList.get(2));
        String targetChar = requestList.get(3);
        String explanation = requestList.get(4);

        AdminManager adminManager = new AdminManager();
        boolean result = adminManager.updateIndicator(proejctId, graphtype, targetNo, targetChar, explanation);

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
