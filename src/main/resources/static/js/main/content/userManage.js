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

$(document).ready(function() {
	debugger;
	initializeUserManageTable();

	$('#userManageForm').on('submit', function(event) {
		event.preventDefault();
		debugger;
		var formData = new FormData(this);
		
		const fileInput = $('#formFile')[0];
		const file = fileInput.files[0];
		if (file) {
			const resizedImage = compressImage(file, 50, 1);
			formData.append('file', resizedImage);
		}
		
		saveUserManage(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#userManageForm input').val('');
		$('#picture-preview').attr('src', '');
		$('#picture-preview').hide();
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
	
	// 사진 선택 시 미리보기 업데이트
	$('#formFile').change(function() {
	  var reader = new FileReader();
	  reader.onload = function(e) {
	    $('#picture-preview').attr('src', e.target.result).show();
	  };
	  reader.readAsDataURL(this.files[0]);
	});	
});
function initializeUserManageTable() {
	table = $('#userManageTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/userManage/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'username' },
			{ data: 'email' },
			{ data: 'role' },
			{ data: 'picture' },
			{ data: 'enable' },
			{ data: 'lastLoginDt' },
/*            { data: 'lastLoginDt',
	            "render": function(data) {
	            	return moment(data, "YYYY-MM-DDTHH:mm:ss").format("YYYY년 MM월 DD일 HH:mm");
	            }
            },	*/
			{
				data: null,
				render: function(data, type, row, meta) {
					var editButton = '<button type="button" class="btn btn-sm btn-outline-info mx-1 edit-button" data-username="' + row.username + '"><i class="fas fa-edit"></i></button>';
					return editButton;
				},
			},
			{
				data: null,
				render: function(data, type, row, meta) {
					var deleteButton = '<button type="button" class="btn btn-sm btn-outline-danger mx-1 delete-button" data-username="' + row.username + '"><i class="fas fa-trash"></i></button>';
					return deleteButton;
				},
			},
		],
		order: [], // 자동 정렬 비활성화
	});

	$('#userManageTable tbody').on('click', '.edit-button', function() {
		var username = $(this).data('username');
		editUserManage(username);
	});

	$('#userManageTable tbody').on('click', '.delete-button', function() {
		var username = $(this).data('username');
		deleteUserManage(username);
	});

	$('#userManageTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

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
        	debugger;
            alert(response.responseText);
        }
    });
    
	//callAjax("/userManage/save", "POST", formData, saveCallback);
}

function editUserManage(username) {
	callAjax("/userManage/list/"+ username, "POST", null, editCallback);
}

function deleteUserManage(username) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/userManage/"+ username, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#username').val(response.data.username);
	//$('#password').val(response.data.password);
	$('#email').val(response.data.email);
	$('#role').val(response.data.role);
	
	// 응답 데이터에서 이미지 URL을 가져와서 파일 업로드 필드에 미리보기 추가
	var pictureUrl = response.data.picture; // 응답 데이터에서 이미지 URL 가져오기
	var $picturePreview = $('#picture-preview'); // 미리보기 이미지가 표시될 엘리먼트 선택
	// 이미지 URL이 유효한 경우에만 미리보기 표시
	if (pictureUrl) {
	  $picturePreview.attr('src', pictureUrl);
	  $picturePreview.show();
	}
	
	$('#enable').prop('checked', response.data.enable);

	$('#userManageForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#userManageForm input').val('');
	$('#userManageTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#userManageTable tbody').find('tr[data-username="' + username + '"]').remove();
	swal({
		title: "Success",
		text: "userManage deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#userManageTable').DataTable().ajax.reload();
}

