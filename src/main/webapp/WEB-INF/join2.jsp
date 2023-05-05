<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
    <title>Multiple File Upload with Preview using AJAX and Bootstrap</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        .preview-image {
            max-width: 100%;
            max-height: 150px;
            margin-bottom: 10px;
        }
        .preview-image-container {
            position: relative;
            display: inline-block;
            margin-right: 10px;
        }
        .delete-button {
            position: absolute;
            top: -10px;
            right: -10px;
            background-color: #d9534f;
            border: none;
            color: white;
            font-weight: bold;
            border-radius: 50%;
            width: 25px;
            height: 25px;
            padding: 0;
            line-height: 0.8;
            text-align: center;
        }
        .delete-button:hover {
            background-color: #c9302c;
        }
    </style>
</head>
<body>
<section class="py-5">
    <div class="container">
        <!-- Contact form-->
        <div class="bg-light rounded-3 py-5">
            <div class="row justify-content-center">
                <div class="col-xs-12 col-lg-6">
		              
				        <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>
				        <form id="uploadForm" enctype="multipart/form-data">
				            <div class="form-group">
				                <label for="file" class="form-label h4">Image files:</label>
								<label class="btn btn-primary" for="file">
								  Choose files
								  <input type="file" name="yfiles" id="file" accept="image/jpeg, image/png" multiple style="display: none;">
								</label>
				            </div>
				            <div class="form-group">
				                <label for="username" class="form-label h4">Username:</label>
				                <input type="text" name="username" id="username" class="form-control" required />
				            </div>
				            <div class="form-group">
				                <label for="password" class="form-label h4">Password:</label>
				                <input type="password" name="password" id="password" class="form-control" required />
				            </div>            
				            <div class="form-group">
				                <label for="email" class="form-label h4">Email:</label>
				                <input type="email" name="email" id="email" class="form-control" required />
				            </div>
				            <div class="form-group">
				            	<label for="role" class="form-label h4">Role:</label>
				            	<div class="container">
				            		<div class="row">
										<div class="col-sm-2">
											<input class="form-check-input" type="radio" name="role" id="role1" value="ROLE_ADMIN,ROLE_FAMILY,ROLE_USER">
											<label class="form-check-label form-label h4" for="role1"> Admin </label>
										</div>	
										<div class="col-sm-2">
											<input class="form-check-input" type="radio" name="role" id="role2" value="ROLE_FAMILY,ROLE_USER" checked>
											<label class="form-check-label form-label h4" for="role2"> FAMILY </label>
										</div>
										<div class="col-sm-2">
											<input class="form-check-input" type="radio" name="role" id="role3" value="ROLE_USER">
											<label class="form-check-label form-label h4" for="role3"> USER </label>
										</div>									
									</div>
								</div>
							</div>
							<div id="preview"></div>
				            <button type="submit" class="btn btn-primary btn-lg" id="submitButton">Upload</button>
				        </form>

	           </div>
            </div>
        </div>
    </div>
</section>  
    
    <script>
    
		var csrfheader = $("meta[name='_csrf_header']").attr("content");
		var csrftoken = $("meta[name='_csrf']").attr("content");	
		
        $(document).ready(function() {
        	var MAX_FILE_SIZE = 100 * 1024 * 1024; // 100MB
        	
        	
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
    </script>    
</body>

</html>