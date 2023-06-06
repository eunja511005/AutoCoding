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
	initializeUserRequestHistoryTable();

	$('#userRequestHistoryForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveUserRequestHistory(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#userRequestHistoryForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#reqField, #resField, #searchField').click(handleCollapseClick);
});
function initializeUserRequestHistoryTable() {
	table = $('#userRequestHistoryTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/userRequestHistory/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'url' },
			{ data: 'method' },
			{ data: 'reqUser' },
			{ data: 'reqIp' },
			{ data: 'createDt' },
			{
				data: null,
				render: function(data, type, row, meta) {
					var editButton = '<button type="button" class="btn btn-sm btn-outline-info mx-1 edit-button" data-id="' + row.id + '"><i class="fa-solid fa-magnifying-glass-chart"></i></button>';
					return editButton;
				},
			},
			{
				data: null,
				render: function(data, type, row, meta) {
					var deleteButton = '<button type="button" class="btn btn-sm btn-outline-danger mx-1 delete-button" data-id="' + row.id + '"><i class="fas fa-trash"></i></button>';
					return deleteButton;
				},
			},
		],
		order: [4, 'desc'], // 자동 정렬 비활성화
	});

	$('#userRequestHistoryTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editUserRequestHistory(id);
	});

	$('#userRequestHistoryTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteUserRequestHistory(id);
	});

	$('#userRequestHistoryTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveUserRequestHistory(formData) {
	callAjax("/userRequestHistory/save", "POST", formData, saveCallback);
}

function editUserRequestHistory(id) {
	callAjax("/userRequestHistory/list/"+ id, "POST", null, editCallback);
}

function deleteUserRequestHistory(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/userRequestHistory/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#reqData').text(response.data.reqData);
	$('#resData').text(response.data.resData);
}

function saveCallback(response){
	$('#userRequestHistoryForm input').val('');
	$('#userRequestHistoryTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#userRequestHistoryTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "userRequestHistory deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#userRequestHistoryTable').DataTable().ajax.reload();
}

