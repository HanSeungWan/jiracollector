$(document).ready(function() {
	
	//프로젝트 관리 > 프로젝트 목록 > 프로젝트 추가 > 프로젝트 키
		//추가예정
	
	//프로젝트 상세 > 품질 지표 > 품질 지표 추가 > 목표
	$("#indicatorAdditionModal").validate({
        rules : {
        	goal : {
                required : true,
                number : true,
                range : [0, 100]
                }
        },
        messages : {
        	goal : {
                required : " 필수항목입니다.",
                number : "숫자만 입력 가능합니다.",
                range : "0~100 사이의 값을 입력해주세요."
            }
        }
    });
	
	//프로젝트 상세 > 품질 지표 > 품질 지표 수정 > 목표
	$("#indicatorAdditionChangeModal").validate({
        rules : {
        	goalChange : {
                required : true,
                number : true,
                range : [0, 100]
                }
        },
        messages : {
        	goalChange : {
                required : " 필수항목입니다.",
                number : "숫자만 입력 가능합니다.",
                range : "0~100 사이의 값을 입력해주세요."
            }
        }
    });

    //input:text 에서 enter키 비활성화(enter누르면 submit되는 현상 방지)
    $("input:text").keydown(function(evt) {
        if (evt.keyCode == 13)
            return false;
    });
});