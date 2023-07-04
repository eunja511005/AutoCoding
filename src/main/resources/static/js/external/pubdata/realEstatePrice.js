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
	
	$('#saveButton').on('click', saveData);
	
    // 파일 업로드 버튼 클릭 이벤트 추가
    $('#uploadButton').on('click', uploadFiles);

    // 파일 선택시 선택한 파일 리스트 제목 보여주기
    $('#fileInput').on('change', function(event) {
        var fileList = event.target.files;
        displayFileList(fileList);
    });

    // 파일 삭제 버튼 클릭 이벤트 처리
    $(document).on('click', '.delete-button', function() {
        var index = $(this).data('index');
        deleteFile(index);
    });
    
});

async function initSearchCondition(){
	await initSelectBox('lawCode', '/commonCode/LAWCODE', false, ['41115']);
	
	var searchMonthInput = $('#searchMonth');
    setInitialMonthValue(searchMonthInput);
}

function displayFileList(fileList) {
    var listContainer = $('#fileList');
    listContainer.empty(); // 기존 목록 초기화
    //selectedFiles = []; // 선택된 파일 배열 초기화

    for (var i = 0; i < fileList.length; i++) {
        var fileName = fileList[i].name;
        //selectedFiles.push(fileList[i]); // 선택된 파일 배열에 추가

        var listItem = $('<li>', {
            class: 'list-group-item',
            'data-index': i // 파일의 인덱스를 data 속성으로 저장
        });

        var fileNameSpan = $('<span>', {
            text: fileName
        });

        var deleteButton = $('<button>', {
            text: 'Delete',
            class: 'btn btn-danger btn-sm float-end delete-button',
            'data-index': i // 삭제 버튼의 인덱스를 data 속성으로 저장
        });

        listItem.append(fileNameSpan);
        listItem.append(deleteButton);
        listContainer.append(listItem);
    }
}

function deleteFile(index) {
    selectedFiles.splice(index, 1); // 선택된 파일 배열에서 해당 인덱스의 파일 삭제
    $('[data-index="' + index + '"]').remove(); // 해당 파일 목록 항목 삭제
}

function uploadFiles(event) {
	event.preventDefault();
	
    var files = $('#fileInput').prop('files');

    if (files.length === 0) {
        alert('Please select files to upload.');
        return;
    }

    var formData = new FormData();
    for (var i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }
    
    $.ajax({
    	type: 'POST',
        url: '/layout/uploadFile',
	    beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
		},        
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {

            // 파일 경로를 추출하여 화면에 표시
            var fileNames = response.fileNames;
            var filePaths = response.filePaths;
            var fileListContainer = $('#fileListContainer');
            fileListContainer.empty();

            if (fileNames.length > 0) {
                var fileList = $('<ul>', {
                    class: 'list-group'
                });

                for (var i = 0; i < fileNames.length; i++) {
                    var fileName = fileNames[i];
                    var filePath = filePaths[i];

                    var listItem = $('<li>', {
                        class: 'list-group-item'
                    });

                    var fileIcon = $('<i>', {
                        class: 'fas fa-file me-2'
                    });

                    var fileNameElement = $('<span>', {
                        text: fileName
                    });

                    listItem.append(fileIcon);
                    listItem.append(fileNameElement);

                    listItem.click(function() {
                        var filePath = filePaths[$(this).index()];
                        window.open(filePath); // 파일 열기
                    });

                    fileList.append(listItem);
                }


                fileListContainer.append(fileList);
            } else {
                fileListContainer.append('<p>No files uploaded.</p>');
            }

            // 파일 입력과 파일 목록 초기화
            $('#fileInput').val('');
        	
        },
        error: function(xhr, status, error) {
          // Handle error response
          console.error('Upload error', error);
        }
      });
    
    // callAjax("/layout/uploadFile", "POST", formData, uploadCallback);
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
			url: '/realEstatePrice/list',
			"type": "POST",
			contentType : 'application/json; charset=utf-8',
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
            "data": function (d) {
            	
            // ----------------- search 값 가져 오기 시작 -----------------
                var lawCode = document.getElementById("lawCode").value;
                var searchMonth = document.getElementById("searchMonth").value.replace(/-/g, '');
            // ----------------- search 값 가져 오기 종료 -----------------
            	
            	var searchParams = {
            		lawCode        : {value : lawCode, regex : false},	
            		searchMonth    : {value : searchMonth, regex : false},	
            	};
            	
                d.search = searchParams;
                
                var orderColumnIndex = d.order[0].column;
                d.orderColumnName = d.columns[orderColumnIndex].data;
                d.orderDirection = d.order[0].dir.toUpperCase();
                
                return JSON.stringify(d);
            },
/*            success: function(response) {
                if (response.success !== undefined && !response.success) {
                  swal("Error", response.errorMessage, "error");
                  $('.dataTables_processing').hide();
                }
            },*/
		    error: function(xhr, status, error) {
		        var errorMessage = getMessage("dataTableAjaxCallError");
		        swal({
		            icon: 'error',
		            title: errorMessage,
		            text: error
		        });
		        $('.dataTables_processing').hide();
		    }
		},
		columns: [
			{ data: '거래금액' },
			{ data: '건축년도' },
	        {
	            data: '법정동',
	            render: function (data, type, row, meta) {
	                return '<span title="' + data + '">' + data + '</span>';
	            }
	        },			
	        {
	            data: '아파트',
	            render: function (data, type, row, meta) {
	                return '<span title="' + data + '">' + data + '</span>';
	            }
	        },				
			{ data: '전용면적' },
			{ data: '층' },
		],
		columnDefs: [
		    { "orderable": false, "targets": [1, 2, 3, 5] }, // 1, 2, 3, 6, 7번째 컬럼은 정렬 불가능하도록 설정
		    { "orderable": true, "targets": [0, 4] } // 0번째와 5번째 컬럼은 정렬 가능하도록 설정
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

//탭 생성 함수
function createTab(tabId, tabTitle, tabContent) {
    // 이미 해당 탭이 생성되어 있는지 확인
    var existingTab = $('#' + tabId);
    if (existingTab.length > 0) {
      // 이미 생성된 탭이 있는 경우 해당 탭으로 이동
      $('.nav-tabs .nav-link').removeClass('active');
      $('.tab-content .tab-pane').removeClass('show active');
      existingTab.addClass('show active');
      $('#header' + tabId).addClass('active');
      return;
    }
  
    // 탭 제목에 사용될 버튼 HTML 생성
    var closeButton = $('<button></button>')
      .addClass('close close-tab-btn')
      .attr('type', 'button')
      .attr('aria-label', 'Close')
      .html('<span aria-hidden="true">&times;</span>')
      .on('click', function() {
        // 닫기 버튼 클릭 이벤트 처리
        var currentTab = $('#' + tabId);
        var prevTab = currentTab.prev('.tab-pane');
        currentTab.remove(); // 현재 탭 컨텐츠 제거
        $('#header' + tabId).parent().remove(); // 탭 제목 제거
  
        if (prevTab.length > 0) {
          // 이전 탭 컨텐츠 보이기
          prevTab.addClass('show active');
          var prevTabId = prevTab.attr('id');
          $('#header' + prevTabId).addClass('active');
        }
      });
  
    // 탭 제목과 닫기 버튼을 포함한 HTML 생성
    var tabTitleHtml = $('<li></li>')
      .addClass('nav-item')
      .append(
        $('<button></button>')
          .addClass('nav-link')
          .attr('id', 'header' + tabId)
          .attr('data-bs-toggle', 'tab')
          .attr('data-bs-target', '#' + tabId)
          .attr('type', 'button')
          .attr('role', 'tab')
          .attr('aria-controls', tabId)
          .attr('aria-selected', 'false')
          .text(tabTitle)
          .append(closeButton) // 닫기 버튼 추가
      );
  
    // 탭 컨텐츠 생성
    var tabContentHtml = $('<div></div>')
      .addClass('tab-pane fade')
      .attr('id', tabId)
      .attr('role', 'tabpanel')
      .attr('aria-labelledby', 'header' + tabId)
      .append($('<h5></h5>').text(tabTitle))
      //.append($('<p></p>').text(tabContent));
      //.append($('<textarea id="longtext_t"></textarea>').text(tabContent)); // Summernote를 초기화하고 tabContent를 설정합니다.
      //.append($('<div class="summernote-container"></div>').append($('<textarea></textarea>').attr('id', 'longtext_t').text(tabContent)));
      .append($('<div></div>').attr('id', 'summernote_' + tabId)); // Summernote를 초기화할 div 요소 추가
      
    // 탭 제목과 컨텐츠를 추가
    $('.nav-tabs').append(tabTitleHtml);
    $('.tab-content').append(tabContentHtml);
  
    // 생성된 탭 활성화
    $('.nav-tabs .nav-link').removeClass('active'); // 모든 탭 제목의 'active' 클래스 제거
    $('.tab-content .tab-pane').removeClass('show active'); // 모든 탭 컨텐츠 숨기기
    $('#' + tabId).addClass('show active');
    $('#header' + tabId).addClass('active');
    
    // Summernote 초기화
    $('#summernote_' + tabId).summernote({
    	  toolbar: false, // 툴바 비활성화
    });
    
    // 탭 컨텐츠에 tabContent 내용 써머노트에 설정
    $('#summernote_' + tabId).summernote("disable");
    $('#summernote_' + tabId).summernote('code', tabContent);
}

function handleCollapseClick() {
	var $cardBody = $(this).parents('.card').find('.card-body');
	$cardBody.slideToggle();
}

function saveData(event) {
	var form = $('#saveForm')[0];
	
	if (!validateAndSave(form, event)) {
		return;
	}
	
	var formData = new FormData(form);
	  
	// 체크 박스의 값을 폼 데이터에 추가
	formData.append('chk', $("#chk_m").is(':checked') ? "Y" : "N");

	var json = {};
	formData.forEach(function(value, key) {
	    if (key === 'radioGroup_m') {
	      var selectedRadio = $("input[name='radioGroup_m']:checked").val();
	      json['radio'] = selectedRadio;
	    } else {
	      json[key] = value;
	    }
	});

	var data = JSON.stringify(json);
	  
	callAjax("/layout/save", "POST", data, saveCallback);
}

function editData(id) {
	callAjax("/layout/list/"+ id, "POST", null, editCallback);
}

function deleteData(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/layout/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id_m').val(response.data.id);
	$('#email_m').val(response.data.email);
	$('#num_m').val(response.data.num);
	$('#shorttext_m').val(response.data.shorttext);
	$('#longtext_m').summernote('code', response.data.longtext);
	
	var chkBox = $("#chk_m");
	chkBox.prop("checked", (response.data.chk === "Y"));
	
	$('input[name="radioGroup_m"]').filter('[value="' + response.data.radio + '"]').prop('checked', true);
	
	var multiArray = [];
	multiArray.push(response.data.multibox);
	
	var selectArray = [];
	selectArray.push(response.data.selectbox);
	
    initSelectBox('multibox_m', '/commonCode/VISIBILITY', false, multiArray);
	initSelectBox('selectbox_m', '/commonCode/GENDER', true, selectArray);

    // 모달 창을 띄움
    $('#addModal').modal('show');
}

function saveCallback(response){
	inputClear('#saveForm');
	// Close the modal
	var modal = $('#addModal');
	var modalInstance = bootstrap.Modal.getInstance(modal);
	modalInstance.hide();
	
	table.ajax.reload();
}

function inputClear(form) {
	$(form).find('input[type="text"]').val('');
	$(form).find('input[type="number"]').val('');
	$(form).find('input[type="email"]').val('');
}

function deleteCallback(response){
	$('#errorHistTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "data deleted successfully.",
		icon: "success",
		button: "OK",
	})
	table.ajax.reload();
}

function uploadCallback(response){
	swal({
		title: "Success",
		text: "upload successfully."+response.createdFilePath,
		icon: "success",
		button: "OK",
	})
}

function onImageUpload(files) {
	  for (var i = 0; i < files.length; i++) {
	    // 이미지 파일을 압축합니다. (압축: 70% 수준 유지)
	    compressImage(files[i], 300, 0.7).then(function (compressedImage) {
	      // 압축된 이미지 파일을 서버에 업로드합니다.
	      const formData = new FormData();
	      formData.append('file', compressedImage);
	      $.ajax({
	        type: 'POST',
	        url: '/posts/uploadImage',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
	        data: formData,
	        processData: false,
	        contentType: false,
	        success: function (data) {
	          // 서버로부터 이미지 파일의 경로를 받아와 HTML 본문에 삽입합니다.
	          const imgNode = $('<img>').attr('src', data.createdFilePath);
	          $('#longtext_m').summernote('insertNode', imgNode[0]);
	        },
	        error: function () {
	          alert('이미지 업로드에 실패했습니다.');
	        }
	      });
	    });
	  }
}