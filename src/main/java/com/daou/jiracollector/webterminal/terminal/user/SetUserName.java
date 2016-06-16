package com.daou.jiracollector.webterminal.terminal.user;

import com.daou.jiracollector.dao.manager.tbmanager.UserTbManager;
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

public class SetUserName implements CommandAction, WebInterFace{

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {

        logger.info("CALL SetUserName...");

        sendResponse(response, setResponse(getRequset(request)));
    }

    @Override
    public ArrayList<String> getRequset(HttpServletRequest request) throws IOException {

        Data data = new Data();

        return data.getRequestUserName(request);
    }

    @Override
    public String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException {

        String userName = requestList.get(0);

        UserTbManager userTbManager = new UserTbManager();
        userTbManager.insertUser(userName);

        return "TRUE";
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
