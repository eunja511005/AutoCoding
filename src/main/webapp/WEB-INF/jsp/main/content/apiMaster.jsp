<!-- 
    This software is protected by copyright laws and international copyright treaties.
    The ownership and intellectual property rights of this software belong to the @autoCoding.
    Unauthorized reproduction, distribution, modification, sale, or commercial use of this software is strictly prohibited
    and may result in legal consequences.
    This software is licensed to the user and must be used in accordance with the terms of the license.
    Under no circumstances should the source code or design of this software be disclosed or leaked.
    The @autoCoding shall not be liable for any loss or damages.
    Please read the license and usage permissions carefully before using.
-->

<div class="container-fluid px-4">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="mt-4">ApiMaster</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">ApiMaster</li>
	</ol>

	<div class="card mb-4">
		<div class="card-header" id="fieldHeading">
			<h1 class="mb-0">
				<button id="newField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					New ApiMaster
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<form id="apiMasterForm" class="row g-2 align-items-center">
								<input type="hidden" id="id" name="id">
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="apiName" name="apiName" placeholder="Enter apiName" required>
										<label for="apiName">API_NAME</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="apiDescription" name="apiDescription" placeholder="Enter apiDescription" required>
										<label for="apiDescription">API_DESCRIPTION</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="callUrl" name="callUrl" placeholder="Enter callUrl" required>
										<label for="callUrl">CALL_URL</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="direction" name="direction" placeholder="Enter direction" required>
										<label for="direction">DIRECTION</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="author" name="author" placeholder="Enter author" required>
										<label for="author">AUTHOR</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="number" class="form-control" id="callMax" name="callMax" placeholder="Enter callMax" required>
										<label for="callMax">CALL_MAX</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="httpMethod" name="httpMethod" placeholder="Enter httpMethod" required>
										<label for="httpMethod">HTTP_METHOD</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="logYn" name="logYn" placeholder="Enter logYn" required>
										<label for="logYn">LOG_YN</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="systemName" name="systemName" placeholder="Enter systemName" required>
										<label for="systemName">SYSTEM_NAME</label>
									</div>
								</div>
								<div class="col-md text-center">
									<button type="submit" class="btn btn-outline-primary"><i class="fas fa-save"></i></button>
									<button type="reset" class="btn btn-outline-secondary" id="clear-btn"><i class="fas fa-undo"></i></button>
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
				<button id="searchField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					Search ApiMaster
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body" style="width:100%">
				<table id="apiMasterTable" class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>API_NAME</th>
							<th>API_DESCRIPTION</th>
							<th>CALL_URL</th>
							<th>DIRECTION</th>
							<th>AUTHOR</th>
							<th>CALL_MAX</th>
							<th>HTTP_METHOD</th>
							<th>LOG_YN</th>
							<th>SYSTEM_NAME</th>
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

</div>

<script src="/js/main/content/apiMaster.js"></script>
