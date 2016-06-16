/**
 * 상품명과 버전을 받아 데이터가 저장되어 있는 확인하는 자바스크립트 존재하는 데이터가 있으면 시작 날짜와 끝 날짜를 텍스트로 띄워주고, 없는
 * 경우, 버전을 등록할 수 있게 해주는 역할을 한다.
 */
$(document).ready(function() {

	$("select#version").bind("change", function() {
		// 상품명과 버전에 대한 data 받기
		var projectData = $('#project').val();
		var versionData = $('#version').val();

		existsDateForVersion(projectData, versionData);
	});
});

/**
 * version에 대한 날짜 데이터의 존재 유무를 판단하는 함수
 * 
 * @param receivedProduct
 *            상품정보
 * @param receivedVersion
 *            버전정보
 */
function existsDateForVersion(receivedProduct, receivedVersion) {

	// 시작날짜와 끝날짜 value 초기화
	initDateText();

	var productAndVersionData = {
		"product" : receivedProduct,
		"version" : receivedVersion
	};

	// 지정되어 있는 날짜인지 확인
	$.ajax({
		url : "../chcekHasDateRange.do",
		type : "POST",
		dataType : "json",
		data : productAndVersionData,
		success : function(existsStored) {

			// JSON 파싱후, key값이 check인 object를 result로 저장.
			var resultData = $.parseJSON(existsStored);
			var result = resultData.check;

			// 저장되어 있으면 OK, 저장되어 있지 않으면 NULL
			if (result == "OK") {
				getSavedDate(productAndVersionData);
			} else if (result == "NULL") {
				// 시작날짜와 끝날짜 value 초기화
				initDateText();
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러 발생~~ㅠ0ㅠ \n" + textStatus + " : " + errorThrown);
		}
	});
}

/**
 * 저장되어있던 시작날짜와 끝날짜를 가져오는 함수.
 * 
 * @param productData
 *            상품명 정보
 * @param versionData
 *            버전 정보
 */
function getSavedDate(productAndVersionData) {
	$.ajax({
		url : "../getStartEndDate.do",
		type : 'POST',
		dataType : "json",
		data : productAndVersionData,
		success : function(dateData) {
			var data = $.parseJSON(dateData);

			var reciveStartDate = data.startDate;
			var reciveEndDate = data.endDate;

			//alert("시작 날짜 : " + reciveStartDate + "\n마지막 날짜 : "
			//		+ reciveEndDate);
			var text = "시작 날짜 : " + reciveStartDate + " 마지막 날짜 : "
			+ reciveEndDate;
			callAlertTop(text);
			insertDateText(reciveStartDate, reciveEndDate);

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("에러 발생~~ㅠ0ㅠ \n" + textStatus + " : " + errorThrown);
		}
	});
}

/**
 * 날짜 데이터를 sorting하는 함수
 */
function dateSort(a, b) {
	return new Date(a.날짜).getTime() - new Date(b.날짜).getTime();
}

/**
 * DateText를 초기화하는 함수.
 */
function initDateText() {
	$('#txtStartDate').val("");
	$('#txtEndDate').val("");
}

/**
 * DateText에 시작날짜와 끝날짜를 넣는 함수.
 * 
 * @param startDate
 *            시작날짜
 * @param endDate
 *            끝날짜
 */
function insertDateText(startDate, endDate) {
	$('#txtStartDate').val(startDate);
	$('#txtEndDate').val(endDate);
}
