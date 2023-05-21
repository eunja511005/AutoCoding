<div class="container-fluid px-4">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h1 class="mt-4">Auto Coding</h1>
		</div>
		<ol class="breadcrumb mb-4">
			<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
			<li class="breadcrumb-item active">Tables</li>
		</ol>
		
		<div class="card mb-4">
		    <div class="card-header" id="fieldHeading">
		        <h1 class="mb-0">
		            <button id="newField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
		                New Field
		            </button>
		        </h1>
		    </div>
		    <div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
		        <div class="card-body">
					<div class="container-fluid">
					  <div class="row">
					    <div class="col-12" style="overflow-x: auto;">
					      <form id="autoCodingForm" class="row g-2 align-items-center">
					        <input type="hidden" id="id" name="id">
					        <div class="col-md">
					          <div class="form-floating">
					            <input type="text" class="form-control" id="structureName" name="structureName" placeholder="Enter structureName" required>
					            <label for="structureName">Structure Name</label>
					          </div>
					        </div>
					        <div class="col-md">
					          <div class="form-floating">
					            <input type="text" class="form-control" id="fieldName" name="fieldName" placeholder="Enter fieldName" required>
					            <label for="fieldName">Field Name</label>
					          </div>
					        </div>
					        <div class="col-md">
					          <div class="form-floating">
					            <input type="text" class="form-control" id="fieldType" name="fieldType" placeholder="Enter fieldType" required>
					            <label for="fieldType">Field Type</label>
					          </div>
					        </div>
					        <div class="col-md">
					          <div class="form-floating">
					            <input type="text" class="form-control" id="defaultValue" name="defaultValue" placeholder="Enter defaultValue" required>
					            <label for="defaultValue">Default Value</label>
					          </div>
					        </div>
					        <div class="col-md">
					          <div class="form-floating">
					            <input type="number" class="form-control" id="orderNumber" name="orderNumber" placeholder="Enter orderNumber" required>
					            <label for="orderNumber">Order Number</label>
					          </div>
					        </div>
					        <div class="col-md">
					          <div class="form-floating">
					            <select class="form-select" id="searchable" name="searchable" required>
					              <option value="Y">Yes</option>
					              <option value="N">No</option>
					            </select>
					            <label for="searchable">Searchable</label>
					          </div>
					        </div>
					        <div class="col-md">
					          <div class="form-floating">
					            <select class="form-select" id="primaryKey" name="primaryKey" required>
					              <option value="Y">Yes</option>
					              <option value="N">No</option>
					            </select>
					            <label for="primaryKey">Primary Key</label>
					          </div>
					        </div>
					        <div class="col-md">
					          <div class="form-floating">
					            <select class="form-select" id="nullable" name="nullable" required>
					              <option value="Y">Yes</option>
					              <option value="N">No</option>
					            </select>
					            <label for="nullable">Nullable</label>
					          </div>
					        </div>
					        <div class="col-md">
					          <div class="form-floating">
					            <input type="text" class="form-control" id="description" name="description" placeholder="Enter description" required>
					            <label for="description">Description</label>
					          </div>
					        </div>
					        <div class="col-md text-center">
					          <button type="submit" class="btn btn-outline-primary"><i class="fas fa-save"></i></button>
					          <button type="reset" class="btn btn-outline-secondary"><i class="fas fa-undo"></i></button>
					        </div>
					      </form>
					    </div>
					  </div>
					</div>


		        </div>
		    </div>
		</div>


		<div class="card mb-4" style="width:100%">
		  <div class="card-header" id="fieldHeading">
		    <h1 class="mb-0">
		      <button id="searchField" class="btn btn-link" type="button" data-toggle="collapse" data-target="#fieldCollapse" aria-expanded="true" aria-controls="fieldCollapse">
		        Search Field
		      </button>
		    </h1>
		  </div>
		  <div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
		    <div class="card-body" style="width:100%">
		      <table id="commonCodeTable" class="table table-hover" style="width:100%">
		        <thead>
			      <tr>
			        <th>Structure Name</th>
			        <th>Field Name</th>
			        <th>Description</th>
			        <th>Field Type</th>
			        <th>Searchable</th>
			        <th>Primary Key</th>
			        <th>Nullable</th>
			        <th>Default Value</th>
			        <th>Order Number</th>
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
		</div>		
				
		<div class="card mb-4">
		  <div class="card-header" id="fieldHeading">
		    <h1 class="mb-0">
		      <button id="autoGeneration" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
		        Auto Generation
		      </button>
		    </h1>
		  </div>
		  <div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
		    <div class="card-body">
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
		  </div>
		</div>


		<div class="row">
			<ul id="sourceList" class="list-group"></ul>
		</div>	      
</div>

<script src="/js/main/content/autoCoding.js"></script>
<script>
   Prism.highlightAll();
</script>