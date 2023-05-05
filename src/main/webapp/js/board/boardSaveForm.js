 var nowPage = 1;
 var pageSize = 1;
 
$(document).ready(function() {
	
    var csrfheader = $("meta[name='_csrf_header']").attr("content");
    var csrftoken = $("meta[name='_csrf']").attr("content");	
	
	var params = {
		"nowPage" : nowPage,
		"pageSize" : pageSize
	};
	
	
            $('form#boardForm').submit(function(event) {
            	
            	// Disable the submit button to prevent multiple submissions
            	$('#submitButton').prop('disabled', true);
            	
                event.preventDefault();

                var formData = new FormData($(this)[0]);
                var isSecret = $('#isSecret').is(':checked');
				formData.append('secret', isSecret);

                $.ajax({
                    url: '/board/save',
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

function callBackInit(response){
	$('#userProfileImg').attr('src', response.userProfileImg);
	$('#userSpan').text(response.userName);
}

function logout(){
	swal({
	  title: "Are you sure want to log out?",
	  text: "Your information is safely managed.",
	  icon: "warning",
	  buttons: true,
	  dangerMode: true,
	})
	.then((willDelete) => {
	  if (willDelete) {
	    logoutForm.submit();
	  } else {
	    
	  }
	});	
}
