/**
 * JIRA 이슈 조치율에 대한 표를 그려주는 자바스크립트
 */

$(document).ready(function() {
	var startLen = 0;
	
	//테이블 종류를 선택하는 selectbox를 숨김
	$('#dvSelectTable').hide();
	
	$("#tableType").change( function(event) {
					
		var choiceProject = $('#project').val();
		var choiceVersion = $('#version').val();
		var tableData = $('#tableType').val();	
		
		//서버에 보낼 데이터
		var projectAndVersionData = { "product" : choiceProject, "version" : choiceVersion };

		var posturl = "";
		if (choiceProject == 'GO') {
			posturl = "getDOIssueMeasure.do";
		} else if (choiceProject == 'TMSE') {
			posturl = "getTMSEIssueMeasure.do";
		}
		$.ajax({
			url : posturl,
			type : 'POST',
			dataType : "json",
			data : projectAndVersionData,
			success : function(chartData) {
				
				var data = $.parseJSON(chartData);
				data.sort(dataSort('날짜')); // data를 sorting
				var listLen = data.length;
				
				if(tableData == 'allData') {
					startLen = 0;
				}
				else if(tableData == 'lastestData') {
					startLen = listLen-1;
				}
				drawTable(choiceProject, data, listLen, startLen);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("chartData 에러 발생 \n" + textStatus + " : "
						+ errorThrown);
			}
		});
		
	});
});

/**
 * 해당 data를 받아 이슈 조치율에 대한 표를 그려주는 함수
 * 
 * @param project 상품 정보
 * @param data 상품 정보에 대한 데이터
 * @param listLen 총 데이터의 길이
 * @param startLen 데이터를 출력할 시작점
 */
function drawTable(project, data, listLen, startLen) {

	var actionsLength = Object.keys(data[0].조치).length-1;
	var inActionsLength = Object.keys(data[0].미조치).length-1;
	var totalLength = actionsLength+inActionsLength;
	
	var html = [];

	html.push("<table id=\"tbContents\">");
	html.push("<tr class=\"header\">");
	html.push("<th>날짜</th>");
	html.push("<th>분류</th>");
	html.push("<th colspan='2'>데이터</th>");
	html.push("<th>분류별 합계</th>");
	html.push("<th>총 합계</th>");
	html.push("</tr>");
		
	for(var i = startLen; i < listLen; i++) {
		
		var sumData = parseInt(data[i].조치.TOTAL) + parseInt(data[i].미조치.TOTAL);
		var actions = Math.round(parseInt(data[i].조치.TOTAL)/sumData*100*10)/10;
		var inActions = Math.round(parseInt(data[i].미조치.TOTAL)/sumData*100*10)/10;
		
		if(data[i].조치.TOTAL == 0) {
			actions = 0;
		}
		if(data[i].미조치.TOTAL == 0) {
			inActions = 0;
		}
		
		html.push("<tr>");
		html.push("<td rowspan='"+totalLength+"'>" + data[i].날짜 + "</td>");
		html.push("<td rowspan='"+actionsLength+"'>조치 </td>");
		html.push("<td>닫힘</td>");
		html.push("<td>" + parseInt(data[i].조치.닫힘) + "</td>");
		html.push("<td rowspan='"+actionsLength+"'>" + parseInt(data[i].조치.TOTAL) + "( " + actions + "% ) </td>");
		html.push("<td rowspan='"+totalLength+"'>" + parseInt(data[i].조치.TOTAL + data[i].미조치.TOTAL) + "( " +  (actions + inActions) + "% ) </td>");
		html.push("</tr>");
		$.each(data[i].조치, function(key, value){
			if( key == 'TOTAL' || key == '닫힘')
		        return true;
			html.push("<tr>");
			html.push("<td>" + key + "</td>");
			html.push("<td>" + value + "</td>");
			html.push("</tr>");
		});
		html.push("<tr>");
		html.push("<td rowspan='"+inActionsLength+"'>미조치 </td>");
		html.push("<td>열림</td>");
		html.push("<td>" + parseInt(data[i].미조치.열림) + "</td>");
		html.push("<td rowspan='"+inActionsLength+"'>" + parseInt(data[i].미조치.TOTAL) + "( " + inActions + "% ) </td>");
		html.push("</tr>");
		$.each(data[i].미조치, function(key, value){
			if( key == 'TOTAL' || key == '열림')
		        return true;
			html.push("<tr>");
			html.push("<td>" + key + "</td>");
			html.push("<td>" + value + "</td>");
			html.push("</tr>");
		});
	}

	html.push("</table>");

	$("#dvOutput").html(html.join(""));
	
}

function dataSort(name) {
	return function(a, b) {
		if ( a[name] == b[name] ) return 0;
		return a[name] < b[name] ? -1 : 1;
	}
} 