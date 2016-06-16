package com.daou.jiracollector.webterminal.json.admin;

import java.sql.SQLException;

import com.daou.jiracollector.dao.manager.AdminManager;
import org.json.simple.JSONObject;

/**
 * 이 클래스는 관리자가 시작날짜와 끝 날짜를 지정하였는지 여부를 확인하여 JOSN 으로 만들어 주는 CLASS 입니다.
 *
 * @author 한승완
 * @since JDK1.8
 */
public class CheckAdmDate2JSON {
	
	private String version = null;
	
	private String project = null;
	
	public CheckAdmDate2JSON(String version, String project){
		
		this.version = version;
		this.project = project;
	}
	
	/**
	 * 설정 데이터가 존재 하는지 검사 한 뒤 JSON 형태로 존재 유무를 전달 한다.
	 * 존재 : OK
	 * 미존재 : NULL
	 * @return OK, NULL text
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public String checkHasDateData() throws SQLException, ClassNotFoundException{
		
		JSONObject obj = new JSONObject();
		String json;

		AdminManager adminManager = new AdminManager();

		if(adminManager.checkHasdate(this.version, this.project)){

			obj.put("check", "OK");

			json = obj.toJSONString();

			return json;

		} else {

			obj.put("check", "NULL");

			json = obj.toJSONString();

			return json;
		}
	}
}
