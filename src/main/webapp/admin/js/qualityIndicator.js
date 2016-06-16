$(document).ready(function() {
	// 초기 정렬
	var flag = 1;

	getQualityIndication(flag);

	$(document).on("click", "#allCheckbox", function() {
		// 전체선택 체크박스가 체크된상태일경우
		if ($("#allCheckbox").prop("checked")) {
			$("input[type=checkbox]").prop("checked", true);
			// 전체선택 체크박스가 해제된 경우
		} else {
			$("input[type=checkbox]").prop("checked", false);
		}
	});

	$(document).on('click', '#graphtype', function() {
		if(flag == 1) {
			flag = 2;
			getQualityIndication(flag);
		}
		else if(flag == 2) {
			flag = 1;
			getQualityIndication(flag);
		}
	});

	$("#btnindicatorDelete").click(function(event) {
		var checkboxValues = [];

		$("input[name='indicatorCheckbox']:checked").each(function(i) {
			checkboxValues.push($(this).val());
		});


        var checkboxData = { "jiraproject" : nowProjectId, "checkArray" : checkboxValues };

		deleteQualityIndicator(checkboxData, name);

	});

	// ajax 실행 및 완료시 'Loading 이미지'작동
	$('#viewLoading').ajaxStart(function() {

		$(this).fadeIn(500);

	}).ajaxStop(function() {

		$(this).hide();

	});
});

/**
 * 품질 지표를 삭제하는 함수
 * 
 * @param data
 *            체크된 박스에 대한 배열 데이터
 */
function deleteQualityIndicator(data, name) {
	$.ajax({
		url : "../test10.do",
		type : "POST",
		data : data,
		success : function(successfulData) {
			if (successfulData == "TRUE") {
				//alert("삭제되었습니다.");
				callAlertMid('', '삭제되었습니다.');

				getQualityIndication(name);
			} else {
        		//alert("실패했습니다.");
				callAlertMid('', '실패했습니다.');
        	}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러 발생 " + textStatus + " : " + errorThrown);
		}
	});
}

/**
 * 품질지표의 데이터를 가져와 동적으로 표를 만들어주는 함수
 * 
 * @param sortingDataName
 *            정렬 기준이 되는 데이터
 */
function getQualityIndication(currentFlag) {

	$.ajax({
		type : "post",
		dataType : "json",
		url : "../test8.do",
		success : function(allProjects) {

			var html = [];

			html.push("<table class=\"qualityIndicatorTable_content\">");
			html.push("<thead>");
			html.push("<tr>");
			html.push("<td class=\"tdcheck\">");
			html.push("<input type=\"checkbox\" id=\"allCheckbox\"/>");
			html.push("</td>");
			html.push("<td class=\"tdMenu\">");

			var data = $.parseJSON(allProjects);

			switch (currentFlag) {
				case 1:
					data.sort(dataSort('graphtype'));
					html.push("<label id=\"graphtype\"> 지표&nbsp;<span class=\"glyphicon glyphicon-menu-down\" aria-hidden=\"true\"></span>");
					break;
				case 2:
					data.sort(dataReverseSort('graphtype'));
					html.push("<label id=\"graphtype\"> 지표&nbsp;<span class=\"glyphicon glyphicon-menu-up\" aria-hidden=\"true\"></span>");
					break;
			}

			html.push("</label></td>");
			html.push("<td class=\"tdMenu\">");
			html.push("<label id=\"targetChar\">단위&nbsp;");
			html.push("</label></td>");
			html.push("<td class=\"tdMenu\">");
			html.push("<label id=\"targetNo\">목표&nbsp;");
			html.push("</label></td>");
			html.push("<td class=\"tdExplanation\">");
			html.push("<label id=\"explanation\">설명&nbsp;");
			html.push("</label></td>");
			html.push("</tr>");
			html.push("</thead>");
			html.push("<tbody>");

			var count = 0;

			$.each( data, function(index, item) {
				if (item.jiraProjectName == originProjectKey) {
					count++;
					html.push("<tr>");
					html.push("<td class=\"tdcheck\">");
					html.push("<input type=\"checkbox\" id=\"indicatorCheckbox\" name=\"indicatorCheckbox\" value=\""
									+ item.graphtype + "\"/>");
					html.push("</td>");
					html.push("<td class=\"tdContent\">");
					html.push("<label id=\"graphtypeName\" data-toggle=\"modal\" onmouseover=\"setCursor(this,'pointer')\" data-target=\".indicatorChange\">"
									+ item.graphtype
									+ "</label>");
					html.push("</td>");
					html.push("<td class=\"tdContent\">");
					html.push("<label id=\"targetChar\">"
									+ item.targetChar
									+ "</label>");
					html.push("</td>");
					html.push("<td class=\"tdContent\">");
					html.push("<label id=\"targetNo\">"
							+ item.targetNo
							+ "</label>");
					html.push("</td>");
					html.push("<td class=\"tdContent\">");
					html.push("<label id=\"explanation\">"
									+ item.explanation
									+ "</label>");
					html.push("</td>");
					html.push("</tr>");
				}
			});

			if (count == 0) {
				html.push("<tr>");
				html.push("<td class=\"text-center\" colspan=\"5\"><br>등록된 지표가 없습니다.");
				html.push("</td>");
				html.push("</tr>");
				html.push("</tbody>");
			}

			html.push("</tbody>");
			html.push("</table>");

			$("#qualityIndicatorContentArea").html(html.join(""));

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러 발생 \n" + textStatus + " : " + errorThrown);
		}
	});

	//지표클릭시 수정창이 뜰 때 기존 정보를 불러오는 함수를 실행하기 위함
	$(document).on('click', '#graphtypeName', function() {
		var nowGraphType = $(this).text();
		callQAIndicatorDetail(nowGraphType);
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

/**
 * 데이터를 역으로 sorting하는 함수
 */
function dataReverseSort(name) {
	return function(a, b) {
		if ( a[name] == b[name] ) return 0;
		return a[name] > b[name] ? -1 : 1;
	}
}

/*
 * 해당 품질 지표의 내용을 가져오는 함수
 */
function callQAIndicatorDetail(nowGraphType) {
	$
			.ajax({
				type : "POST",
				dataType : "JSON",
				url : "../test8.do",
				success : function(projectDetail) {
					var parsedProjectDetail = $.parseJSON(projectDetail);
					$.each(parsedProjectDetail, function(index, item) {
						if (originProjectKey == item.jiraProjectName
								&& item.graphtype == nowGraphType) {
							// 단위 목록을 제공 (후에 DB목록이 완성되면 수정)
							//showQAIndicatorUnitList();

							// 지표 제공
							originGraphtype = item.graphtype;
							$("#qualityIndicatorChange").text(item.graphtype);

							// 단위 제공
							$("#qualityIndicatorUnitChange").val("%");

							// 목표 제공
							$('#qualityIndicatorGoalChange').val(item.targetNo);
						}
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("에러 발생 " + textStatus + " : " + errorThrown);
				}
			});
}

/*
 * 단위 목록 불러오기
 */
function showQAIndicatorUnitList() {
	$.ajax({
        type: "post",
        dataType: "json",
        url: "../test8.do",
        success: function (allManagers) {
            var parsedAllManagers = $.parseJSON(allManagers);
           $.each(parsedAllManagers, function (index, item) {
                $("select#qualityIndicatorUnitChange").append(
                    "<option id = '" + item.targetChar + "' value='" + item.targetChar
                    + "'>" + item.targetChar + "</option>");
            });
        },
		error : function(jqXHR, textStatus, errorThrown) {
            alert("에러 발생 \n" + textStatus + " : " + errorThrown);
        }
    });
}


$(document).ready(function() {

	//확인버튼 누르면 저장하는 함수 불러오기
	$('#btnChangeQualityIndicator').click(function(event) {
			
			// 목표 필드가 비어있지는 않은지, validation을 충족하는지 확인한다.
			var filledGoalForm = false;
			filledGoalForm = checkGoalForm("changemodal");
			// 목표 필드의 값이 정상적이면 저장하는 함수를 호출한다.
			if (filledGoalForm == true) {
			saveChangedQAIndicator();
			}
			else {
				//alert("목표를 입력해주세요.");
				$('#allScreen').remove();
		     	showTopAlert();
		     	$('.alertText').text("목표를 입력해주세요.");
		     	setTimeout("fadeOutAlert()", 2000);
			}
			document.getElementById("existingIndicatorAlertChange").innerText = '';
		
	});

	// 취소 버튼을 누르면, modal화면을 초기화 하는 함수를 불러온다.
	// (모달을 다시 호출했을 때 이전에 입력했던 값을 없애주기 위함)
	$('#btnAddQualityIndicatorChangeCancel').click(function(event) {

		var QAGoalChange = document.getElementById('QAGoalChange');
		var QAGoalChangeAlert = QAGoalChange.firstChild.nextSibling;

		while(QAGoalChangeAlert) {
			QAGoalChange.removeChild(QAGoalChangeAlert.nextSibling);
		}
	});
});


/*
 * 수정된 내용을 저장하는 함수
 */
function saveChangedQAIndicator() {

	var QAIndicator = $("#qualityIndicatorChange").text();
	var QAIndicatorUnit = $("select#qualityIndicatorUnitChange").val();
	var qualityIndicatorGoal = $('#qualityIndicatorGoalChange').val();
	var jiraProjectID = nowProjectId;
	var explanation = '';

	var changedQAIndicatorInfo = {
		"jiraProjectName" : jiraProjectID,
		"graphtype" : QAIndicator,
		"targetNo" : qualityIndicatorGoal,
		"targetChar" : QAIndicatorUnit,
		"explanation" : explanation
	};

	$.ajax({
		url : "../test7.do",
		type : "POST",
		data : changedQAIndicatorInfo,
		success : function(successfulData) {
			if (successfulData == "TRUE") {
				//alert("수정되었습니다.");
				callAlertMid('', '수정되었습니다.');
				
				$('#indicatorChange').modal('hide');
				getQualityIndication(1);
			} else {
        		//alert("실패했습니다.");
				callAlertMid('', '실패했습니다.');
        	}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러 발생 " + textStatus + " : " + errorThrown);
		}
	});
}
