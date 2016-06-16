/*
 * 관리자가 새로운 프로젝트를 추가시키는 역할을 하는 자바스크립트
 */
$(document).ready(function() {

	// 지표추가와 단위의 리스트를 불러온다.
	// showQualityIndicatorList();

	// 품질지표추가 모달에서 확인 버튼을 누른다.
	// 만약 Goal 이 비어있거나 0~100사이의 숫자가 아니면 save 실행 안되게..

	$('#btnAddQualityIndicator').click(function(event) {	
		// 이미 존재하는 지표 리스트를 불러온다.
		callsavedQAIndicatorList();
		// 사용자가 선택한 지표가 기존에 존재하지 않으면 existingIndicator == '' 이 된다.
		var existingIndicator = $('#existingIndicatorAlert').text();

		if (existingIndicator == '') {
			// 목표 필드가 비어있지는 않은지, validation을 충족하는지 확인한다.
			var filledGoalForm = false;
			filledGoalForm = checkGoalForm();
			// 목표 필드의 값이 정상적이면 저장하는 함수를 호출한다.
			if (filledGoalForm == true) {
				saveNewQAIndicator();
			}
			else {
				//alert("목표를 입력해주세요.");
				callAlertTop('목표를 입력해주세요.');
			}

			// 확인 후 페이지 초기화하기
			refreshQAIndicatorAdditionModal();
		}
	});

	//지표 selectbox에서 변화가 일어날 경우
	$("select#qualityIndicator").bind("change", function() {
		// 지표를 선택했을 때 나타날 경고창 부분을 미리 초기화 해준다.
		document.getElementById("existingIndicatorAlert").innerText = '';
		// 존재하는 지표 리스트를 불러온다.
		callsavedQAIndicatorList();
		// 사용자가 선택한 지표가 기존에 존재하지 않으면 existingIndicator == '' 이 된다.
		var existingIndicator = $('#existingIndicatorAlert').text();
		if (existingIndicator == '') {
			document.getElementById("existingIndicatorAlert").innerText = '';
		}
		// 사용자가 선택한 지표가 기존에 존재하면 경우 경고창을 띄워준다.
		else
			document.getElementById("existingIndicatorAlert").innerText = '이미 존재하는 지표입니다.';
	});

	// 취소 버튼을 누르면, modal화면을 초기화 하는 함수를 불러온다.
	// (모달을 다시 호출했을 때 이전에 입력했던 값을 없애주기 위함)
	$('#btnAddQualityIndicatorCancel').click(function(event) {

		$("select#qualityIndicator").val('이슈조치율');
		$('#qualityIndicatorGoal').val('');
		document.getElementById("existingIndicatorAlert").innerText = '';

		var QAGoal = document.getElementById('QAGoal');
		var QAGoalAlert = QAGoal.firstChild.nextSibling;

		while(QAGoalAlert) {
			QAGoal.removeChild(QAGoalAlert.nextSibling);
		}
	});
});

/*
 * 목표 form이 비어있는지, 0~100사이의 값이 맞는지 확인하는 함수 //수정 필요: 목표를 입력해주세요. 안나옴
 */
function checkGoalForm(inputvalue) {
	if(inputvalue == null) {
		var goalInput = $('#qualityIndicatorGoal').val();
	}
	else if(inputvalue != null) {
		var goalInput = $('#qualityIndicatorGoalChange').val();
	}
	
	if (goalInput > 0 && goalInput <= 100) {
		return true;
	} else if (goalInput == null) {
		return false;
	} else
		return false;
}

/*
 * 품질지표에서 지표추가와 단위 목록을 불러오는 함수	//아직 완전히 구현되지 않음
 */
function showQualityIndicatorList() {
	$.ajax({
		type : "post",
		dataType : "json",
		url : "../test7.do",
		success : function(allQualityIndicator) {
			var parsedAllQualityIndicator = $.parseJSON(allQualityIndicator);
			$.each(parsedAllQualityIndicator, function(index, item) {
				// 지표추가 리스트 가져오기
				$("select#qualityIndicator").append(
						"<option onmouseup='isDefaultQualityIndicator(this)' id = '"
								+ item.graphtype + "' value='" + item.graphtype
								+ "'>" + item.graphtype + "</option>");
				// 단위 목록 가져오기
				$("select#qualityIndicatorUnit").append(
						"<option onmouseup='isDefaultQualityUnitIndicator(this)' id = '"
								+ item.targetChar + "' value='"
								+ item.targetChar + "'>" + item.targetChar
								+ "</option>");
			});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러 발생 \n" + textStatus + " : " + errorThrown);
		}
	});
}

/*
 * 새로운 품질지표를 등록하는 함수
 */
function saveNewQAIndicator() {

	var QAIndicator = $("select#qualityIndicator").val();
	var QAIndicatorUnit = $("select#qualityIndicatorUnit").val();
	var qualityIndicatorGoal = $('#qualityIndicatorGoal').val();
	var jiraProjectID = nowProjectId;
	var explanation = '';

	var newQAIndicatorInfo = {
		"jiraProjectName" : jiraProjectID,
		"graphtype" : QAIndicator,
		"targetNo" : qualityIndicatorGoal,
		"targetChar" : QAIndicatorUnit,
		"explanation" : explanation
	};

	$.ajax({
		url : "../test9.do",
		type : "POST",
		data : newQAIndicatorInfo,
		success : function(successfulData) {
			if (successfulData == "TRUE") {
				//alert("추가되었습니다.");
				callAlertMid('', '추가되었습니다.');
				
				$('#indicatorAddition').modal('hide');
				getQualityIndication('graphtype');
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

/*
 * 현재까지 저장되어 있는 지표 목록 가져오기
 */
function callsavedQAIndicatorList() {
		var QAIndicator = $("select#qualityIndicator").val();
		$.ajax({
			type : "post",
			dataType : "json",
			url : "../test8.do",
			async : false,
			success : function(allIndicators) {
				var parsedallIndicators = $.parseJSON(allIndicators);
				$.each(
						parsedallIndicators,
						function(index, item) {
							if (QAIndicator == item.graphtype && originProjectKey == item.jiraProjectName) {
								document.getElementById("existingIndicatorAlert").innerText = "이미 존재하는 지표입니다.";
							}
					});
			},
			error : function(xhr, status, error) {
				alert("에러 발생 \n" + textStatus + " : " + errorThrown);
			}
		});
}