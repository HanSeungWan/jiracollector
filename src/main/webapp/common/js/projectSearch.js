/**
 * 상품명에 대한 jira에 등록된 상품명을 찾는 자바스크립트
 */
$(document).ready(function() {
    $("select#project option").remove();
    getProjectData();

    $("select#project").bind("change", function() {
        if($("select#project").val() != "projectdefault") {
            var selectedProject = $('#product').val();
            $('#txtJiraName').val(selectedProject);
        }
        else
            $('#txtJiraName').val("");
    });
});

function getProjectData() {

    $.ajax({
        type : "post",
        dataType : "json",
        url : "../test1.do",
        success : function(allProjects) {
            var parsedProjects = $.parseJSON(allProjects);

            parsedProjects.sort(dataSort('project'));

            $("select#project")
                .append("<option id='projectdefault' value='projectdefault' selected>제품선택</option>");
            $.each(parsedProjects, function(index, item) {
                if(item.active == "true"){
                    $("select#project").append(
                        "<option id = '" + item.project + "' value='" + item.jiraProject+ "'>" + item.project + "</option>");
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