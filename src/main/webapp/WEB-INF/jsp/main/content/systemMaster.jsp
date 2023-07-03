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
		<h1 class="mt-4">SystemMaster</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">SystemMaster</li>
	</ol>

	<div class="card mb-4">
		<div class="card-header" id="fieldHeading">
			<h1 class="mb-0">
				<button id="newField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					New SystemMaster
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<form id="systemMasterForm" class="row g-2 align-items-center">
								<input type="hidden" id="id" name="id">
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="systemName" name="systemName" placeholder="Enter systemName" required>
										<label for="systemName">SYSTEM_NAME</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="systemDescription" name="systemDescription" placeholder="Enter systemDescription" required>
										<label for="systemDescription">SYSTEM_DESCRIPTION</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="systemAdmin" name="systemAdmin" placeholder="Enter systemAdmin" required>
										<label for="systemAdmin">SYSTEM_ADMIN</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="homepage" name="homepage" placeholder="Enter homepage" required>
										<label for="homepage">HOMEPAGE</label>
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
					Search SystemMaster
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body" style="width:100%">
				<table id="systemMasterTable" class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>SYSTEM_NAME</th>
							<th>SYSTEM_DESCRIPTION</th>
							<th>SYSTEM_ADMIN</th>
							<th>HOMEPAGE</th>
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

<script src="/js/main/content/systemMaster.js"></script>
