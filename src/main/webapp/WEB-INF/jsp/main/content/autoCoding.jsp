	<div class="container-fluid px-4">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h1 class="mt-4">Auto Coding</h1>
		</div>
		<ol class="breadcrumb mb-4">
			<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
			<li class="breadcrumb-item active">Tables</li>
		</ol>
		
	<div class="row">	
	    <div class="col-md-12 mb-4">
			<div class="row">
			      <form id="commonCodeForm">
			        <input type="hidden" id="id" name="id">
			     	<div class="row">
				      <div class="col-md-12">
				        <div class="form-floating">
				          <input type="text" class="form-control" id="codeGroup" name="codeGroup" placeholder="Enter code group" required>
				          <label for="codeGroup"><i class="fas fa-code-branch"></i> Code Group</label>
				        </div>
				      </div>
				    </div>
			        <div class="row my-4">
			          <div class="col-md-6">
			            <div class="form-floating">
			              <input type="text" class="form-control" id="code" name="code" placeholder="Enter code" required>
			              <label for="code"><i class="fas fa-code"></i> Code</label>
			            </div>
			          </div>
			          <div class="col-md-6">
			            <div class="form-floating">
			              <input type="text" class="form-control" id="value" name="value" placeholder="Enter value" required>
			              <label for="value"><i class="fas fa-pen"></i> Value</label>
			            </div>
			          </div>
			        </div>
			        <div class="row my-4">
			          <div class="col-md-12">
			            <button type="submit" class="btn btn-outline-primary"><i class="fas fa-save"></i> Save</button>
			            <button type="reset" class="btn btn-outline-secondary"><i class="fas fa-undo"></i> Clear</button>
			          </div>      
	        		</div>    
			      </form>			
			</div>   
	    	<div class="row">
		      <table id="commonCodeTable" class="table table-hover" style="width:100%">
		        <thead>
		          <tr>
		            <th>Code Group</th>
		            <th>Code</th>
		            <th>Value</th>
					<th>Edit</th>
			        <th>Delete</th>
		          </tr>
		        </thead>
		        <tbody>
		          <!-- Common code data will be populated here -->
		        </tbody>
		      </table>
			</div>			   
	    </div>	
	    <div class="col-md-12">
	        <div class="row">
			      <form id="subjectForm" novalidate>
			     	<div class="row">
				      <div class="col-md-12">
				        <div class="form-floating">
				          <input type="text" class="form-control" id="subject" name="subject" placeholder="Enter subject" required>
				          <label for="subject"><i class="fas fa-puzzle-piece"></i> Subject</label>
				          <div class="invalid-feedback">Please enter subject</div>
				        </div>
				      </div>
				    </div>
			        <div class="row my-4">
			          <div class="col-md-12">
			            <button type="reset" class="btn btn-outline-success" id="btn_generation"><i class="fas fa-lightbulb"></i> Generation</button>
			          </div>      
	        		</div>    
			      </form>
	    	</div>
			<div class="row">
				<ul id="sourceList" class="list-group"></ul>
			</div>	      
    	</div>
  </div>
</div>

<script src="/js/main/content/autoCoding.js"></script>