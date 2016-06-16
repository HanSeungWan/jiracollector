/** ***********아래 수정필요************ */
/*
 * 상단 alert창 만들기
 */
function showTopAlert() {
	$("body").append(
			"<div id='allScreen' class='alert alert-dismissible'></div>");
	wrapWindowByMask();
	$("body")
			.append(
					"<div class='alertpositionTop alert alert-info' role='alert'><button type='button' class='close closeAlert' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button><span class='alertText' style='font-weight:bold'></span></div>");

	$('.closeAlert').click(function(event) {
		var alertBackground = $('#allScreen');
		alertBackground.fadeOut();
	});
}

/*
 * 중앙 alert창 만들기
 */
function showMidAlert(movedPage) {
	$("body").append(
			"<div id='allScreen' class='alert alert-dismissible'></div>");
	wrapWindowByMask();
	$("body")
			.append(
					"<div class='alertpositionMid alert alert-success' role='alert'><button type='button' class='close closeAlert' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button><p class='alertText' style='font-weight:bold; padding-top:30px; padding-bottom:20px;'></p><p><button type='button' class='closeAlert btn btn-primary' data-dismiss='alert'>확인</button></p></div>");

	if (movedPage != '') {
		$('.closeAlert').click(function(event) {
			var alertBackground = $('#allScreen');
			alertBackground.fadeOut();
			movePage(movedPage);
		});
	}
	else if (movedPage == '') {
		$('.closeAlert').click(function(event) {
			var alertBackground = $('#allScreen');
			alertBackground.fadeOut();
		});
	}
}

/*
 * alert 확인 후 페이지 이동
 */
function movePage(movedPage) {
	if(movedPage != null) {
	location.href = movedPage;
	}
}

/*
 * Alert창 n초후 자동으로 사라지게 하기
 */
function fadeOutAlert() {
	var alertBox = $('.alertpositionTop');
	var alertBox2 = $('.alertpositionMid');
	var alertBackground = $('#allScreen');
	alertBox.fadeOut(300);
	alertBox2.fadeOut(300);
	alertBackground.fadeOut(200);
}
/*
 * 전체를 덮는 div
 */
function wrapWindowByMask() {
	// 화면의 높이와 너비를 구한다.
	var maskHeight = $(document).height();
	var maskWidth = $(window).width();
	// 마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
	$('#allScreen').css({
		'width' : maskWidth,
		'height' : maskHeight
	});
}

/*
 * 실제로 불러다 쓰는 함수
 */
function callAlertTop(showingMsg) {
	$('#allScreen').remove();
 	showTopAlert();
 	$('.alertText').text(showingMsg);
 	setTimeout("fadeOutAlert()", 2000);
}
//callAlertTop('프로젝트 명이 공백일 수 없습니다.');

function callAlertMid(movedPage, showingMsg) {
	$('#allScreen').remove();
 	showMidAlert(movedPage);
 	$('.alertText').text(showingMsg);
 	setTimeout("fadeOutAlert()", 2000);
}
//callAlertMid('./projectlist', '추가되었습니다.');