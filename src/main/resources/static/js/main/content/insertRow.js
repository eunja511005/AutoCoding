/**
 * This software is protected by copyright laws and international copyright
 * treaties. The ownership and intellectual property rights of this software
 * belong to the developer. Unauthorized reproduction, distribution,
 * modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences. This software is licensed to the user
 * and must be used in accordance with the terms of the license. Under no
 * circumstances should the source code or design of this software be disclosed
 * or leaked. The developer shall not be liable for any loss or damages. Please
 * read the license and usage permissions carefully before using.
 */

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;
var newRowId = 0; // 새로 추가된 행의 ID를 관리하기 위한 변수
var rowDataArray = []; //서버에서 받아온 데이터와 임시로 추가된 데이터를 저장할 배열 변수
var deletedRowData = [];
var columnDefinitions;

$(document).ready(function() {
	debugger;
	
	// 컬럼 정보를 별도의 변수로 정의
	columnDefinitions = [
		{ data: null, name: "", searchable: true, orderable: false, search: { value: "", regex: false } },
		{ data: "no", name: "", searchable: true, orderable: false, search: { value: "", regex: false } },
	    { data: "BIZPLC_NM", name: "", searchable: true, orderable: true, search: { value: "", regex: false } },
	    { data: "BSN_STATE_NM", name: "", searchable: true, orderable: true, search: { value: "", regex: false } },
	    { data: "REFINE_ROADNM_ADDR", name: "", searchable: true, orderable: true, search: { value: "", regex: false } }
	];

	
	// 테이블 초기화
	initSearchCondition();

	// 버튼 클릭 이벤트 등록
	$('#addRowButton').on('click', addRow);
	$('#editRowButton').on('click', editRow);
	$('#deleteRowsButton').on('click', deleteRows);
	$('#saveRowsButton').on('click', saveRows);
	$('#selectAllCheckBox').on('click', toggleAllCheckbox);
	$('#dataTable').on('blur', ':input:not(:checkbox)', temporarySave);
	$('#searchButton').on('click', function(event) {
		event.preventDefault();
		searchDataTable();
	});
	
	// 카드 헤더 클릭시 바디 토글
	$('.card-header').on('click', handleCollapseClick);
	
});

async function initSearchCondition(){
	await initSelectBox('sigunCd', '/commonCode/SIGUNCD', false, ['41115']);
	searchDataTable(); // 검색 조건을 초기화하고 나서 데이터를 조회합니다.
}

function searchDataTable() {
    $.ajax({
        url: '/tourismStaying/list',
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfheader, csrftoken);
        },
        data: getSearchParams(), // getSearchParams 함수로 검색 조건 생성
        success: function(response) {
            // 서버에서 받아온 데이터를 rowDataArray에 저장
            rowDataArray = response.data;
            
            // 'editing' 속성을 각 행 데이터 객체에 추가하고 초기 값으로 'false'를 설정합니다.
            rowDataArray.forEach(function(rowData) {
                rowData.editing = false;
            });
            
            // 테이블 다시 그리기
            drawTable();
        },
        error: function(error) {
            console.error('Error fetching data from server:', error);
        }
    });
}

//화면을 다시 그리는 함수
function drawTable() {
    // 테이블 초기화
    if ($.fn.DataTable.isDataTable('#dataTable')) {
    	table.destroy();
    }
    
    // 저장된 임시 데이터를 테이블에 그립니다.
    table = $('#dataTable').DataTable({
        "responsive": true,
        "serverSide": false, // [중요] 서버에서 조회해 오는거 아니므로 false라고 해야 오류 안남
        "processing": true,
        "searching": false,
        scrollX: true,
        data: rowDataArray, // 임시 데이터를 테이블에 바로 연결합니다.
        columns: getColumnData(columnDefinitions),
        order: [0, 'desc'],
        columnDefs: [
            {
                // This column is the first one with checkboxes
                targets: 0,
                orderable: false,
                className: 'dt-body-center',
                render: function (data, type, row, meta) {
                    // Add a hidden input field with the ID value for each row
                	return '<input type="hidden" class="row-id" value="' + row.id + '"><input type="checkbox" class="form-check-input" ' + (row.checkboxValue ? 'checked' : '') + '>';
                }
            },
            // 각 데이터 필드에 input 필드 추가 (숨겨둠)
            {
                targets: [1],
                render: function(data, type, row, meta) {
                	return '<span>' + (meta.row + 1) + '</span>';
                }
            },            
            {
            	targets: [2, 3, 4],
            	render: function(data, type, row, meta) {
            		if (row.editing) {
            			// Render editable input fields when the row is in edit mode
            			return '<input type="text" class="form-control" value="' + data + '">';
            		} else {
            			// Render non-editable input fields for other rows
            			return '<span>' + data + '</span>';
            		}
            	}
            },            
        ]
    });
}

//컬럼 정보를 기반으로 컬럼 데이터를 동적으로 생성하는 함수
function getColumnData(columnDefinitions) {
    var columns = [];
    columnDefinitions.forEach(function(definition) {
        var columnData = {
            data: definition.data,
            name: definition.name,
            searchable: definition.searchable,
            orderable: definition.orderable,
            search: {
                value: "", // 기본 검색 값은 빈 문자열로 설정
                regex: false,
            },
        };
        columns.push(columnData);
    });
    return columns;
}

function getSearchParams() {
    var sigunCd = document.getElementById("sigunCd").value;

    var searchParams = {
        sigunCd: { value: sigunCd, regex: false },
    };

    var orderColumnIndex = 0; // 기본값으로 0을 사용하거나 적절한 값으로 설정해야 함
    var orderDirection = "desc"; // 기본값으로 "desc"를 사용하거나 적절한 값으로 설정해야 함

    var d = {
        draw: 1,
        columns: getColumnData(columnDefinitions), // getColumnData 함수로 동적으로 컬럼 정보 생성
        order: [
            {
                column: orderColumnIndex,
                dir: orderDirection,
            },
        ],
        search: searchParams,
        start: 0,
        length: 10, // 적절한 페이지당 데이터 개수로 설정해주세요
    };

    return JSON.stringify(d);
}

function addRow(event) {
    event.stopPropagation();

    var newRowData = {
        id: 'new_' + newRowId++, // 새로운 행에 고유한 ID 부여
        no:'',
        BIZPLC_NM: '', // 빈 값으로 초기화
        BSN_STATE_NM: '', // 빈 값으로 초기화
        REFINE_ROADNM_ADDR: '', // 빈 값으로 초기화
        editing: true, // 새로운 행은 편집 모드로 설정
        checkboxValue: true,
    };

    // 새로운 행의 데이터를 배열의 맨 뒤에 추가합니다.
    //rowDataArray.push(newRowData);
    rowDataArray.unshift(newRowData);
    // 화면을 다시 그려줍니다.
    drawTable();
}

function temporarySave() {
	  var cell = $(this).closest('td');
	  var row = cell.parent();
	  var rowId = row.index();

	  var rowData = rowDataArray[rowId] || {}; // 기존 데이터 가져오거나 빈 객체 생성

	  // columnDefinitions에 따라 각 열의 데이터를 추출하여 rowData 객체에 업데이트
	  $.each(columnDefinitions, function(index, column) {
	    var columnName = column.data;
	    var cellData = row.find('td').eq(index).find(':input').val();
	    
	    
	    if (columnName !== null && typeof cellData !== 'undefined') {
	    	rowData[columnName] = cellData;
	    }
	    
	  });

}


function editRow(event) {
    event.stopPropagation();

    var selectedRowsData = [];

    $('#dataTable tbody input[type="checkbox"]').each(function () {
        var checkbox = $(this);
        var row = checkbox.closest('tr');
        var rowData = table.row(row).data();

        if (checkbox.prop('checked')) {
            // checkbox가 체크된 경우 editing 모드를 토글합니다.
            rowData.editing = !rowData.editing;
            selectedRowsData.push(rowData);
        } 

        // editing 모드에 따라 checkboxValue를 업데이트합니다.
        rowData.checkboxValue = rowData.editing;

    });

    // 선택된 행이 없는 경우
    if (selectedRowsData.length === 0) {
        swal({
            title: alertTitle,
            text: noRowSelectedMessage,
            icon: "warning",
            button: "OK",
        });
        return;
    }

    var currentPage = table.page.info().page;
    
    // 변경된 editing 상태를 반영하여 테이블을 다시 그립니다.
    table.rows().invalidate().draw();
    
    goToPage(currentPage);
}


// 선택된 행 삭제 함수
function deleteRows(event) {
	debugger;
	event.stopPropagation();
	
	swal({
		  title: "Delete Confirmation",
		  text: "Are you sure you want to delete it?",
		  icon: "warning",
		  buttons: true,
		  dangerMode: true,
		})
		.then((willDelete) => {
		  if (willDelete) {
			  
			var selectedRowsData = [];
			  
		    $('#dataTable tbody input[type="checkbox"]').each(function () {
		        var checkbox = $(this);
		        var row = checkbox.closest('tr');
		        var rowData = table.row(row).data();
		        var rowIndex = table.page.info().start + row.index();

		        if (checkbox.prop('checked')) {
		            selectedRowsData.push(rowData);
		            deletedRowData.push(rowData);
		            
		            table.row(rowIndex).remove().draw();
		            
		            rowDataArray.splice(rowIndex, 1);
		        } 

		        // editing 모드에 따라 checkboxValue를 업데이트합니다.
		        rowData.checkboxValue = rowData.editing;

		    });
		    
		    // 선택된 행이 없는 경우
		    if (selectedRowsData.length === 0) {
		        swal({
		            title: alertTitle,
		            text: oneRowSelectedMessage,
		            icon: "warning",
		            button: "OK",
		        });
		        return;
		    }  		    
		    
		    
		   /* if (deletedRowData.length > 0) {
		    	showLoadingBar();
		    	callAjax("/palceOrder", "DELETE", deletedRowData, deleteTableCallback);
		    }*/
		    drawTable();

		  }else{
			  console.log("Deletion canceled.");
		  }
  	});
	
}

function deleteTableCallback(response){
/*	deletedRowData = [];
	initSearchCondition();
	
    swal({
        title: "Delete",
        text: response.errorMessage,
        icon: "success",
        button: "OK",
    });	
	
	hideLoadingBar();
*/	
}

function saveRows(event) {
    event.stopPropagation();

    //showLoadingBar();
    
	var selectedRowsData = [];
	  
    $('#dataTable tbody input[type="checkbox"]').each(function () {
        var checkbox = $(this);
        var row = checkbox.closest('tr');
        var rowData = table.row(row).data();

        if (checkbox.prop('checked')) {
            selectedRowsData.push(rowData);
        } 

    });
    
    // 선택된 행이 없는 경우
    if (selectedRowsData.length === 0) {
        swal({
            title: alertTitle,
            text: oneRowSelectedMessage,
            icon: "warning",
            button: "OK",
        });
        
        //hideLoadingBar();
        
        return;
    }  	

  //callAjax("/palceOrder/save", "POST", selectedRowsData, deleteTableCallback);
}

function goToPage(pageNumber){
	table.page(pageNumber).draw('page');
}

function toggleAllCheckbox(){
	const isChecked = $(this).prop('checked');
	$('#dataTable tbody input[type="checkbox"]').prop('checked', isChecked);
}