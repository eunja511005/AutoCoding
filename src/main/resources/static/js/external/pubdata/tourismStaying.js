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

$(document).ready(function() {
	debugger;
	initSearchCondition()
	  .then(() => {
	    searchDataTable();
	  })
	  .catch(error => {
	    console.error('셀렉트 박스 초기화 에러:', error);
	});

	// 카드 헤더 클릭시 바디 토글
	$('.card-header').on('click', handleCollapseClick);
	
	$('#searchButton').on('click', function(event) {
		event.preventDefault();
		table.ajax.reload();
	});
	
});

async function initSearchCondition(){
	await initSelectBox('sigunCd', '/commonCode/SIGUNCD', false, ['41115']);
}

function searchDataTable() {
	table = $('#dataTable').DataTable({
		"responsive": true,
        "serverSide": true,
        "processing": true,
        "searching": false, // 검색 기능 비활성화
		scrollX: true, 
		ajax: {
			// async: false, // 동기 호출을 위해 async 속성을 false로 설정, 디폴트는 true
			url: '/tourismStaying/list',
			"type": "POST",
			contentType : 'application/json; charset=utf-8',
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
            "data": function (d) {
            	
            // ----------------- search 값 가져 오기 시작 -----------------
                var sigunCd = document.getElementById("sigunCd").value;
            // ----------------- search 값 가져 오기 종료 -----------------
            	
            	var searchParams = {
            		sigunCd        : {value : sigunCd, regex : false},	
            	};
            	
                d.search = searchParams;
                
                var orderColumnIndex = d.order[0].column;
                d.orderColumnName = d.columns[orderColumnIndex].data;
                d.orderDirection = d.order[0].dir.toUpperCase();
                
                return JSON.stringify(d);
            },
		},
		columns: [
			{ data: 'SIGUN_NM' },
			{ data: 'BIZPLC_NM' },
			{ data: 'BSN_STATE_NM' },
			{ data: 'REFINE_ROADNM_ADDR' },
		],
		columnDefs: [
		    //{ "orderable": false, "targets": [0,1] }, // 1, 2, 3, 6, 7번째 컬럼은 정렬 불가능하도록 설정
		    //{ "orderable": true, "targets": [0,1] } // 0번째와 5번째 컬럼은 정렬 가능하도록 설정
		],
		order: [0, 'desc'], // 자동 정렬 비활성화
	});
	
	$('#addButton').on('click', function(event) {
        // 초기화 필드의 값을 비워줌
		inputClear('#saveForm');
        
        initSelectBox('multibox_m', '/commonCode/VISIBILITY', false);
    	initSelectBox('selectbox_m', '/commonCode/GENDER', true);

        // 모달 창을 띄움
        $('#addModal').modal('show');
	});
	
	$('#longtext_m').summernote({
  	    placeholder: 'Hello Summernote lite',
  	    tabsize: 2,
        height: 300,                 // set editor height
        minHeight: null,             // set minimum height of editor
        maxHeight: null,             // set maximum height of editor
        focus: true,                  // set focus to editable area after
										// initializing summernote
        toolbar: [
  		    ['fontname', ['fontname']],
  		    ['fontsize', ['fontsize']],
  		    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
  		    ['color', ['forecolor','color']],
  		    ['table', ['table']],
  		    ['para', ['ul', 'ol', 'paragraph']],
  		    ['height', ['height']],
  		    ['insert',['picture','link','video']],
  		    ['view', ['codeview','fullscreen', 'help']]
  		  ],		  
		  callbacks: {
		      onImageUpload: onImageUpload
		  },  		  
  		// 추가한 글꼴
  		fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋음체','바탕체'],
  		 // 추가한 폰트사이즈
  		fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72']
    });
	
	$(document).on('focusin', '#longtext_t', function() {
		debugger;
		
	});

	$('#dataTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editData(id);
	});

	$('#dataTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteData(id);
	});

	$('#dataTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
		
		var selectedRowsData = table.row(this).data();		
		
        // 탭을 생성하고 내용을 설정합니다.
        var tabId = selectedRowsData.id;
        var tabTitle = selectedRowsData.shorttext;
        var tabContent = selectedRowsData.longtext;

        createTab(tabId, tabTitle, tabContent);
		
	});
}