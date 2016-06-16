package com.daou.jiracollector.webterminal.json.version;

// java class
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.daou.jiracollector.dao.manager.VersionManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 이 클래스는 제품에 해당 하는 version 리스트를 JSON으로 제공해 주는 CLASS 입니다.
 * 위한 기능을 사용 하기위해서는 getJiraData2JSON() 메소드를 사용하여 수행하면 됩니다.
 *
 * @author 한승완
 * @since JDK1.8
 */
public class Version2JSON {

	/**
	 * 웹에서 요청한 제품명을 저장히기 위한 변수이다.
	 */
	private String project = null;

	/**
	 * version data를 저장 하는 변수
	 */
	ArrayList<String> jsonResult = new ArrayList<>();
	
	/**
	 * project set 생성자
	 * @param project  request 받은 제품명
	 */
	public Version2JSON(String project){
	
		this.project = project;
	}
	
	/**
	 * project로 검색하여 version 리스트를 JSONArray Data로 만들어 주는 메서드
	 * 
	 * @return JSON version 리스트
	 * @throws IOException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public String getJiraData2JSON() throws IOException, ClassNotFoundException, SQLException{

		JSONArray objArray = new JSONArray();

		VersionManager versionManager = new VersionManager();

		List<Object> versionList = versionManager.getVersionNameRelease(this.project);

		for (Object aVersionList : versionList) {
			Object[] versionInfoArray = (Object[]) aVersionList;

			objArray = getResultJSON(versionInfoArray, objArray);
		}

		jsonResult.clear();

		return objArray.toString();
	}

	private JSONArray getResultJSON(Object[] projectInfoArray, JSONArray objArray) throws ClassNotFoundException, SQLException {

		JSONObject obj = new JSONObject();

		obj.put("versionName", projectInfoArray[0].toString());
		obj.put("releaseStatus", projectInfoArray[1].toString());

		objArray.add(obj);

		return objArray;
	}
}
