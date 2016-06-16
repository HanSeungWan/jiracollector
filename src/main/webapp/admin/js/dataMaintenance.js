/**
 * 메인 페이지에서 관리페이지로 정보를 전해주는 자바스크립트입니다. localStorage를 사용하여 Administrator에서 정했던
 * 상품명과 버전을 받아 selected box에 띄워주는 역할을 합니다.
 */

$(document).ready(function() {

	// sessionStorage에 저장되어있는 상품명과 버전을 가져옴
	var recivedProject = sessionStorage.getItem("project");
	var recivedVersion = sessionStorage.getItem("version");

	// 페이지가 로딩될 때 'Loading 이미지'를 숨김
	$('#viewLoading').hide();

	if (recivedProject != null) {
		setTimeout(function() {
			// id값이 product인 value값을 recivedProject 으로 지정
			$("#product").val(recivedProject);

			// 해당 product에 대한 version을 가져오는 함수
			getVersionData();

			setTimeout(function() {
				// id값이 version인 value값을 recivedVersion 으로 지정
				$("#version").val(recivedVersion);

				// sessionStorage에 기록되어 있던 것을 모두 지움
				sessionStorage.clear();
			}, 300);
		}, 300);

		// 상품명과 버전을 받아 저장되어 있는 날짜를 가져옴
		existsDateForVersion(recivedProject, recivedVersion);

		// ajax 실행 및 완료시 'Loading 이미지'작동
		$('#viewLoading').ajaxStart(function() {

			$(this).fadeIn(500);

		}).ajaxStop(function() {

			$(this).hide();

		});
	}
});