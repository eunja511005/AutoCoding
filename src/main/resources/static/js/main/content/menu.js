var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
	debugger;
	initializeMenuTable();

	$('#menuForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveMenu(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#menuForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
});
function initializeMenuTable() {
	table = $('#menuTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/menu/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'category' },
			{ data: 'menuLevel' },
			{ data: 'menuName' },
			{ data: 'menuId' },
			{ data: 'menuPath' },
			{ data: 'menuIcon' },
			{ data: 'menuOrder' },
			{ data: 'parentMenuId' },
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

	$('#menuTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editMenu(id);
	});

	$('#menuTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteMenu(id);
	});

	$('#menuTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveMenu(formData) {
	callAjax("/menu/save", "POST", formData, saveCallback);
}

function editMenu(id) {
	callAjax("/menu/list/"+ id, "POST", null, editCallback);
}

function deleteMenu(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/menu/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id').val(response.data.id);
	$('#category').val(response.data.category);
	$('#menuLevel').val(response.data.menuLevel);
	$('#menuName').val(response.data.menuName);
	$('#menuId').val(response.data.menuId);
	$('#menuPath').val(response.data.menuPath);
	$('#menuIcon').val(response.data.menuIcon);
	$('#menuOrder').val(response.data.menuOrder);
	$('#parentMenuId').val(response.data.parentMenuId);

	$('#menuForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#menuForm input').val('');
	$('#menuTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#menuTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "menu deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#menuTable').DataTable().ajax.reload();
}

