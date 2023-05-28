/**
    This software is protected by copyright laws and international copyright treaties.
    The ownership and intellectual property rights of this software belong to the developer.
    Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
    and may result in legal consequences.
    This software is licensed to the user and must be used in accordance with the terms of the license.
    Under no circumstances should the source code or design of this software be disclosed or leaked.
    The developer shall not be liable for any loss or damages.
    Please read the license and usage permissions carefully before using.
*/

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
	debugger;
	initializeErrorHistTable();

	$('#errorHistForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveErrorHist(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#errorHistForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
});
function initializeErrorHistTable() {
	table = $('#errorHistTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/errorHist/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'errorMsg' },
			{ data: 'solutionMsg' },
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

	$('#errorHistTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editErrorHist(id);
	});

	$('#errorHistTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteErrorHist(id);
	});

	$('#errorHistTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveErrorHist(formData) {
	callAjax("/errorHist/save", "POST", formData, saveCallback);
}

function editErrorHist(id) {
	callAjax("/errorHist/list/"+ id, "POST", null, editCallback);
}

function deleteErrorHist(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/errorHist/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id').val(response.data.id);
	$('#errorMsg').val(response.data.errorMsg);
	$('#solutionMsg').val(response.data.solutionMsg);

	$('#errorHistForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#errorHistForm input').val('');
	$('#errorHistTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#errorHistTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "errorHist deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#errorHistTable').DataTable().ajax.reload();
}
