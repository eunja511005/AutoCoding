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
	initializeSystemMasterTable();

	$('#systemMasterForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveSystemMaster(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#systemMasterForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
});
function initializeSystemMasterTable() {
	table = $('#systemMasterTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/systemMaster/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'systemName' },
			{ data: 'systemDescription' },
			{ data: 'systemAdmin' },
			{ data: 'homepage' },
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

	$('#systemMasterTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editSystemMaster(id);
	});

	$('#systemMasterTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteSystemMaster(id);
	});

	$('#systemMasterTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveSystemMaster(formData) {
	callAjax("/systemMaster/save", "POST", formData, saveCallback);
}

function editSystemMaster(id) {
	callAjax("/systemMaster/list/"+ id, "POST", null, editCallback);
}

function deleteSystemMaster(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/systemMaster/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id').val(response.data.id);
	$('#systemName').val(response.data.systemName);
	$('#systemDescription').val(response.data.systemDescription);
	$('#systemAdmin').val(response.data.systemAdmin);
	$('#homepage').val(response.data.homepage);

	$('#systemMasterForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#systemMasterForm input').val('');
	$('#systemMasterTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#systemMasterTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "systemMaster deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#systemMasterTable').DataTable().ajax.reload();
}

