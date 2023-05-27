<div class="container-fluid px-4">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="mt-4">AccessControl</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">AccessControl</li>
	</ol>

	<div class="card mb-4">
		<div class="card-header" id="fieldHeading">
			<h1 class="mb-0">
				<button id="newField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					New AccessControl
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<form id="accessControlForm" class="row g-2 align-items-center">
								<input type="hidden" id="id" name="id">
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="userId" name="userId" placeholder="Enter userId" required>
										<label for="userId">USER_ID</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="roleId" name="roleId" placeholder="Enter roleId" required>
										<label for="roleId">ROLE_ID</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="number" class="form-control" id="relation" name="relation" placeholder="Enter relation" required>
										<label for="relation">RELATION</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="resourceId" name="resourceId" placeholder="Enter resourceId" required>
										<label for="resourceId">RESOURCE_ID</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="permission" name="permission" placeholder="Enter permission" required>
										<label for="permission">PERMISSION</label>
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
				<button id="searchField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					Search AccessControl
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body" style="width:100%">
				<table id="accessControlTable" class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>USER_ID</th>
							<th>ROLE_ID</th>
							<th>RELATION</th>
							<th>RESOURCE_ID</th>
							<th>PERMISSION</th>
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

<script src="/js/main/content/accessControl.js"></script>
