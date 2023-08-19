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
var showButton = true; // 이 부분을 원하는 조건에 따라 true 또는 false로 변경하세요.

$(document).ready(function() {
	debugger;
	
	$('.role-button').on('click', function(event) {
	    event.preventDefault();
	    $('#uploadForm').trigger('reset');
	    var role = $(this).data('role');
	    openModalAndModifyContent(role);
	    $(this).blur(); // 버튼 클릭 후 포커스 해제
	});
    
	$('#applicationModal').on('hidden.bs.modal', function () {
	    $(this).find('form')[0].reset(); // 폼 요소 클리어
	    $(this).find('#frame').attr('src', ''); // 이미지 클리어
	}); 
    
    //$('#applicationModal .modal-footer button.btn-primary').on('click', function(event) {	
    //$('#uploadForm').on('submit', function(event) {	
    $('#submitBtn').on('click', function(event) {
    	
    	showLoadingBar();
    	
        var form = $('#uploadForm')[0];

        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
            form.classList.add('was-validated');
            hideLoadingBar();
            return;
        }

        event.preventDefault(); // Prevent the default submit behavior
        form.classList.add('was-validated');

        const fileInput = $('#formFile')[0];
        const file = fileInput.files[0];
        if (file) {
            const resizedImage = compressImage(file, 50, 1);
            
            var formData = new FormData(form);
            formData.append('file', resizedImage);
        }

        saveUserManage(formData);
    });
  

	// 카드 헤더 클릭시 바디 토글
	$('.card-header').on('click', handleCollapseClick);
	
});

function openModalAndModifyContent(role) {
    $('#roleName').text(role);

    if (role === 'designer') {
        $('#applicationModalLabel').html(designer);
        $('#applicationModalDescription').html(descriptionDesigner);
    } else if (role === 'publisher') {
        $('#applicationModalLabel').html(publisher);
        $('#applicationModalDescription').html(descriptionPublisher);
    } else if (role === 'frontendDeveloper') {
        $('#applicationModalLabel').html(frontendDeveloper);
        $('#applicationModalDescription').html(descriptionFrontendDeveloper);
    } else if (role === 'backendDeveloper') {
        $('#applicationModalLabel').html(backendDeveloper);
        $('#applicationModalDescription').html(descriptionBackendDeveloper);
    } else if (role === 'databaseAdmin') {
        $('#applicationModalLabel').html(databaseAdmin);
        $('#applicationModalDescription').html(descriptionDatabaseAdmin);
    } else if (role === 'systemAdmin') {
        $('#applicationModalLabel').html(systemAdmin);
        $('#applicationModalDescription').html(descriptionSystemAdmin);
    }

    $('#applicationModal').modal('show');
}

function preview() {
    var fileInput = document.getElementById('formFile');
    var frame = document.getElementById('frame');

    if (fileInput.files && fileInput.files[0]) {
        var reader = new FileReader();

        reader.onload = function(e) {
            frame.src = e.target.result;
        }

        reader.readAsDataURL(fileInput.files[0]);
    }
}

function saveUserManage(formData) {
    $.ajax({
        url: '/userManage/save',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        beforeSend : function(xhr){   
			xhr.setRequestHeader(csrfheader, csrftoken);
        },
        success: function(response) {
        	saveCallback(response);
        },
        error: function(response) {
        	hideLoadingBar();
            alert(response.responseText);
        }
    });
}

function saveCallback(response){
	hideLoadingBar();
	
	swal({
		title: "Success",
		text: "saved successfully.",
		icon: "success",
		button: "OK",
	})
	$('#applicationModal').modal('hide');
}