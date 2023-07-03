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
	initializeApiMasterTable();

	$('#apiMasterForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveApiMaster(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#apiMasterForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
});
function initializeApiMasterTable() {
	table = $('#apiMasterTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/apiMaster/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'apiName' },
			{ data: 'apiDescription' },
			{ data: 'callUrl' },
			{ data: 'direction' },
			{ data: 'author' },
			{ data: 'callMax' },
			{ data: 'httpMethod' },
			{ data: 'logYn' },
			{
				data: null,
				render: function(data, type, row, meta) {
					var editButton = '<button type="button" class="btn btn-sm btn-outline-info mx-1 edit-button" data-id="' + row.id + '"><i class="fas fa-edit"></i></button>';
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
		order: [], // 자동 정렬 비활성화
	});

	$('#apiMasterTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editApiMaster(id);
	});

	$('#apiMasterTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteApiMaster(id);
	});

	$('#apiMasterTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveApiMaster(formData) {
	callAjax("/apiMaster/save", "POST", formData, saveCallback);
}

function editApiMaster(id) {
	callAjax("/apiMaster/list/"+ id, "POST", null, editCallback);
}

function deleteApiMaster(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/apiMaster/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id').val(response.data.id);
	$('#apiName').val(response.data.apiName);
	$('#apiDescription').val(response.data.apiDescription);
	$('#callUrl').val(response.data.callUrl);
	$('#direction').val(response.data.direction);
	$('#author').val(response.data.author);
	$('#callMax').val(response.data.callMax);
	$('#httpMethod').val(response.data.httpMethod);
	$('#logYn').val(response.data.logYn);

	$('#apiMasterForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#apiMasterForm input').val('');
	$('#apiMasterTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#apiMasterTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "apiMaster deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#apiMasterTable').DataTable().ajax.reload();
}

