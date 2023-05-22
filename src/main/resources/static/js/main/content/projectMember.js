var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
	debugger;
	initializeProjectMemberTable();

	$('#projectMemberForm').on('submit', function(event) {
		event.preventDefault();
		var formData = new FormData(this);
		saveProjectMember(formData);
	});

	$('#clear-btn').on('click', function() {
		$('#projectMemberForm input').val('');
	});

	function handleCollapseClick() {
		var $cardBody = $(this).parents('.card').find('.card-body');
		$cardBody.slideToggle();
	}

	$('#newField, #searchField').click(handleCollapseClick);
});
function initializeProjectMemberTable() {
	table = $('#projectMemberTable').DataTable({
		scrollX: true, 
		ajax: {
			url: '/projectMember/list',
			"type": "POST",
			beforeSend : function(xhr){
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: '',
		},
		columns: [
			{ data: 'name' },
			{ data: 'email' },
			{ data: 'contact' },
			{ data: 'position' },
			{ data: 'picture' },
			{ data: 'introduction' },
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

	$('#projectMemberTable tbody').on('click', '.edit-button', function() {
		var id = $(this).data('id');
		editProjectMember(id);
	});

	$('#projectMemberTable tbody').on('click', '.delete-button', function() {
		var id = $(this).data('id');
		deleteProjectMember(id);
	});

	$('#projectMemberTable tbody').on('click', 'tr', function() {
		$(this).toggleClass('selected');
	});

}

function saveProjectMember(formData) {
	callAjax("/projectMember/save", "POST", formData, saveCallback);
}

function editProjectMember(id) {
	callAjax("/projectMember/list/"+ id, "POST", null, editCallback);
}

function deleteProjectMember(id) {
	if (confirm('Are you sure you want to delete?')) {
		callAjax("/projectMember/"+ id, "DELETE", null, deleteCallback);
	}
}

function editCallback(response){
	$('#id').val(response.data.id);
	$('#name').val(response.data.name);
	$('#email').val(response.data.email);
	$('#contact').val(response.data.contact);
	$('#position').val(response.data.position);
	$('#picture').val(response.data.picture);
	$('#introduction').val(response.data.introduction);

	$('#projectMemberForm').attr('data-mode', 'edit');
}

function saveCallback(response){
	$('#projectMemberForm input').val('');
	$('#projectMemberTable').DataTable().ajax.reload();
}

function deleteCallback(response){
	$('#projectMemberTable tbody').find('tr[data-id="' + id + '"]').remove();
	swal({
		title: "Success",
		text: "projectMember deleted successfully.",
		icon: "success",
		button: "OK",
	})
	$('#projectMemberTable').DataTable().ajax.reload();
}

