/**
 * This software is protected by copyright laws and international copyright treaties.
 * The ownership and intellectual property rights of this software belong to the @autoCoding.
 * Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
 * and may result in legal consequences.
 * This software is licensed to the user and must be used in accordance with the terms of the license.
 * Under no circumstances should the source code or design of this software be disclosed or leaked.
 * The @autoCoding shall not be liable for any loss or damages.
 * Please read the license and usage permissions carefully before using.
 */

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;
var selectedFiles = [];

$(document).ready(function() {
	
	$('#sendEmailButton').on('click', sendEmail);
	
	// 카드 헤더 클릭시 바디 토글
	$('.card-header').on('click', handleCollapseClick);
	
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

function displayFileList(fileList) {
    var listContainer = $('#fileList');
    listContainer.empty(); // 기존 목록 초기화
    selectedFiles = []; // 선택된 파일 배열 초기화

    for (var i = 0; i < fileList.length; i++) {
        var fileName = fileList[i].name;
        selectedFiles.push(fileList[i]); // 선택된 파일 배열에 추가

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

function sendEmail(event) {
	event.preventDefault();
	
	showLoadingBar();
	
    var files = $('#fileInput').prop('files');

    if (files.length === 0) {
        alert('Please select files to upload.');
        hideLoadingBar();
        return;
    }

    var formData = new FormData();
    for (var i = 0; i < files.length; i++) {
      formData.append('files', files[i]);
    }
	
    // 추가 필드 추가
    formData.append('to', document.getElementById('to').value);
    formData.append('subject', document.getElementById('subject').value);
    formData.append('body', document.getElementById('body').value);

    // formData를 서버로 전송하는 코드를 작성하세요
    // 예: jQuery를 사용하는 경우 아래와 같이 사용할 수 있습니다.
    $.ajax({
      url: '/sendEmail',
      type: 'POST',
	  beforeSend: function(xhr) {
		  xhr.setRequestHeader(csrfheader, csrftoken);
	  },       
      data: formData,
      processData: false,
      contentType: false,
      success: emailCallback,
      error: function (error) {
    	  // 에러 처리
    	  hideLoadingBar();
      },
    });
	
	//callAjax("/sendEmail", "POST", data, emailCallback);
	
}

function emailCallback(response){
	hideLoadingBar();
	
	swal({
		title: "Success",
		text: response.data,
		icon: "success",
		button: "OK",
	});
	
    // 이메일 입력 필드 초기화
    document.getElementById("to").value = "";
    document.getElementById("subject").value = "";
    document.getElementById("body").value = "";
    
}

