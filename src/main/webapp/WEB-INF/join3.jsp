<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>YB's Register Page</title>
<jsp:include page="jsp/common/common.jsp"/>
</head>
<body>
	<section class="vh-100" style="background-color: #eee;">
	  <div class="container h-100">
	    <div class="row d-flex justify-content-center align-items-center h-100">
	      <div class="col-lg-12 col-xl-11">
	        <div class="card text-black" style="border-radius: 25px;">
	          <div class="card-body p-md-5">
	            <div class="row justify-content-center">
	              <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
	
	                <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>
	
	                        <form id="uploadForm" enctype="multipart/form-data">
            <div class="form-group">
                <label for="file">Select files:</label>
                <input type="file" name="file[]" id="file" accept="image/jpeg, image/png" multiple />
            </div>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" class="form-control" required />
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" name="password" id="password" class="form-control" required />
            </div>            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" class="form-control" required />
            </div>
			<div class="form-floating mb-3">
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="role" id="role1"
						value="ROLE_ADMIN,ROLE_FAMILY,ROLE_USER"> <label
						class="form-check-label" for="role1"> Admin </label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="role" id="role2"
						value="ROLE_FAMILY,ROLE_USER" checked> <label
						class="form-check-label" for="role2"> FAMILY </label>
				</div>
				<div class="form-check form-check-inline">
					<input class="form-check-input" type="radio" name="role" id="role3"
						value="ROLE_USER"> <label class="form-check-label"
						for="role3"> USER </label>
				</div>
			</div>
			<div id="preview"></div>
            <button type="submit" class="btn btn-primary" id="submitButton">Upload</button>
        </form>
	
	              </div>
	              
	              <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">
	
	                <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
	                  class="img-fluid" alt="Sample image">
	
	              </div>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
	</section>
	
	<script>
            function preview() {
                frame.src = URL.createObjectURL(event.target.files[0]);
            }
    </script>
    
	<script type="text/javascript" src="/js/join3.js"></script>
	
</body>
</html>