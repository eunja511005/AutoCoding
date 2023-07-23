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
var columnDefinitions;

$(document).ready(function() {
	debugger;
	
	// 컬럼 정보를 별도의 변수로 정의
	columnDefinitions = [
		{ data: null, name: "", searchable: true, orderable: false, search: { value: "", regex: false } },
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
                targets: [1, 2, 3],
                render: function(data, type, row, meta) {
                    if (row.editing) {
                        // Render editable input fields when the row is in edit mode
                        return '<input type="text" class="form-control" value="' + data + '">';
                    } else {
                        // Render non-editable input fields for other rows
                        return '<input type="text" class="form-control" value="' + data + '" readonly>';
                    }
                }
            }            
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


function editRow(event) {
    event.stopPropagation();

    var selectedRowsData = [];
    var checkedRowCount = 0; 

    $('#dataTable tbody input[type="checkbox"]').each(function () {
        var checkbox = $(this);
        var row = checkbox.closest('tr');
        var rowData = table.row(row).data();

        if (checkbox.prop('checked')) {
            // checkbox가 체크된 경우 editing 모드를 토글합니다.
            rowData.editing = true;
            checkedRowCount++;
        } else {
            // checkbox가 체크되어 있지 않은 경우 editing 모드를 해제합니다.
            rowData.editing = false;
        }

        // editing 모드에 따라 checkboxValue를 업데이트합니다.
        rowData.checkboxValue = rowData.editing;

        selectedRowsData.push(rowData);
    });

    // 선택된 행이 두 개 이상인지 확인합니다.
    if (checkedRowCount > 1) {
        swal({
            title: alertTitle,
            text: multiRowSelectedMessage,
            icon: "warning",
            button: "OK",
        });
        return;
    }

    // 선택된 행이 없는 경우
    if (checkedRowCount === 0) {
        swal({
            title: alertTitle,
            text: noRowSelectedMessage,
            icon: "warning",
            button: "OK",
        });
        return;
    }

    // 변경된 editing 상태를 반영하여 테이블을 다시 그립니다.
    table.rows().invalidate().draw();
}


// 선택된 행 삭제 함수
function deleteRows(event) {
	event.stopPropagation();
	
    var selectedRows = table.rows('.selected').data();

    // 선택된 행이 없으면 삭제할 행이 없다고 알림
    if (selectedRows.length === 0) {
        alert("선택된 행이 없습니다.");
        return;
    }

    // 선택된 행들을 순회하며 삭제
    for (var i = 0; i < selectedRows.length; i++) {
        table.row(selectedRows[i]).remove().draw();
    }
}

function saveRows(event) {
    event.stopPropagation();

    // 사용자가 입력한 값을 가져오기 위해 추가적인 처리가 필요합니다.
    // 새로운 행의 데이터는 DOM에서 직접 가져와야 합니다.

    var selectedRowsData = [];

    $('#dataTable tbody tr').each(function () {
        var row = $(this);
        var checkbox = row.find('input.form-check-input');
        var bizplcNmInput = row.find('input#bizplcNmInput');
        var bsnStateNmInput = row.find('input#bsnStateNmInput');
        var refineRoadnmAddrInput = row.find('input#refineRoadnmAddrInput');

        var newRowData = {
            checkboxValue: checkbox.prop('checked'),
            BIZPLC_NM: bizplcNmInput.val(),
            BSN_STATE_NM: bsnStateNmInput.val(),
            REFINE_ROADNM_ADDR: refineRoadnmAddrInput.val(),
        };

        selectedRowsData.push(newRowData);
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

    // 여기에서 저장 로직을 구현합니다.
    // 선택된 행들의 데이터를 서버로 전송하거나 필요한 작업을 수행합니다.
    // 예시를 위해 콘솔에 로그를 출력하는 것으로 대체하겠습니다.
    for (var i = 0; i < selectedRowsData.length; i++) {
        var data = selectedRowsData[i];
        console.log("checkboxValue:", data.checkboxValue, "BIZPLC_NM:", data.BIZPLC_NM, "BSN_STATE_NM:", data.BSN_STATE_NM, "REFINE_ROADNM_ADDR:", data.REFINE_ROADNM_ADDR);
    }
}