package com.daou.jiracollector.webterminal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface WebInterFace {

    ArrayList<String> getRequset(HttpServletRequest request) throws IOException;

    String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException;

    String setResponse() throws SQLException, IOException, ClassNotFoundException;

    void sendResponse(HttpServletResponse response, String json);
}
