<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<div class="container-fluid px-4">
    <div class="breadcrumb-container">
        <ol class="breadcrumb mt-2 justify-content-end">
            <li class="breadcrumb-item">Interface</li>
            <li class="breadcrumb-item">Layout</li>
            <li class="breadcrumb-item active"><fmt:message key="page.title.email" /></li>
        </ol>
    </div>
    
    <h1 class="page-title mb-4"><fmt:message key="page.title.email" /></h1> 

	<div class="card mb-4" style="width:100%">
	  <div class="card-header">
	    <i class="fas fa-envelope"></i> <fmt:message key="send.email" />
	  </div>
	  <div class="card-body">
	    <form id="emailForm">
		      <div class="row mb-1">
		        <label for="to" class="col-sm-2 col-form-label">To Email</label>
		        <div class="col-sm-10">
		          <input type="email" class="form-control" id="to" name="to" required>
		        </div>
		      </div>
		      <div class="row mb-1">
		        <label for="subject" class="col-sm-2 col-form-label">Subject</label>
		        <div class="col-sm-10">
		          <input type="text" class="form-control" id="subject" name="subject" required>
		        </div>
		      </div>
		      <div class="row mb-1">
		        <label for="body" class="col-sm-2 col-form-label">Body</label>
		        <div class="col-sm-10">
		          <textarea type="text" class="form-control" id="body" name="body" rows="10" required></textarea>
		        </div>
		      </div>
	    </form>
	  </div>
	</div>
	
    <div class="card mb-4">
		<div class="card-body">
		    <div class="container mt-5">
		        <div class="mb-3">
		        	<div class="row">
		            	<div class="col">
		                	<div class="form-group">
		                    	<input type="file" class="form-control-file" id="fileInput" name="file" multiple>
		                	</div>
		               </div>
		        	</div>
		        </div>
		        
		        <div class="border p-3">
		            <h4>Selected Files</h4>
		            <ul class="list-group" id="fileList"></ul>
		        </div>
		                
		    </div>
		</div>
    </div>	 
    
	<div class="row mt-3">
		<div class="col text-end">
			<button class="btn btn-primary" id="sendEmailButton"><i class="fas fa-envelope"></i> Send Email 
			<span class="spinner-border spinner-border-sm d-none" role="status" aria-hidden="true"></span></button>
		</div>	        
	</div> 	
	
	
</div>

<script src="/js/main/content/email.js"></script>