var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");	
var table;
$(document).ready(function() {
	  // Initialize the common code table
	  initializeCommonCodeTable();

	  // Handle the form submit event
	  $('#commonCodeForm').on('submit', function(event) {
	    event.preventDefault();

	    // Get the form data
	    var formData = $(this).serializeArray();

	    // Send the AJAX request to save the common code data
	    saveCommonCode(formData);
	  });
	  
	  // Handle the form submit event
	  $('#clear-btn').on('click', function() {
		  // Clear the form inputs
	      $('#commonCodeForm input').val('');
	  });
	  
	});

	function initializeCommonCodeTable() {
	  // Initialize the common code table
	  $('#commonCodeTable').DataTable({
		scrollX: true,  
	    ajax: {
	      url: '/commonCode/list',
	      "type": "POST",
	      beforeSend : function(xhr){   
				xhr.setRequestHeader(csrfheader, csrftoken);
          },
	      dataSrc: '',
	    },
	    columns: [
	      { data: 'codeGroup' },
	      { data: 'code' },
	      { data: 'value' },
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

	  // Add the click event handlers for the edit and delete buttons
	  $('#commonCodeTable tbody').on('click', '.edit-button', function() {
	    var id = $(this).data('id');
	    editCommonCode(id);
	  });

	  $('#commonCodeTable tbody').on('click', '.delete-button', function() {
	    var id = $(this).data('id');
	    deleteCommonCode(id);
	  });
	}

	function saveCommonCode(formData) {
	  $.ajax({
	    url: '/commonCode/save',
	    type: 'POST',
	    beforeSend : function(xhr){   
			xhr.setRequestHeader(csrfheader, csrftoken);
	    },
	    data: formData,
	    success: function(response) {
	      if (response.success) {
	        // Clear the form inputs
	        $('#commonCodeForm input').val('');

	        // Reload the common code table
	        $('#commonCodeTable').DataTable().ajax.reload();
	      } else {
	        alert(response.errorMessage);
	      }
	    },
	    error: function() {
	      alert('Failed to save the common code data.');
	    },
	  });
	}

	function editCommonCode(id) {
	  $.ajax({
	    url: '/commonCode/list/'+ id,
	    type: 'POST',
	    beforeSend : function(xhr){   
			xhr.setRequestHeader(csrfheader, csrftoken);
	    },
	    data: {
	      id: id,
	    },
	    success: function(response) {
	      if (response.success) {
	        // Set the form values
	    	$('#id').val(response.data.id);
	        $('#codeGroup').val(response.data.codeGroup);
	        $('#code').val(response.data.code);
	        $('#value').val(response.data.value);

	        // Set the edit mode flag
	        $('#commonCodeForm').attr('data-mode', 'edit');
	      } else {
	    	swal({
	      		  title: "Application Error",
	      		  text: response.errorMessage,
	      		  icon: "warning",
	      		  button: "OK",
	      	})
	      }
	    },
	    error: function() {
	    	swal({
      		  title: "Ajax Error",
      		  text: "Failed to get the common code data.",
      		  icon: "warning",
      		  button: "OK",
      		})
	    },
	  });
	}

	function deleteCommonCode(id) {
	    if (confirm('Are you sure you want to delete this common code?')) {
	        $.ajax({
	            url: '/commonCode/' + id,
	            type: 'DELETE',
	            beforeSend : function(xhr){   
	    			xhr.setRequestHeader(csrfheader, csrftoken);
	    	    },
	            success: function(result) {
	                if (result.success) {
	                    // Remove the common code row from the table
	                    $('#commonCodeTable tbody').find('tr[data-id="' + id + '"]').remove();
	                    alert('Common code deleted successfully.');
	                    
	                    // Reload the common code table
	        	        $('#commonCodeTable').DataTable().ajax.reload();
	                } else {
	                    alert('Failed to delete common code. Please try again later.');
	                }
	            },
	            error: function() {
	                alert('Failed to delete common code. Please try again later.');
	            }
	        });
	    }
	}
