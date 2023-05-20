var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");	
var table;
$(document).ready(function() {
	debugger;
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
	  table = $('#commonCodeTable').DataTable({
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
	  
	  $('#commonCodeTable tbody').on('click', 'tr', function () {
	      $(this).toggleClass('selected');
	  });
	 
	  $('#btn_generation').on('click', function (event) {
		  
		  
		  if(table.rows('.selected').data().length==0){
		      swal({
		      	 title: "Warning",
		      	 text: "Please select fields",
		      	 icon: "warning",
		      	 button: "OK",
		      })			  
			  return;
		  }
		  
		  var form = $('#subjectForm')[0];
		  
		  if (form.checkValidity() === false) {
			  event.preventDefault();
			  event.stopPropagation();
			  form.classList.add('was-validated');
			  return; // 유효성 검사 실패 시, 더 이상 진행하지 않고 종료합니다.			  
		  }
		  
		  form.classList.add('was-validated');

	      var data = {
	    		  subject : $('#subject').val(),
				  data : table.rows('.selected').data().toArray()
	      };
				      
	      callAjax("/autoCoding/generate", "POST", data, listCallback);  
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

	function listCallback(response) {
		  var data = response.data;
		  const sourceList = $('#sourceList');

		  // Clear the source list
		  sourceList.empty();

		  // Create list items for each source code
		  for (var autoCoding of data) {
		    const li = $('<li>').addClass('list-group-item d-flex flex-column gap-2');
		    const row = $('<div>').addClass('row');
		    const col = $('<div>').addClass('col-md-12 d-flex justify-content-between');

		    const nameElement = $('<span>').addClass('mr-4').text(autoCoding.sourceName);

		    const copyButton = $('<button>').addClass('btn btn-sm btn-dark');
		    const copyIcon = $('<i>').addClass('fas fa-copy');
		    const buttonText = $('<span>').text('Copy');
		    const separator = $('<span>').addClass('mx-2 separator').text('|'); // Add separator between icon and button text
		    copyButton.append(copyIcon);
		    copyButton.append(separator);
		    copyButton.append(buttonText);
		    copyButton.on('click', function() {
		        copyToClipboard(codeElement.text());
		        buttonText.text('Copied!');
		        copyIcon.removeClass('fa-copy').addClass('fa-check'); // Change the icon to check mark
		        setTimeout(function() {
		            buttonText.text('Copy');
		            copyIcon.removeClass('fa-check').addClass('fa-copy'); // Change the icon back to copy
		        }, 2000);
		    });


		    col.append(nameElement);
		    col.append(copyButton);
		    row.append(col);
		    li.append(row);

		    const codeElement = $('<pre>').css('padding-left', '20px').css('padding-top', '20px');
		    codeElement.addClass('bg-dark');

		    if (autoCoding.sourceName.endsWith('.java')) {
		    	codeElement.addClass('language-java').html(Prism.highlight(autoCoding.sourceCode, Prism.languages.java));
		    } else if (autoCoding.sourceName.endsWith('.xml')) {
		    	codeElement.addClass('language-xml').html(Prism.highlight(autoCoding.sourceCode, Prism.languages.xml));
		    } else if (autoCoding.sourceName.endsWith('.sql')) {
		    	codeElement.addClass('language-sql').html(Prism.highlight(autoCoding.sourceCode, Prism.languages.sql));
		    } else {
		    	codeElement.text(autoCoding.sourceCode);
		    }


		    li.css('padding', '20px');
		    li.append(codeElement);
		    sourceList.append(li);
		  }
		}

		function copyToClipboard(text) {
		  const textarea = $('<textarea>').val(text);
		  $('body').append(textarea);
		  textarea.select();
		  document.execCommand('copy');
		  textarea.remove();
		}










