var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
	debugger;
	initializeAccessControlTable();

	$('#accessControlForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveAccessControl(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#accessControlForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
});
function initializeAccessControlTable() {
	table = $('#accessControlTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/accessControl/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'userId' },
			{ data: 'roleId' },
			{ data: 'relation' },
			{ data: 'resourceId' },
			{ data: 'permission' },
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

	$('#accessControlTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editAccessControl(id);
	});

	$('#accessControlTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteAccessControl(id);
	});

	$('#accessControlTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveAccessControl(formData) {
	callAjax("/accessControl/save", "POST", formData, saveCallback);
}

function editAccessControl(id) {
	callAjax("/accessControl/list/"+ id, "POST", null, editCallback);
}

function deleteAccessControl(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/accessControl/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id').val(response.data.id);
	$('#userId').val(response.data.userId);
	$('#roleId').val(response.data.roleId);
	$('#relation').val(response.data.relation);
	$('#resourceId').val(response.data.resourceId);
	$('#permission').val(response.data.permission);

	$('#accessControlForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#accessControlForm input').val('');
	$('#accessControlTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#accessControlTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "accessControl deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#accessControlTable').DataTable().ajax.reload();
}

