package com.daou.jiracollector.webterminal.terminal.version;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//웹, 자바 연동 패키지
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//java에서 생성한 JSON 데이터를 웹으로 전달 하기 위한 패키지
import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;

//.do 파일 연결
import com.daou.jiracollector.webterminal.json.version.Version2JSON;
import com.daou.jiracollector.webterminal.request.Data;
import com.daou.jiracollector.webterminal.WebInterFace;
import org.apache.log4j.Logger;

/**
 * 이 클래스는 웹에서 버전을 요청시 product를 request하여 version 리스트를 response 하는 클래스이다.
 *
 * @author 한승완
 * @version 1.0.0 2016-02-25
 * @since JDK1.8
 */
public class GetVersion implements CommandAction, WebInterFace {

    private Logger logger = Logger.getLogger(getClass());

    @Override
    public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        // TODO Auto-generated method stub

        logger.info("CALL GetVersion...");

        sendResponse(response, setResponse(getRequset(request)));
    }

    @Override
    public ArrayList<String> getRequset(HttpServletRequest request) throws IOException {

        Data data = new Data();

        return data.getReqeustProject(request);

    }

    @Override
    public String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException {

        String product = requestList.get(0);

        Version2JSON v2j = new Version2JSON(product);

        return v2j.getJiraData2JSON();
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
