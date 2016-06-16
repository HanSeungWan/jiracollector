package com.daou.jiracollector.webterminal.terminal.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;

import com.daou.jiracollector.webterminal.json.admin.AdmDate2JSON;
import com.daou.jiracollector.webterminal.request.Data;
import com.daou.jiracollector.webterminal.WebInterFace;
import org.apache.log4j.Logger;

/**
 * 날짜 기간이 설정 되어 있으면 설정 파일을 읽어서 startDate, endDate를 웹으로 전달
 *
 * @author 한승완
 * @version 1.0.0 2016-02-25
 * @since JDK1.8
 */
public class GetAdmDate implements CommandAction, WebInterFace {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // TODO Auto-generated method stub

        logger.info("CALL GetAdmDate...");

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

        AdmDate2JSON jsonMaker = new AdmDate2JSON(version, product);

        return jsonMaker.getJiraData2JSON();
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
