<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

  <head>
	<jsp:include page="/WEB-INF/jsp/common/common.jsp"/>
    <title>Signin</title>
  </head>
  
  <body class="text-center">
    
    <div class="container my-5">
      <div class="row justify-content-center">
        <div class="col-md-4">
          <div class="card">
            <div class="card-header">
              <h4>Login Form</h4>
            </div>
            <div class="card-body">
              <form id="myForm">
                <div class="mb-3">
                  <label for="username" class="form-label">Username</label>
                  <input type="text" class="form-control" id="username" name="username">
                </div>
                <div class="mb-3">
                  <label for="password" class="form-label">Password</label>
                  <input type="password" class="form-control" id="password" name="password">
                </div>
                <div class="d-grid gap-2">
                  <button type="submit" class="btn btn-primary">Login</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      
    <div class="container px-5">
		<div class="row justify-content-center">
	    	<a href="/joinInit">Sign up</a>
		</div>
	</div>	
      
    </div>    
	    
	<jsp:include page="/WEB-INF/jsp/common/commonScript.jsp"/>

    <script>
    	var csrfheader = $("meta[name='_csrf_header']").attr("content");
    	var csrftoken = $("meta[name='_csrf']").attr("content");
    
        $(function(){
            $('#myForm').submit(function(event) {
            	debugger;
            	
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
                        			  window.location.href = "/initInit";
                        		  }
                        		});
                        } else {
                        	swal({
                      		  title: "Unauthorized",
                      		  text: "Error : "+response.result,
                      		  icon: "warning",
                      		  button: "OK",
                      		}) 
                      		.then((result) => {
                      		  if (result) {
                      			  window.location.href = "/signout";
                      		  }
                      		});
                        }
                    	
                    	
                    },
                	error : function (jqXHR, textStatus, errorThrown){
                		console.log(jqXHR);  //응답 메시지
                		console.log(textStatus); //"error"로 고정인듯함
                		console.log(errorThrown);
                	}
                })
                
            });
        });
    </script>

  </body>
</html>
