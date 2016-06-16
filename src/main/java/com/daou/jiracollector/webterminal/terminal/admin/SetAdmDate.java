package com.daou.jiracollector.webterminal.terminal.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.daou.jiracollector.dao.manager.AdminManager;
import com.daou.jiracollector.dao.manager.VersionManager;
import com.daou.jiracollector.dao.manager.tbmanager.ProjectTbManager;
import com.daou.jiracollector.webterminal.WebInterFaceUtil;
import com.daou.jiracollector.webterminal.controller.CommandAction;

import com.daou.jiracollector.webterminal.request.Data;
import com.daou.jiracollector.webterminal.WebInterFace;
import org.apache.log4j.Logger;

/**
 * 이 클래스는 Jira 웹에서 product, version, startDate, endDate 파라미터 전송시 startDate,
 * endDate 조건에 맞고 product, version 에 해당하는 이슈 추이 JSON으로 리턴
 *
 * @version 1.0.0 2016-02-25
 * @author 한승완
 * @since JDK1.8
 */
public class SetAdmDate implements CommandAction, WebInterFace {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public void requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// TODO Auto-generated method stub

		logger.info("CALL SetAdmDate...");

		sendResponse(response, setResponse(getRequset(request)));

	}

	@Override
	public ArrayList<String> getRequset(HttpServletRequest request) throws IOException {

		Data data = new Data();

		return data.getRequestProjectVersionDate(request);
	}

	@Override
	public String setResponse(ArrayList<String> requestList) throws SQLException, IOException, ClassNotFoundException {

		String version = requestList.get(0);
		String product = requestList.get(1);
		String startDate = requestList.get(2);
		String endDate = requestList.get(3);

		ProjectTbManager projectTbManager = new ProjectTbManager();
		VersionManager versionManager = new VersionManager();
		AdminManager adminManager = new AdminManager();

		adminManager.setStartEndDate(versionManager.getVersionId(version), projectTbManager.selectProjectId(product), startDate, endDate);

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
