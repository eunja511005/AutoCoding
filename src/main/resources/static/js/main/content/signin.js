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
                      		  title: "Warning",
                      		  text: response.result,
                      		  icon: "warning",
                      		  buttons: {
                      		    confirm: "OK",
                      		    logout: "Logout",
                      		  },
                      		}) 
                      		.then((result) => {
	                      		if (result === "logout") {
	                      			// 로그아웃 처리 후 리디렉션
	                      			window.location.href = "/signout";
	                      			window.location.href = "/";
	                      		} else {
	                      			// 사용자가 확인 버튼을 클릭하거나 대화상자를 닫은 경우 아무 작업 없음
	                      			window.location.href = "/";
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
        
        