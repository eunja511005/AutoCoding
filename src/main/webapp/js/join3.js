var nowPage = 1;
var pageSize = 1;
 
		var csrfheader = $("meta[name='_csrf_header']").attr("content");
		var csrftoken = $("meta[name='_csrf']").attr("content");	
		
        $(document).ready(function() {
        	var MAX_FILE_SIZE = 1024 * 1024; // 1 MB
        	
        	
            $('form#uploadForm').submit(function(event) {
            	
            	// Disable the submit button to prevent multiple submissions
            	$('#submitButton').prop('disabled', true);
            	
                event.preventDefault();

                var formData = new FormData($(this)[0]);

                $.ajax({
                    url: '/join',
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    beforeSend : function(xhr){   
        				xhr.setRequestHeader(csrfheader, csrftoken);
                    },
                    success: function(response) {
                    	debugger;
                    	
                    	if(response.redirectUrl != undefined && response.redirectUrl != ""){
                    		window.location.href = response.redirectUrl;
                    	}
                    },
                    error: function(response) {
                    	debugger;
                        alert(response.responseText);
                    }
                });
                
             	// Enable the button after 0.3 seconds
                setTimeout(function() {
                  $('#submitButton').prop('disabled', false);
                }, 300);
            });

            $('input[type=file]').change(function() {
                $('#preview').html('');

                var files = $(this)[0].files;

                if(files.length > 0) {
                    for(var i=0; i<files.length; i++) {
                        var file = files[i];
                        
                        if (file.size > MAX_FILE_SIZE) {
                            alert('File size exceeds maximum limit of ' + MAX_FILE_SIZE + ' bytes.');
                            continue;
                        }
                        
                        var reader = new FileReader();

                        reader.onload = function(e) {
                            var previewImage = '<div class="preview-image-container"><img src="' + e.target.result + '" class="preview-image" /><button type="button" class="delete-button" onclick="$(this).parent().remove()">&times;</button></div>';
                            $('#preview').append(previewImage);
                        }

                        reader.readAsDataURL(file);
                    }
                }
            });
        });