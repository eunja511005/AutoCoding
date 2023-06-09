    	var csrfheader = $("meta[name='_csrf_header']").attr("content");
    	var csrftoken = $("meta[name='_csrf']").attr("content");
    
        $(function(){
            $('#myForm').submit(function(event) {
            	event.preventDefault();
            	
                var myFormData = {
                    username : $('#username').val(),
                    password : $("#password").val()
                };
                $.ajax({
                    type : 'POST',
                    url : '/signin',
                    contentType : 'application/x-www-form-urlencoded; charset=utf-8',
                    data : myFormData,
                    dataType : 'text',
                    beforeSend : function(xhr){   
        				xhr.setRequestHeader(csrfheader, csrftoken);
                    },
                    success: function(response) {
                    	var response = JSON.parse(response);
                    	
                        if(response.code=='200'){
                        	swal({
                        		  title: response.result,
                        		  text: "Click the button to go to the main page.",
                        		  icon: "success",
                        		  button: "OK",
                        		})
                        		.then((result) => {
                        		  if (result) {
                        			  $('#dynamic-content').load("/content1");
                        			  location.reload(); // 로그인 성공후 csrf 토큰 리프레쉬를 위해 페이지 리로드
                        		  }
                        		});
                        } else {
                        	swal({
                      		  title: "Unauthorized",
                      		  text: response.result,
                      		  icon: "warning",
                      		  buttons: {
                      		    confirm: "Cancel",
                      		    logout: "Delete Previous Session",
                      		  },
                      		}) 
                      		.then((result) => {
	                      		if (result === "logout") {
	                      			
	                      		  callAjax("/signout2", "POST", myFormData, function(response) {
	                      		      if (response.success) {
	                                  	swal({
	                              		  title: "Success",
	                              		  text: "The previous session has been successfully deleted. Please log in again.",
	                              		  icon: "success",
	                              		  button: "OK",
	                              		})
	                      			  }
	                      		  });
	                      		}                  			
                      		});
                        }
                    	
                    	
                    },
                	error : function (jqXHR, textStatus, errorThrown){
                		console.log(jqXHR);  // 응답 메시지
                		console.log(textStatus); // "error"로 고정인듯함
                		console.log(errorThrown);
                	}
                })
                
            });
            
        });            
        
        