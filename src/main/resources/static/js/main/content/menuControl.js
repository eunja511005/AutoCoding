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
	initializeMenuControlTable();

	$('#menuControlForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveMenuControl(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#menuControlForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
});
function initializeMenuControlTable() {
	table = $('#menuControlTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/menuControl/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'url' },
			{ data: 'method' },
			{ data: 'roleId' },
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
	});

	$('#menuControlTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editMenuControl(id);
	});

	$('#menuControlTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteMenuControl(id);
	});

	$('#menuControlTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveMenuControl(formData) {
	callAjax("/menuControl/save", "POST", formData, saveCallback);
}

function editMenuControl(id) {
	callAjax("/menuControl/list/"+ id, "POST", null, editCallback);
}

function deleteMenuControl(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/menuControl/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id').val(response.data.id);
	$('#url').val(response.data.url);
	$('#method').val(response.data.method);
	$('#roleId').val(response.data.roleId);

	$('#menuControlForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#menuControlForm input').val('');
	$('#menuControlTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#menuControlTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "menuControl deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#menuControlTable').DataTable().ajax.reload();
}

