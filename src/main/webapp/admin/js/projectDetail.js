/*
 * 프로젝트 상세에서 해당 프로젝트의 정보를 불러오고 수정할 수 있는 자바스크립트
 */
$(document).ready(function () {
	var receivedProject = sessionStorage.getItem("projectName");
    // 프로젝트의 상세 제공 및 변경사항 수정
    provideProjectDetailAndChanges(receivedProject);

    markProjectKey(receivedProject);

    $("select#personInChargeOfDetail").bind("change", function () {
        saveChangesOfDetail();
    });
});

/**
 * 프로젝트key를 상단에 출력하는 함수
 * @param projectKey 프로젝트 키
 */
function markProjectKey(projectKey) {
    $("#mainTitle").append("<span class=\"projectKey\">/\n\n\n\n\n" + projectKey + "</span>");
}
/**
 * 프로젝트 상세에서 해당 프로젝트의 정보를 불러오는 역할을 하는 함수
 * @param projectKey
 */
function provideProjectDetailAndChanges(receivedProject) {
    checkmouse = 'nonclicked';
    $.ajax({
        type: "POST",
        dataType: "JSON",
        url: "../test1.do",
        success: function (projectDetail) {
            var parsedProjectDetail = $.parseJSON(projectDetail);
            $.each(parsedProjectDetail, function (index, item) {
                if (receivedProject == item.project) {
                    // 현재 선택된 프로젝트의 Primary Key를 유지함
                    nowProjectId = item.projectId;
                    originProjectName = item.project;
                    originProjectKey = item.jiraProject;
                    originProjectManager = item.managerName;
                    
                    $("#projectNameOfDetailPage").empty();
                    $("#projectKeyOfDetailPage").empty();
                    
                    // 담당자 목록을 제공
                    showManagerList();
                
                    //모든 항목이 담당자목록이 제공된 이후에 나타나도록 하기위해 200timeout
                    setTimeout(function () {
                    	// 제공된 담당자 목록 중 해당 프로젝트의 담당자를 보여줌
                        $("#personInChargeOfDetail").val(item.managerName);
                    	// 프로젝트 명 제공, class=editable을 이용하여 프로젝트 명의 변경사항을 수정함
                   	 	$("#projectNameOfDetailPage").append(
                                "<span id=\"projectNameDetail\" class=\"projectNameDetail form-control-static editable\" onmouseover=\"mouseOver()\" onmouseout=\"mouseOut()\">"
                                + item.project + "</span>");
                    	// 프로젝트 키 제공
                    	 $("#projectKeyOfDetailPage").append(
                                 "<p class=\"projectKeyDetail form-control-static\">"
                                 + item.jiraProject + "</p>");
                        //sessionStorage.clear();
                    }, 400);
                }
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("에러 발생 " + textStatus + " : " + errorThrown);
        }
    });
}
/*
 * 담당자 목록을 제공하는 함수
 */
function showManagerList() {
    $.ajax({
        type: "post",
        dataType: "json",
        url: "../test5.do",
        success: function (allManagers) {
            var parsedAllManagers = $.parseJSON(allManagers);
            $("select#personInChargeOfDetail")
                .append(
                    "<option id='managerdefault' value='managerdefault' selected>담당자 선택</option>");
            $.each(parsedAllManagers, function (index, item) {
                $("select#personInChargeOfDetail").append(
                    "<option id = '" + item + "' value='" + item
                    + "'>" + item + "</option>");
            });
        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert("에러 발생 \n" + textStatus + " : " + errorThrown);
        }
    });
}

/*
 * 프로젝트 명, 담당자가 변경되었을 때 변경된 사항을 저장하는 함수 (inlineTextEditor.js에서 실행됨)
 */
function saveChangesOfDetail() {
    var projectPK = nowProjectId; // 현재 프로젝트의 기본키 값을 유지함
    var projectName = $('.projectNameDetail').text();
    receivedProject = projectName;
    var jiraProjectName = $('.projectKeyDetail').text();
    var personInCharge = $("select#personInChargeOfDetail").val();

    // 만약 프로젝트명과 프로젝트 담당자가 공백이면 error메시지를 보냄
    if (projectName == '') {
        //alert("프로젝트 명이 공백일 수 없습니다.");
    	callAlertTop('프로젝트 명이 공백일 수 없습니다.');
        $('.projectNameDetail').text(originProjectName);

    } else if (personInCharge == 'managerdefault') {
        //alert("담당자가 공백일 수 없습니다.");
    	callAlertTop('담당자가 공백일 수 없습니다.');
        $("#personInChargeOfDetail").val(originProjectManager);

    } else {
        var changedProjectInfo = {
            "projectId": projectPK,
            "product": projectName,
            "jiraProduct": jiraProjectName,
            "managerName": personInCharge
        };

        // 변경된 프로젝트 정보를 제공
        $.ajax({
            url: "../test3.do",
            type: "POST",
            data: changedProjectInfo,
            success: function (successfulData) {
                if (successfulData != "TRUE") {                    
                    $('.projectNameDetail').text(originProjectName);
                    $("#personInChargeOfDetail").val(originProjectManager);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("에러 발생 " + textStatus + " : " + errorThrown);
            }
        });
    }
}

/*
 * 마우스 오버
 */
function mouseOver() {
	
	if(checkmouse == 'nonclicked') {
		var element = document.getElementById('projectNameDetail');
		element.style.paddingLeft = "2px";
	    element.style.border = "1px solid #bcbcbc";
	    element.style.borderRadius = "3px";
	    $("#projectNameDetail").append(
                "<span class='textEditorIcon'><span class='glyphicon glyphicon-pencil' aria-hidden='true'></span></span>");
	    
	}
	/*
	else if(checkmouse == 'clicked'){
			var element = document.getElementById('projectNameDetail');
		    element.style.border = "1px solid transparent";	 
	}*/
}

function mouseOut() 
{
	$(".textEditorIcon").remove();
	var element = document.getElementById('projectNameDetail');
	element.style.border = "1px solid transparent";
}