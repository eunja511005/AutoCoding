var nowPage = 1;
var pageSize = 1;

var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");

var currentCodeId;

$(document).ready(function() {

	debugger;
	var aParams = {
		nowPage: nowPage,
		pageSize: pageSize
	};


	$('#codeTable').DataTable({
		lengthChange: false,
		displayLength: 20,
		scrollX: true,
		"autoWidth": true,
		ajax: {
			url: '/code/list',
			type: 'POST',
			data: aParams,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: 'codeList'
		},
		columns: [
			{ data: 'codeGroupId' },
			{ data: 'codeGroupName' },
			{ data: 'codeGroupDescription' },
			{ data: 'codeId' },
			{ data: 'codeLevel', "className": "text-right" },
			{ data: 'codeSequence', "className": "text-right" },
			{ data: 'codeName' },
			{ data: 'codeDescription' },
			{ data: 'enable' },
			{ data: 'updateId' },
			{
				data: 'updateTime',
				"render": function(data, type, row, meta) {
					data = data;
					return data;
				}
			},
			{
				data: '',
				"render": function(data, type, row, meta) {
					return '<i class="fa-solid fa-magnifying-glass-chart"></i>';
				}
			},
			{
				data: '',
				"render": function(data, type, row, meta) {
					return '<i class="fa-solid fa-trash-can"></i>';
				}
			}
		],
		columnDefs: [
			//{ targets: 0, visible: false }
			{ "className": "text-right", "targets": [4, 5] },
			{ "className": "text-center", "targets": [11, 12] },
		],
		order: [[0, 'desc'], [3, 'desc'], [10, 'desc']]
	});

});

// Attach click event to "Save Changes" button
$('#newCodeModal .modal-footer button.btn-primary').on('click', function() {
	var form = $('#newCodeForm')[0];
	if (form.checkValidity() === false) {
		event.preventDefault();
		event.stopPropagation();
	}
	form.classList.add('was-validated');
	if (form.checkValidity() === true) {
		save();
	}
});

// Bind click event to delete button
$('#codeTable').on('click', '.fa-trash-can', function() {

	swal({
		title: "Are you sure you want to delete it?",
		text: "Your information is safely managed.",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				var data = $('#codeTable').DataTable().row($(this).parents('tr')).data();

				var id = data.id;

				$.ajax({
					url: '/code/delete/' + id,
					type: 'DELETE',
					beforeSend: function(xhr) {
						xhr.setRequestHeader(csrfheader, csrftoken);
					},
					success: function(response) {
						if (response.redirectUrl != undefined && response.redirectUrl != "") {
							window.location.href = response.redirectUrl;
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						console.log(jqXHR);  //응답 메시지
						swal({
                      		  title: "Delete failed",
                      		  text: "Error : "+jqXHR.responseJSON.message,
                      		  icon: "warning",
                      		  button: "OK",
                      		}) 
                      		.then((result) => {
									// Close the modal
									var modal = $('#newCodeModal');
									var modalInstance = bootstrap.Modal.getInstance(modal);
									modalInstance.hide();

									// Reset form
									$('#newCodeModal form')[0].reset();
                      		});
					}
				});
			} else {

			}
		});

});

$(document).on('click', '.fa-magnifying-glass-chart', function() {
	var $btn = $(this);
	var $tr = $btn.closest('tr');
	var dataTableRow = $("#codeTable").DataTable().row($tr[0]); // get the DT row so we can use the API on it
	var rowData = dataTableRow.data();

	$('#newCodeModal').modal('show');
	$('#id').val(rowData.id);
	$('#codeGroupId').val(rowData.codeGroupId);
	$('#codeGroupName').val(rowData.codeGroupName);
	$('#codeGroupDescription').val(rowData.codeGroupDescription);
	$('#codeId').val(rowData.codeId);
	$('#codeLevel').val(rowData.codeLevel);
	$('#codeSequence').val(rowData.codeSequence);
	$('#codeName').val(rowData.codeName);
	$('#codeDescription').val(rowData.codeDescription);
	$('#enable').prop('checked', rowData.enable);
});

$(document).on('click', '.btn-secondary', function() {
	$('#contentModal').modal('hide');
});

$(document).on('click', '#updateContents', function() {
	var data = {
		codeId: currentCodeId,
		content: $('#summernote').summernote('code')
	};

	$.ajax({
		type: 'PUT',
		url: '/code/update',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
		},
		contentType: 'application/json; charset=utf-8',
		data: JSON.stringify(data),
		processData: false,
		dataType: 'text',
		success: function(response) {
			debugger;

			//var response = eval( '(' + response + ')' );
			var response = JSON.parse(response);

			swal({
				title: response.result,
				text: "Your changes have been saved.",
				icon: "success",
				button: "OK",
			})
				.then((result) => {
					if (result) {
						window.location.href = response.redirectUrl;
					}
				});
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR);  //응답 메시지
		}
	})

});

function save(){
	// Get form data
	var formData = new FormData($('#newCodeModal form')[0]);

	// Convert FormData object to JSON object
	var json = {};
	formData.forEach(function(value, key) {
      // If the key is "enable", set the value as a boolean
      if (key === 'enable') {
        json[key] = $("#enable").is(':checked');
      } else {
        json[key] = value;
      }
	});

	// Send AJAX request to save data
	$.ajax({
		url: '/code/save',
		method: 'POST',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
		},
		headers: {
			'Content-Type': 'application/json; charset=UTF-8'
		},
		data: JSON.stringify(json),
		success: function(response) {
			
			swal({
	        	title: response.result,
	        	text: "Your changes have been saved.",
	        	icon: "success",
	        	button: "OK",
	        	})
	        	.then((result) => {
	        	if (result) {
					// Close the modal
					var modal = $('#newCodeModal');
					var modalInstance = bootstrap.Modal.getInstance(modal);
					modalInstance.hide();
		
					// Reset form
					$('#newCodeModal form')[0].reset();
					
					if (response.redirectUrl != undefined && response.redirectUrl != "") {
						window.location.href = response.redirectUrl;
					}
	        	}
	            });			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR);  //응답 메시지
			swal({
				title: "Registration failed",
				text: "Error : " + jqXHR.responseJSON.message,
				icon: "warning",
				button: "OK",
			})
				.then((result) => {
					if (result) {
						// Close the modal
						var modal = $('#newCodeModal');
						var modalInstance = bootstrap.Modal.getInstance(modal);
						modalInstance.hide();

						// Reset form
						$('#newCodeModal form')[0].reset();
					}
				});
		}		
	});	
}
