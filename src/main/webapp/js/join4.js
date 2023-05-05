var nowPage = 1;
var pageSize = 1;
 
var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");	
		
$(document).ready(function() {
    var MAX_FILE_SIZE = 100 * 1024 * 1024; // 100MB
    
    $('form#uploadForm').submit(function(event) {
    	
    	// Disable the submit button to prevent multiple submissions
    	$('#submitButton').prop('disabled', true);
    	
        event.preventDefault();

        var fileInput = $('input[type=file]')[0];
        var file = fileInput.files[0];

        if (file.size > MAX_FILE_SIZE) {
            alert('File size exceeds the maximum allowed limit of 100MB.');
            return;
        }
        
        // Compress the image
        compressImage(file).then(function(compressedFile) {
            // Create a new FormData object and add the compressed image file
            //var formData = new FormData($(this)[0]);
            var form = document.getElementById('uploadForm'); //id of form
            var formData = new FormData(form);
            formData.set('file', compressedFile, compressedFile.name);

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
    });
});

function compressImage(file) {
    return new Promise(function(resolve, reject) {
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = function() {
            var img = new Image();
            img.src = reader.result;
            img.onload = function() {
                var canvas = document.createElement('canvas');
                var ctx = canvas.getContext('2d');

                // Calculate the new width and height of the image
                var width = img.width;
                var height = img.height;
                var maxWidth = 800;
                var maxHeight = 800;
                if (width > height) {
                    if (width > maxWidth) {
                        height *= maxWidth / width;
                        width = maxWidth;
                    }
                } else {
                    if (height > maxHeight) {
                        width *= maxHeight / height;
                        height = maxHeight;
                    }
                }

                // Draw the compressed image on the canvas
                canvas.width = width;
                canvas.height = height;
                ctx.drawImage(img, 0, 0, width, height);

                // Convert the canvas image to a Blob object and resolve the Promise
                canvas.toBlob(function(blob) {
                    resolve(new File([blob], file.name, { type: file.type, lastModified: Date.now() }));
                }, file.type, 0.5);
            };
        };
        reader.onerror = function(error) {
            reject(error);
        };
    });
}
