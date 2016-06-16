/**
 * 선택한 상품에 대한 버전을 찾는 자바스크립트
 */
var dataLength = 0;

$(document).ready(function() {
	
	$("select#project").bind("change", function() {

		if($("select#project").val() != "projectdefault") { 
			getVersionData();			
		}

	});

	$(document).on("click","#releaseStatus",function(){
		
		var version = $(this).val();
		var releaseStatus = $(this).prop('checked');		
		var project = $('#project').val();

		var requestData = {"versionName" : version , "releaseStatus" : releaseStatus, "projectName" : project };
		
		$.ajax({
			type : "post",
			data : requestData,
			dataType : "json",
			url : "../test6.do",
			success : function(allProjects) {

				callAlertMid('', '저장이 완료되었습니다.');
				
				getVersionData();

			},error : function(jqXHR, textStatus, errorThrown) {
				alert("에러 발생 \n" + textStatus + " : " + errorThrown);
			}
		});
	});
});

/**
 * 선택한 상품명에 대해 보내고 json data를 받아 버전 셀렉트 박스에 추가하는 메서드
 */
function getVersionData() {

	var requestedProduct = $('#project').val();

	projectName = {"product" : requestedProduct};

	getReleaseVersionTable(projectName);
	
}

function getReleaseVersionTable(project) {

	$.ajax({
		type: "post",
		data: project,
		dataType: "json",
		url: "../versionInsertData.do",
		success: function (allProjects) {

			var data = $.parseJSON(allProjects);

			data.sort(dataSort('versionName'));

			dataLength = data.length;

			var html = [];

			html.push("<table class=\"versionAlternationTable_content\">");
			html.push("<thead>");
			html.push("<tr>");
			html.push("<td class=\"tdMenu\"> 버전명 </td>");
			html.push("<td class=\"tdMenu\"> True/False </td>");
			html.push("</tr>");
			html.push("</thead>");
			html.push("<tbody>");

			$.each(data, function (index, item) {
				html.push("<tr>");
				html.push("<td class=\"tdMenu\">");
				html.push("<label id=\"projectName\">" + item.versionName + "</label>");
				html.push("</td>");
				html.push("<td class=\"tdContent\">");
				if (item.releaseStatus == "true") {
					html.push("<input type=\"checkbox\" name=\"releaseStatus\" id=\"releaseStatus\" value=\"" + item.versionName + "\" checked=\"checked\"/>");
				}
				else if (item.releaseStatus == "false") {
					html.push("<input type=\"checkbox\" name=\"releaseStatus\" id=\"releaseStatus\" value=\"" + item.versionName + "\"/>");
				}
				html.push("</td>");
				html.push("</tr>");
			});

			html.push("</tbody>");
			html.push("</table>");

			$("#versionConfigContentArea").html(html.join(""));

		},
		error: function (jqXHR, textStatus, errorThrown) {
			alert("에러 발생 \n" + textStatus + " : " + errorThrown);
		}
	});
}

/**
 * 데이터를 sorting하는 함수
 */
function dataSort(name) {
	return function(a, b) {
		if (a[name] == b[name])
			return 0;
		return a[name] < b[name] ? -1 : 1;
	}
}