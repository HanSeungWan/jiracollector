package com.daou.jiracollector.webterminal.terminal.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;

import com.daou.jiracollector.webterminal.json.admin.CheckAdmDate2JSON;
import com.daou.jiracollector.webterminal.request.Data;
import com.daou.jiracollector.webterminal.WebInterFace;
import org.apache.log4j.Logger;

/**
 * 이 클래스는 Jira 웹에서 날짜가 설정되어 잇는지 판별후 JSON 으로 결괄르 보내는 클래스이다.
 *
 * @author 한승완
 * @version 1.0.0 2016-02-25
 * @since JDK1.8
 */
public class CheckHasAdmDate implements CommandAction, WebInterFace {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // TODO Auto-generated method stub

        logger.info("CALL CheckHasAdmDate...");

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
        String product = requestList.get(1);

        CheckAdmDate2JSON hasDateRangeCheck = new CheckAdmDate2JSON(version, product);

        hasDateRangeCheck.checkHasDateData();

        return hasDateRangeCheck.checkHasDateData();
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
