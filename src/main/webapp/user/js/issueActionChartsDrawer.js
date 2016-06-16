/**
 * JIRA 이슈 조치율에 대해서 데이터를 받아 그래프를 생성해주는 자바스크립트
 */

// 색이 채워진 꺾은 선 그래프
var graphType = "area";

// 막대 그래프
//var graphType = "column";


/**
 * GO data를 받아 stacked bar 차트 출력하는 함수
 * 
 * @param receivedProject
 *            상품 정보
 * @param receivedVersion
 *            버전 정보
 */
function drawIssueActions(receivedProject, receivedVersion) {
	
	var projectAndVersionData = { "product" : receivedProject, "version" : receivedVersion };
	
	// 표 생성시, start 할 시점
	var startPosition = 0;
	
	var url = "";
	
	if (receivedProject == "GO") {
		url = "getDOIssueMeasure.do";
	} else if (receivedProject == "TMSE") {
		url = "getTMSEIssueMeasure.do";
	}
		
	// JIRA 조치율 그래프에 그릴 데이터들을 배열로 저장
	var actionsIssue = []; // 조치
	var inActionsIssue = []; // 미조치
	
	$.ajax({
		url : url,
		type : 'POST',
		dataType : "json",
		data : projectAndVersionData,
		success : function(actionChartData) {
			
			$('#dvSelectTable').show();
	
			var data = $.parseJSON(actionChartData);
			
			data.sort(dataSort('날짜')); // data를 sorting
			
			var tableData = $('#tableType').val();
	
			var listLength = data.length;

			var issueDate = "";
			var actions = "";
			var inActions = "";
			
			for (var i = 0; i < listLength; i++) {
	
				// 각각의 데이터들을 찾아 저장
				issueDate = data[i].날짜;
				actions = data[i].조치.TOTAL;
				inActions = data[i].미조치.TOTAL;
	
				// 날짜와 이슈의 갯수를 함께 배열로 저장.
				actionsIssue.push([ issueDate, parseInt(actions) ]);
				inActionsIssue.push([ issueDate, parseInt(inActions) ]);
	
			}
	
			/**
			 * 저장한 데이터들을 가지고 차트를 그려주는 함수 chartContainer에 highcharts.js를
			 * 이용해 차트를 그려준다.
			 */
			// 막대 그래프
			if(graphType == 'column') {
			$(function() {
				$('#chartContainer').highcharts( {
						chart : {
							type : graphType
						},
						title : {
							text : $('#project option:selected').text()+ ' JIRA 이슈',
							x : -20
						},
						subtitle : {
							text : $('#version option:selected').val()+ ' 이슈 조치율',
							x : -20
						},
						xAxis : {
							categories : [],
							type : 'datetime'
						},
						yAxis : {
							min : 0,
							max : 100,
							title : {
								text : '퍼센트 (%)'
							},
							stackLabels: {
				                enabled: true,
				                style: {
				                    fontWeight: 'bold',
				                    color: 'gray'
				                }
				            }
						},
						legend : {
							reversed : true
						},
						plotOptions : {
							series : {
								stacking : 'percent',
								dataLabels: {
				                    enabled: true,
				                    color: 'white',
				                    style: {
				                        textShadow: '0 0 3px black'
				                    },
					                formatter: function () {
					                    return Math.round(this.percentage*10)/10 +" %";
					                }
								}
							}
						},
						tooltip : {
							headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
							pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
								+ '<td style="padding:0"><b>{point.y} 개</b> ({point.percentage:.0f}%)</td></tr>',
							footerFormat : '</table>',
							shared : true,
							enabled : true,
							useHTML : true
						},
						series : [ {
							name : '미조치',
							data : inActionsIssue,
							color : '#dddddd'
						}, {
							name : '조치',
							data : actionsIssue
						} ]
					});
				});
			} 
			// 꺾은 선 그래프
			else if(graphType == 'area') {
				$(function() {
					$('#chartContainer').highcharts( {
						
						chart : {
							type : graphType
						},
						title : {
							text : $('#project option:selected').text()+ ' JIRA 이슈',
							x : -20
						},
						subtitle : {
							text : $('#version option:selected').val()+ ' 이슈 조치율',
							x : -20
						},
						xAxis : {
							categories : [],
							type : 'datetime'
						},
						yAxis : {
							min : 0,
							max : 100,
							title : {
								text : '퍼센트 (%)'
							}
						},
						legend : {
							reversed : true
						},
						plotOptions : {
							area: {
				                stacking: 'percent',
				                lineColor: '#666666',
				                lineWidth: 1,
				                marker: {
				                    lineWidth: 1,
				                    lineColor: '#666666'
				                }
				            }
						},
						tooltip : {
							headerFormat : '<span style="font-size:10px">{point.key}</span><table>',
							pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
									+ '<td style="padding:0"><b>{point.y} 개</b> ({point.percentage:.0f}%)</td></tr>',
							footerFormat : '</table>',
							shared : true,
							enabled : true,
							useHTML : true
						},
						series : [ {
							name : '미조치',
							data : inActionsIssue,
							color : '#dddddd'
						},
						{
							name : '조치',
							data : actionsIssue
						} ]
					});
				});
			}
			if(tableData == 'allData') {
				startPosition = 0;
			}
			else if(tableData == 'lastestData') {
				startPosition = listLength-1;
			}
			drawTable(receivedProject, data, listLength, startPosition);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("chartData 에러 발생 \n" + textStatus + " : " + errorThrown);
		}
	});

}

/**
 * 데이터를 sorting하는 함수
 */
function dataSort(name) {
	return function(a, b) {
		if ( a[name] == b[name] ) return 0;
		return a[name] < b[name] ? -1 : 1;
	}
}