/**
 * 관리자 페이지 혹인 메인 페이지로 넘어가는 버튼의 이벤트를 구현한 자바스크립트
 */
$(document).ready(function() {

	$("#btnGoMainpage").click(function(event) {
		location.href = "../chartdrawer";
	});
	
	$("#btnGoAdmin").click(function(event) {
		location.href = "./dashboard";
	});
	
	$("#btnProjectAddition").click(function(event) {
		location.href = "./projectaddition";
	});
	
	$("#btnForGoBack").click(function(event) {
		location.href = "./projectlist";
	});

});