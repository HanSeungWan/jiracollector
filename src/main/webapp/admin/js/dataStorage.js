/**
 * 상품과 버전에 맞는 날짜 데이터를 저장하는 자바스크립트
 */

$(document).ready(function() {
	$('#btnSubmitDate').click(function(event) {
		saveDate();
	});
});

/**
 * 상품명,버전,시작날짜,끝날짜를 저장하는 함수
 */
function saveDate() {
	var projectData = $('#project').val();
	var versionData = $('#version').val();
	var startDate = $('#txtStartDate').val();
	var endDate = $('#txtEndDate').val();

	var eachDateData = {
		"product" : projectData,
		"version" : versionData,
		"startDate" : startDate,
		"endDate" : endDate
	};

	if (startDate > endDate) {

		callAlertTop('시작날짜가 끝날짜보다 늦을 수 없습니다. 재 설정해주세요.');
		
	} else {
		// 해당 상품명과 버전에 해당하는 날짜를 저장함
		$.ajax({
			url : "../saveDate.do",
			type : "POST",
			data : eachDateData,
			success : function(data) {
				callAlertMid('', '저장에 성공하였습니다.');
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("에러 발생 " + textStatus + " : " + errorThrown);
			}
		});

	}
}