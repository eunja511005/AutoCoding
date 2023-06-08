<!-- 
    This software is protected by copyright laws and international copyright treaties.
    The ownership and intellectual property rights of this software belong to the developer.
    Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
    and may result in legal consequences.
    This software is licensed to the user and must be used in accordance with the terms of the license.
    Under no circumstances should the source code or design of this software be disclosed or leaked.
    The developer shall not be liable for any loss or damages.
    Please read the license and usage permissions carefully before using.
-->

<div class="container-fluid px-4">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="mt-4">ErrorHist</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">ErrorHist</li>
	</ol>
	
	<div class="card mb-4" style="max-height: none;">
	   <div class="card-header" id="errorField">
	        <i class="fas fa-times-circle"></i> ERROR_MSG
	   </div>	
	   <div class="card-body">
		    <div class="form-control form-control-plaintext" id="errorMsg" name="errorMsg" style="overflow: auto;" readonly></div>
	   </div>
	</div>		

	<div class="card mb-4">
		<div class="card-header" id="solutionField">
	        <i class="fas fa-check"></i> SOLUTION
		</div>
		<div class="card-body">
			<div class="container-fluid">
				<div class="row">
					<div class="col-12">
						<form id="errorHistForm" class="row g-2 align-items-center">
						  <input type="hidden" id="id" name="id">
						  
							<div class="col-md-1 col-lg-2">
								<div class="form-floating">
									<select class="form-select" id="category" name="category" required></select>
									<label for="category">CATEGORY</label>
								</div>
							</div>
							<div class="col-md-1 col-lg-1">
								<div class="form-floating">
									<select class="form-select" id="severity" name="severity" required></select>
									<label for="severity">SEVERITY</label>
								</div>
							</div>
							<div class="col-md-1 col-lg-1">
								<div class="form-floating">
									<select class="form-select" id="status" name="status" required></select>
									<label for="status">STATUS</label>
								</div>									
							</div>						  
							<div class="col-md-1 col-lg-2">
								<div class="form-floating">
									<select class="form-select" id="responsiblePerson" name="responsiblePerson" required></select>
									<label for="responsiblePerson">PIC</label>
								</div>									
							</div>						  
						  
						  <div class="col-md-6 col-lg-5">
						    <div class="form-floating">
						      <textarea class="form-control" id="solutionMsg" name="solutionMsg" rows="5" style="resize: none; max-height: 80vh;"></textarea>
						    </div>
						  </div>
						  
						  <div class="col-md-2 col-lg-1 text-end">
						    <button type="submit" class="btn btn-outline-primary"><i class="fas fa-save"></i></button>
						    <button type="reset" class="btn btn-outline-secondary" id="clear-btn"><i class="fas fa-undo"></i></button>
						  </div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="card mb-4" style="width:100%">
		<div class="card-header" id="searchField">
	        <i class="fas fa-border-all"></i> ErrorHist Lists
		</div>
		<div class="card-body" style="width:100%">
			<table id="errorHistTable" class="table table-hover" style="width:100%">
				<thead>
					<tr>
						<th>CATEGORY</th>
						<th>SEVERITY</th>
						<th>STATUS</th>
						<th>PIC</th>
						<th>CREATED_DT</th>
						<th>ERROR_MSG</th>
						<th>SOLUTION_MSG</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>

</div>

<script src="/js/main/content/errorHist.js"></script>
