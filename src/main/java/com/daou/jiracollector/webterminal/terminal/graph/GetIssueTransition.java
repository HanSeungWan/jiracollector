package com.daou.jiracollector.webterminal.terminal.graph;

// java class 패키지

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// 웹, 자바 연동 패키지
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// java에서 생성한 JSON 데이터를 웹으로 전달 하기 위한 패키지
import com.daou.jiracollector.dao.manager.AdminManager;
import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;

// .do 파일 연결
import com.daou.jiracollector.webterminal.json.graph.IssueTransition2JSON;
import com.daou.jiracollector.webterminal.request.Data;
import com.daou.jiracollector.webterminal.WebInterFace;
import org.apache.log4j.Logger;

/**
 * 웹에서 초기 날짜 설정시 전체 데이터를 JSON으로 웹에 전달하고, 초기 날짜 설정시에는 기간에 맞는 데이터를 웹어 전손 하는 클래스
 *
 * @author 한승완
 * @version 1.0.0 2016-02-25
 * @since JDK1.8
 */
public class GetIssueTransition implements CommandAction, WebInterFace {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // TODO Auto-generated method stub

        logger.info("CALL GetIssueTransition...");

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

        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            Object[] obj = (Object[]) itr.next();
            startDate =  String.valueOf(obj[0]);
            endDate = String.valueOf(obj[1]);
        }

        logger.info("recive project : " + project + " recive version : " + version + " recive startDate : "
                + startDate + " recive endDate : " + endDate);

        IssueTransition2JSON graphData = new IssueTransition2JSON(version, project, startDate);

        String json = graphData.getJiraData2JSON();

        return json;

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
