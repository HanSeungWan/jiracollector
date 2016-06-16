/**
 * 선택한 상품에 대한 버전을 찾는 자바스크립트
 */
$(document).ready(function() {

    $("select#project").bind("change", function() {
        $('#txtStartDate').val("");
        $('#txtEndDate').val("");

        if($("select#project").val() != "projectdefault") {
            $("select#version option").remove();
            getVersionData();
        } else {
            $("select#version option").remove();
            $("select#version")
                .append("<option id='versiondefault' value='versiondefault' selected>버전선택</option>");
        }

    });

});

/**
 * 선택한 상품명에 대해 보내고 json data를 받아 버전 셀렉트 박스에 추가하는 메서드
 */
function getVersionData() {

    var requestedProduct = $('#project').val();

    $.ajax({
        type : "post",
        data : requestedProduct,
        dataType : "json",
        url : "../versionInsertData.do",
        success : function(allVersions) {
            var parsedVersions = $.parseJSON(allVersions);

            parsedVersions.sort(dataSort('versionName'));

            $("select#version")
                .append("<option id='versiondefault' value='versiondefault' selected>버전선택</option>");

            $.each(parsedVersions, function(index, item) {
                if(item.releaseStatus =="true") {
                    $("select#version").append(
                        "<option id = '" + item.versionName + "' value='" + item.versionName+ "'>" + item.versionName + "</option>");
                }
            });
        },
        error : function(jqXHR, textStatus, errorThrown) {
            alert("에러 발생 \n" + textStatus + " : " + errorThrown);
        }
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