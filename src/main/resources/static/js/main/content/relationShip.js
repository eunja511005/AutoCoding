var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
	debugger;
	initializeRelationShipTable();

	$('#relationShipForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveRelationShip(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#relationShipForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
});
function initializeRelationShipTable() {
	table = $('#relationShipTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/relationShip/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'relation' },
			{ data: 'fromUser' },
			{ data: 'toUser' },
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

	$('#relationShipTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editRelationShip(id);
	});

	$('#relationShipTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteRelationShip(id);
	});

	$('#relationShipTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveRelationShip(formData) {
	callAjax("/relationShip/save", "POST", formData, saveCallback);
}

function editRelationShip(id) {
	callAjax("/relationShip/list/"+ id, "POST", null, editCallback);
}

function deleteRelationShip(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/relationShip/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id').val(response.data.id);
	$('#relation').val(response.data.relation);
	$('#fromUser').val(response.data.fromUser);
	$('#toUser').val(response.data.toUser);

	$('#relationShipForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#relationShipForm input').val('');
	$('#relationShipTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#relationShipTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "relationShip deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#relationShipTable').DataTable().ajax.reload();
}

