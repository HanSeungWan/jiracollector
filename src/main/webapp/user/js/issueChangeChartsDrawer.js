/**
 * JIRA 이슈 추이에 대해서 데이터를 받아 그래프를 생성해주는 자바스크립트
 */

// 상품명과 버전에 대한 data 받기
var graphTypeData = "";
var projectData = "";
var versionData = "";

$(document).ready( function() {

	$("#btnSubmitGraph").click(function(event) {

		projectData = $('#project').val();
		versionData = $('#version').val();

		if( projectData ==  "projectdefault" || versionData == "versiondefault" ) {
			alert("선택을 해주세요.");
		}
		else {

			var projectAndVersionData = {
				"product": projectData,
				"version": versionData
			};

			$.ajax({
				url: "chcekHasDateRange.do",
				type: 'POST',
				dataType: "json",
				data: projectAndVersionData,
				success: function (existsStored) {

					var resultData = $.parseJSON(existsStored);

					// 저장된 기간이 있는지 확인
					var result = resultData.check;

					if (result == "OK") {
						drawChart();
					} else if (result == "NULL") {
						//alert("상품 : " + projectData + "\n 버전 : " + versionData + "\n 날짜를 지정해주세요.");
						setValue(projectData, versionData);
						callAlertMid('./admin/projectdate', '날짜를 지정해주세요.');
						setTimeout("movePage('./admin/projectdate')", 2000);
					}
				},
				error: function (jqXHR, textStatus, errorThrown) {
					alert("existsData 에러 발생 \n" + textStatus
						+ " : " + errorThrown);
				}
			});
		}
	});
});

/**
 * 제품명과 버전명을 sessionStorage에 저장하는 함수
 * 
 * @param requestProject
 *            보낼 상품명
 * @param requestVersion
 *            보낼 버전명
 */
function setValue(requestProject, requestVersion) {
	sessionStorage.setItem("product", requestProject);
	sessionStorage.setItem("version", requestVersion);
}

/**
 * 상품명과 버전명을 받아 이슈 추이에 대한 그래프를 그려주는 함수
 */
function drawChart() {

	// 상품명과 버전에 대한 data 받기
	projectData = $('#project').val();
	versionData = $('#version').val();
	graphTypeData = $('#graph').val();

	if (graphTypeData == "issueChanges") {

		drawIssueChanges(projectData, versionData);
		$('#dvSelectTable').hide();
		$('#dvOutput').hide();

	} else if (graphTypeData == "stepChanges") {

		drawIssueActions(projectData, versionData);
		$('#dvOutput').show();

	}
}

/**
 * 날짜 이슈 추이 column, line 그래프를 그리는 함수
 * 
 * @param receivedProject
 *            상품정보
 * @param receivedVersion
 *            버전정보
 */
function drawIssueChanges(receivedProject, receivedVersion) {

	var projectAndVersionData = {
		"product" : receivedProject,
		"version" : receivedVersion
	};

	var keyValue = [];
	var dataByTheType = [];
	var total = [];
	var seriesData = [];	
	
	$.ajax({
		url : "projectVersionInsertData.do",
		type : 'POST',
		dataType : "json",
		data : projectAndVersionData,
		success : function(chartData) {

			var dataByTheDate = $.parseJSON(chartData); // 받은 데이터를 JSON

			dataByTheDate.sort(dataSort('날짜')); // data를 sorting
			
			// 날짜별 데이터의 길이 즉, 날짜의 갯수
			var listLen = dataByTheDate.length;

			var maxValue = 0;			

			// 타입별 데이터의 키값(타입의 종류)을 배열에 저장
			$.each(dataByTheDate[0], function(key, value) {
				keyValue.push(key);
			});

			//날짜와 전체 데이터는 따로 정리할 것이므로 제거
			remove(keyValue, "날짜");
			remove(keyValue, "전체");
			
			//alert(keyValue);
			
			//가로 막대 그래프를 그릴 전체 데이터를 total이란 변수명의 배열에 저장
			for (var i = 0; i < listLen; i++) {
				
				//키: 날짜, 값: 전체 데이터의 갯수
				total.push([dataByTheDate[i].날짜, parseInt(dataByTheDate[i].전체)]);
				
				// 그래프의 최댓값을 지정하기 위해 전체의 최댓값 구하기
				maxValue = Math.max(maxValue, dataByTheDate[i].전체);

			}

			seriesData.push({
				type : 'column',
				name : '전체',
				data : total,
				color : '#dddddd'
			})
			
			//데이터를 타입별로 분류하여 배열에 다시 저장
			for (var j = 0; j < keyValue.length; j++) {					
				
				dataByTheType[j] = [];				

				for (var i = 0; i < listLen; i++) {
					
					//키 : 날짜, 값 : 날짜 순서대로 타입별 데이터의 갯수
					dataByTheType[j].push([ dataByTheDate[i].날짜,
							parseInt(dataByTheDate[i][keyValue[j]])]);
					
				}
			
				seriesData.push({
					name : keyValue[j],
					type : 'line',
					data: dataByTheType[j]
				})
			}
			
			/**
			 * 저장한 데이터들을 가지고 차트를 그려주는 함수 chartContainer에 highcharts.js를
			 * 이용해 차트를 그려준다.
			 */

			$(function() {
				$('#chartContainer').highcharts({
					title : {
						text : $('#project option:selected').text() + ' JIRA 이슈추이',
						x : -20
					},
					subtitle : {
						text : $('#version option:selected').val() + ' 이슈 타입별 개수',
						x : -20
					},
					xAxis : {
						categories : [],
						type : 'datetime'
					},
					yAxis : {
						min : 0,
						max : maxValue,
						title : {
							text : '갯수'
						}
					},
					tooltip : {
						headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
						pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}:</td>'
								+ '<td style="padding:0"><b>{point.y} 개</b></td></tr>',
						footerFormat : '</table>',
						shared : true,
						enabled : true,
						useHTML : true
					},
					series : seriesData
				});
			});

		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("chartData 에러 발생 \n" + textStatus + " : "
					+ errorThrown);
		}
	});
}

function remove(arr, value) {
	var i, len;
	if (arr.indexOf) { // IE9+, 다른 모든 브라우져
		i = arr.indexOf(value);
		if (i !== -1) {
			arr.splice(i, 1);
		}
	} else { // IE8 이하
		len = arr.length;
		for (i = 0; i < arr.len; i++) {
			if (arr[i] === value) {
				arr.splice(i, 1);
				return;
			}
		}
	}
}

function dataSort(name) {
	return function(a, b) {
		if ( a[name] == b[name] ) return 0;
		return a[name] < b[name] ? -1 : 1;
	}
} 

