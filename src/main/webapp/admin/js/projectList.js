/**
 * 상품명과 jira에 등록된 상품명의 이름을 매칭시키는 자바스크립트
 * jira에 등록된 상품명의 이름을 추가/삭제/수정의 역할을 합니다.
 */

$(document).ready(function() {
	// 초기 정렬
	var name = 'projectSort';
	
	getAllProjectData(name);

	$(document).on("click","#allCheckbox",function(){
		//전체선택 체크박스가 체크된상태일경우
		if($("#allCheckbox").prop("checked")) {
			$("input[type=checkbox]").prop("checked",true);
		//전체선택 체크박스가 해제된 경우
		} else {
			$("input[type=checkbox]").prop("checked",false);
		}
	});
	/* 정렬
	$(document).on('click', '#projectSort', function() {
		name = 'projectSort';
		getAllProjectData(name);
	});
	$(document).on('click', '#jiraProjectSort', function() {
		name = 'jiraProjectSort';
		getAllProjectData(name);
	});
	$(document).on('click', '#releaseNoSort', function() {
		name = 'releaseNoSort';
		getAllProjectData(name);
	});
	$(document).on('click', '#managerNameSort', function() {
		name = 'managerNameSort';
		getAllProjectData(name);
	});
	*/
	
	//클릭한 프로젝트를 수정하기 위해 session에 text를 임시 저장하고 상세페이지로 넘김.
	$(document).on('click', '#projectName', function() {
	    sessionStorage.setItem("projectName",$(this).text());
	    location.href = "./projectdetail";
	});
	
	$("#btnDelete").click(function(event) {
		var checkboxValues = [];
		
	    $("input[name='projectCheckbox']:checked").each(function(i) {
	    	checkboxValues.push($(this).val());
	    });
		
	    var data = { "checkArray" : checkboxValues };
	    //alert(data);

		$.ajax({
	        url: "../test2.do",
	        type:"POST",
	        data: data,
	        success:function(successfulData){
	        	if(successfulData == "TRUE"){
	        		//alert("삭제되었습니다.");
	        		callAlertMid('', '삭제되었습니다.');
	        		getAllProjectData();
	        	}
	        	else {
	        		//alert("실패했습니다.");
	        		callAlertMid('', '실패했습니다.');
	        	}
	        },
	        error : function(jqXHR, textStatus, errorThrown) {
				alert("에러 발생 " + textStatus + " : " + errorThrown);
			}
		});		
	});
	
	// ajax 실행 및 완료시 'Loading 이미지'작동
	$('#viewLoading').ajaxStart(function() {

		$(this).fadeIn(500);

	}).ajaxStop(function() {
		
		$(this).hide();	

	});
});

/**
 * 전체선택 체크박스가 체크가 된 상태일 경우 해당화면에 전체 체크박스들을 체크해주고,
 * 전체선택 체크박스가 해제된 경우, 해당화면에 모든 체크박스들의 체크를 해제해준다.
 * @param checkboxId 전체체크박스 Id
 */
function CheckboxEvent(checkboxId) {

	//전체선택 체크박스가 체크된상태일경우
	if($(checkboxId).prop("checked")) {

		$("input[type=checkbox]").prop("checked",true);

	//전체선택 체크박스가 해제된 경우
	} else {

		$("input[type=checkbox]").prop("checked",false);

	}
}

/**
 * 프로젝트를 가져와서 테이블 작성하는 함수
 */
function getAllProjectData(name) {

	$.ajax({
		type : "post",
		dataType : "json",
		url : "../test1.do",
		success : function(allProjects) {
			
			var data = $.parseJSON(allProjects);
					
			switch (name) {
				case 'projectSort':
					data.sort(dataSort('project'));
					break;
				/*case 'jiraProjectSort':
					data.sort(dataSort('jiraProject'));
					break;
				case 'releaseNoSort':
					data.sort(dataSort('releaseNo'));
					break;
				case 'managerNameSort':
					data.sort(dataSort('managerName'));
					break;*/
			}
			
			var html = [];
			
			html.push("<table class=\"JiraNameAlternationTable_content\">");
			html.push("<thead>");			
			html.push("<tr>");
			html.push("<td class=\"tdcheck\">");
			html.push("<input type=\"checkbox\" id=\"allCheckbox\"/>");
			html.push("</td>");
			html.push("<td class=\"tdMenu\">");
			html.push("<label id=\"projectSort\"> 프로젝트 명");
			//html.push("<span class=\"glyphicon glyphicon-menu-down\" aria-hidden=\"true\"></span>");
			html.push("</label></td>");
			html.push("<td class=\"tdMenu\">");
			html.push("<label id=\"jiraProjectSort\">키");
			//html.push("<span class=\"glyphicon glyphicon-menu-down\" aria-hidden=\"true\"></span>");
			html.push("</label></td>");
			html.push("<td class=\"tdMenu\">");
			html.push("<label id=\"releaseNoSort\">릴리즈 수");
			//html.push("<span class=\"glyphicon glyphicon-menu-down\" aria-hidden=\"true\"></span>");
			html.push("</label></td>");
			html.push("<td class=\"tdMenu\">");
			html.push("<label id=\"managerNameSort\">담당자");
			//html.push("<span class=\"glyphicon glyphicon-menu-down\" aria-hidden=\"true\"></span>");
			html.push("</label></td>");
			html.push("</tr>");
			html.push("</thead>");
			html.push("<tbody>");
				
			$.each(data, function(index, item) {
				if(item.active == "true") {
					html.push("<tr>");
					html.push("<td class=\"tdcheck\">");
					html.push("<input type=\"checkbox\" id=\"projectCheckbox\" name=\"projectCheckbox\" value=\""+item.project+"\"/>");
					html.push("</td>");
					html.push("<td class=\"tdContent\">");
					html.push("<label id=\"projectName\" onmouseover=\"setCursor(this,'pointer')\">" + item.project + "</label>");
					html.push("</td>");
					html.push("<td class=\"tdContent\">");
					html.push("<label id=\"jiraProject\">" + item.jiraProject + "</label>");
					html.push("</td>");
					html.push("<td class=\"tdContent\">");
					html.push("<label id=\"releaseNo\">" + item.releaseNo + "</label>");
					html.push("</td>");
					html.push("<td class=\"tdContent\">");
					html.push("<label id=\"managerName\">" + item.managerName + "</label>");
					html.push("</td>");
					html.push("</tr>");
				}
			});
			
			html.push("</tbody>");
			html.push("</table>");

			$("#jiraNameConfigContentArea").html(html.join(""));
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러 발생 \n" + textStatus + " : " + errorThrown);
		}
	});
	
}

function setCursor(str,str2){
	str.style.cursor = str2;
}
/**
 * 데이터를 sorting하는 함수
 */
function dataSort(name) {
	return function(a, b) {
		if ( a[name] == b[name] ) return 0;
		return a[name] < b[name] ? -1 : 1;
	}
} 