<!doctype html>
<html lang="en" data-bs-theme="auto">
  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.111.3">
    <title>Signin Template · Bootstrap v5.3</title>


	<jsp:include page="jsp/common/common.jsp"/>
    <link rel="canonical" href="https://getbootstrap.com/docs/5.3/examples/sign-in/">
	<link href="../assets/dist/css/bootstrap.min.css" rel="stylesheet">
   </head>
   
   <body>
   
    <script>
    	var csrfheader = $("meta[name='_csrf_header']").attr("content");
    	var csrftoken = $("meta[name='_csrf']").attr("content");
    
        $(function(){
            $('#myForm').submit(function(event) {
            	debugger;
                var myFormData = {
                    username : $('#username').val(),
                    password : $("#password").val(),
                    email    : $("#email").val(),
                    phone    : $("#phone").val(),
                    role     : $('input[name=role]:checked').val()
                };
                $.ajax({
                    type : 'POST',
                    url : '/join',
                    contentType : 'application/x-www-form-urlencoded; charset=utf-8',
                    data : myFormData,
                    dataType : 'json',
                    beforeSend : function(xhr){   
        				xhr.setRequestHeader(csrfheader, csrftoken);
                    }
                }).done(function (data) {
                    <!--alert("login success");-->
                    debugger;
                    alert(data.result)
                    window.location.href = '/index.html';
                }).fail(function (error) {
                	debugger;
                    var errText;
                    if(JSON.stringify(error.status)==401){
                        errText="Invalid username or password";
                    }else{
                        errText="UnKnown error";
                    }
                    alert("login fail : "+errText);
                });
                event.preventDefault();
            });
        });
    </script>

	<section class="py-5">
	    <div class="container px-5">
	        <!-- Contact form-->
	        <div class="bg-light rounded-3 py-5 px-4 px-md-5 mb-5">
	            <div class="text-center mb-5">
	                <h1 class="fw-bolder">Account Registration</h1>
	                <p class="lead fw-normal text-muted mb-0">Welcome to AutoCoding</p>
	            </div>
	            <div class="row gx-5 justify-content-center">
	                <div class="col-lg-8 col-xl-6">
	                    <form id="myForm">
	                        <!-- Name input-->
	                        <div class="form-floating mb-3">
	                            <input class="form-control" id="username" name="username" type="text" placeholder="Enter your name..." data-sb-validations="required" />
	                            <label for="name">Username</label>
	                            <div class="invalid-feedback" data-sb-feedback="username:required">A name is required.</div>
	                        </div>
	                        <!-- Password input-->
	                        <div class="form-floating mb-3">
	                            <input class="form-control" id="password" name="password" type="password" placeholder="Enter your password..." data-sb-validations="required" />
	                            <label for="name">Password</label>
	                            <div class="invalid-feedback" data-sb-feedback="password:required">A password is required.</div>
	                        </div>
	                        <!-- Email address input-->
	                        <div class="form-floating mb-3">
	                            <input class="form-control" id="email" name="email" type="email" placeholder="name@example.com" data-sb-validations="required,email" />
	                            <label for="email">Email address</label>
	                            <div class="invalid-feedback" data-sb-feedback="email:required">An email is required.</div>
	                            <div class="invalid-feedback" data-sb-feedback="email:email">Email is not valid.</div>
	                        </div>
	                        <!-- Phone number input-->
	                        <div class="form-floating mb-3">
	                            <input class="form-control" id="phone" type="tel" placeholder="(123) 456-7890" data-sb-validations="required" />
	                            <label for="phone">Phone number</label>
	                            <div class="invalid-feedback" data-sb-feedback="phone:required">A phone number is required.</div>
	                        </div>
	                        <!-- Message input-->
	                        <div class="form-floating mb-3">
	                            <div class="form-check form-check-inline">
	                                <input class="form-check-input" type="radio" name="role" id="role1" value="ROLE_ADMIN,ROLE_FAMILY,ROLE_USER">
	                                <label class="form-check-label" for="role1">
	                                    Admin
	                                </label>
	                            </div>
	                            <div class="form-check form-check-inline">
	                                <input class="form-check-input" type="radio" name="role" id="role2" value="ROLE_FAMILY,ROLE_USER" checked>
	                                <label class="form-check-label" for="role2">
	                                    FAMILY
	                                </label>
	                            </div>
	                            <div class="form-check form-check-inline">
	                                <input class="form-check-input" type="radio" name="role" id="role3" value="ROLE_USER">
	                                <label class="form-check-label" for="role3">
	                                    USER
	                                </label>
	                            </div>
	                        </div>
	                        <div class="d-grid"><button class="btn btn-primary btn-lg" id="submitButton" type="submit">Submit</button></div>
	                    </form>
	                </div>
	            </div>
	        </div>
	    </div>
	</section>

  </body>
</html>
