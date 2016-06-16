/*
 * 관리자가 새로운 프로젝트를 추가시키는 역할을 하는 자바스크립트
 */
$(document).ready(function() {
	showManagerList();
	$('#btnForSavingNewProject').click(function(event) {
		// 프로젝트 명, 프로젝트 키, 담당자 중 빈 칸이 있는지 확인한다.
		var filledAllForms = checkAllForms();
		if (filledAllForms == true) {
			saveNewProject();
		}
	});
});

/*
 * 빈칸이 있는지 확인하는 함수
 */
function checkAllForms() {
	// 프로젝트 명이 비어있으면 - 프로젝트 명이 공백인지 체크하는 함수로 이동
	if ($('#newProjectName').val() == "") {
		checkProjectName(newProjectName);
	}
	// 프로젝트 키가 비어있으면 - 프로젝트 키가 공백인지 체크하는 함수로 이동
	else if ($('#newProjectKey').val() == "") {
		checkProjectKey(newProjectKey);
	}
	// validation체크 함수로 이동 담당자가 default이면 - 담당자가 디폴트 값인지 체크하는 함수로 이동
	else if ($("select#personInCharge").val() == "managerdefault") {
		checkProjectManager();
	}
	// 프로젝트 명, 프로젝트 키, 담당자가 모두 채워져 있으면 true를 반환
	else {
		return true;
	}
}

/*
 * 프로젝트 명이 공백인지 체크하는 함수
 */
function checkProjectName(inputValue) {
	document.getElementById("newProjectNameAlert").innerText = "필수항목입니다.";
	inputValue.focus();
}

/*
 * 프로젝트 키가 공백인지 체크하는 함수
 */
function checkProjectKey(inputValue) {
	document.getElementById("newProjectKeyAlert").innerText = "필수항목입니다.";
}

/*
 * 프로젝트 담당자가 디폴트 값인지 체크하는 함수
 */
function checkProjectManager() {
	document.getElementById("newProjectManagerAlert").innerText = "필수항목입니다.";
}

/*
 * 담당자 목록을 불러오는 함수
 */
function showManagerList() {
	$.ajax({
		type : "post",
		dataType : "json",
		url : "../test5.do",
		success : function(allManagers) {
			var parsedAllManagers = $.parseJSON(allManagers);
			$("select#personInCharge")
				.append("<option id='managerdefault' value='managerdefault' selected>담당자 선택</option>");
			$.each(parsedAllManagers, function(index, item) {
				$("select#personInCharge").append(
					"<option onmouseup='isDefaultProjectManager(this)' id = '"
					+ item + "' value='" + item + "'>"
					+ item + "</option>");
			});
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러 발생 \n" + textStatus + " : " + errorThrown);
		}
	});
}

/*
 * 새로운 프로젝트를 등록하는 함수
 */
function saveNewProject() {
	var projectName = $('#newProjectName').val();
	var jiraProjectName = $('#newProjectKey').val();
	var personInCharge = $("select#personInCharge").val();

	var newProjectInfo = {
		"product" : projectName,
		"jiraProduct" : jiraProjectName,
		"managerName" : personInCharge
	};

	$.ajax({
		url : "../test4.do",
		type : "POST",
		data : newProjectInfo,
		success : function(successfulData) {
			if (successfulData == "TRUE") {
				callAlertMid('./projectlist', '추가되었습니다.');
				setTimeout("movePage('./projectlist')", 2000);
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
 * 프로젝트 키 입력시 영어만 입력되도록 하는 함수
 */
function isEng(event) {
	var code = event.keyCode;
	if (code == 9 || code == 36 || code == 35 || code == 37 || code == 39
		|| code == 8 || code == 46 || code == 20) {
		return;
	} else if (code > 64 && code < 91) {
		document.getElementById("newProjectKeyAlert").innerText = "";
		return;
	} else {
		document.getElementById("newProjectKeyAlert").innerText = "영어만 입력가능합니다. (특수문자, 공백 입력 불가능)";
		event.preventDefault();
	}
}

/*
 * 프로젝트 키에 영어만 입력되게 하는 함수 구버전(파이어폭스에서 작동 안함) function isEng(inputValue) {
 * 
 * var checkCode = inputValue.value.charCodeAt(inputValue.value.length - 1); var
 * str;
 * 
 * if (inputValue.value.length == 0) {
 * document.getElementById("newProjectKeyAlert").innerText = "필수항목입니다.";
 * inputValue.focus(); } else if (inputValue.value.length > 0 && !(checkCode >=
 * 65 && checkCode <= 90) && !(checkCode >= 97 && checkCode <= 122)) {
 * document.getElementById("newProjectKeyAlert").innerText = "영어만 입력가능합니다.
 * (특수문자, 공백 입력 불가능)"; var thisLength = inputValue.value.length; for (var i = 0;
 * i < thisLength; i++) { checkCode_for = inputValue.value
 * .charCodeAt(inputValue.value.length - 1); if (!(checkCode_for >= 65 &&
 * checkCode_for <= 90)) { str = inputValue.value .substring(0,
 * inputValue.value.length - 1); inputValue.value = str; } } inputValue.focus(); }
 * else if ((checkCode >= 65 && checkCode <= 90) || (checkCode >= 97 &&
 * checkCode <= 122)) { document.getElementById("newProjectKeyAlert").innerText =
 * ""; inputValue.focus(); } }
 */

/*
 * 프로젝트 명이 공백일 때 경고text 띄워주는 함수
 */
function isNullProjectName(inputValue) {

	if (inputValue.value.length == 0) {
		document.getElementById("newProjectNameAlert").innerText = "필수항목입니다.";
		inputValue.focus();
	} else {
		document.getElementById("newProjectNameAlert").innerText = "";
		inputValue.focus();
	}
}

/*
 * 프로젝트 담당자가 선택되었을 때 경고 text를 없애주는 함수
 */
function isDefaultProjectManager(inputValue) {
	if (inputValue.value != "managerdefault") {
		document.getElementById("newProjectManagerAlert").innerText = "";
	}
}