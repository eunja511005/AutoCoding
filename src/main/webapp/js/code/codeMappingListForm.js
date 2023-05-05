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


	$('#codeMappingTable').DataTable({
		lengthChange: false,
		displayLength: 20,
		scrollX: true,
		"autoWidth": true,
		ajax: {
			url: '/code/mapping/list',
			type: 'POST',
			data: aParams,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfheader, csrftoken);
			},
			dataSrc: 'codeMappingList'
		},
		columns: [
			{ data: 'codeMappingName' },
			{ data: 'codeMappingDescription' },
			{ data: 'fromCodeId' },
			{ data: 'toCodeId' },
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
			{ "className": "text-center", "targets": [7, 8] },
		],
		order: [[6, 'desc']]
	});
	
	// set combo box
	$.ajax({
		url: '/code/mapping/codes',
		method: 'POST',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfheader, csrftoken);
		},
		headers: {
			'Content-Type': 'application/json; charset=UTF-8'
		},
		success: function(data) {
			var codeMappingName = $('#codeMappingName');
			$.each(data.mappingList, function(i, item) {
			  codeMappingName.append($('<option>', {
			    value: item.codeId,
			    text: item.codeName
			  }));
			});

			var fromCodeId = $('#fromCodeId');
			$.each(data.fromCodeIdList, function(i, item) {
			  fromCodeId.append($('<option>', {
			    value: item.codeId,
			    text: item.codeName
			  }));
			});
			
			var toCodeId = $('#toCodeId');
			$.each(data.toCodeIdList, function(i, item) {
			  toCodeId.append($('<option>', {
			    value: item.codeId,
			    text: item.codeName
			  }));
			});			
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR);  //응답 메시지
		}
	});

});

// Attach click event to "Save Changes" button
$('#newCodeMappingModal .modal-footer button.btn-primary').on('click', function() {
	var form = $('#newCodeMappingForm')[0];
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
$('#codeMappingTable').on('click', '.fa-trash-can', function() {

	swal({
		title: "Are you sure you want to delete it?",
		text: "Your information is safely managed.",
		icon: "warning",
		buttons: true,
		dangerMode: true,
	})
		.then((willDelete) => {
			if (willDelete) {
				var data = $('#codeMappingTable').DataTable().row($(this).parents('tr')).data();

				var codeMappingId = data.codeMappingId;

				$.ajax({
					url: '/code/mapping/delete/' + codeMappingId,
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
									var modal = $('#newCodeMappingModal');
									var modalInstance = bootstrap.Modal.getInstance(modal);
									modalInstance.hide();

									// Reset form
									$('#newCodeMappingModal form')[0].reset();
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
	var dataTableRow = $("#codeMappingTable").DataTable().row($tr[0]); // get the DT row so we can use the API on it
	var rowData = dataTableRow.data();

	$('#newCodeMappingModal').modal('show');
	$('#codeMappingId').val(rowData.codeMappingId);
	$('#codeMappingName').val(rowData.codeMappingName);
	$('#codeMappingDescription').val(rowData.codeMappingDescription);
	$('#fromCodeId').val(rowData.fromCodeId);
	$('#toCodeId').val(rowData.toCodeId);
	$('#enable').prop('checked', rowData.enable);
});


function save(){
	// Get form data
	var formData = new FormData($('#newCodeMappingModal form')[0]);

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
		url: '/code/mapping/save',
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
					var modal = $('#newCodeMappingModal');
					var modalInstance = bootstrap.Modal.getInstance(modal);
					modalInstance.hide();
		
					// Reset form
					$('#newCodeMappingModal form')[0].reset();
					
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
					var modal = $('#newCodeMappingModal');
					var modalInstance = bootstrap.Modal.getInstance(modal);
					modalInstance.hide();

					// Reset form
					$('#newCodeMappingModal form')[0].reset();
				}
			});
		}		
	});	
}

