<div class="container-fluid px-4">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="mt-4">RelationShip</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">RelationShip</li>
	</ol>

	<div class="card mb-4">
		<div class="card-header" id="fieldHeading">
			<h1 class="mb-0">
				<button id="newField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					New RelationShip
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<form id="relationShipForm" class="row g-2 align-items-center">
								<input type="hidden" id="id" name="id">
								<div class="col-md">
									<div class="form-floating">
										<input type="number" class="form-control" id="relation" name="relation" placeholder="Enter relation" required>
										<label for="relation">RELATION</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="fromUser" name="fromUser" placeholder="Enter fromUser" required>
										<label for="fromUser">FROM_USER</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="toUser" name="toUser" placeholder="Enter toUser" required>
										<label for="toUser">TO_USER</label>
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
					Search RelationShip
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body" style="width:100%">
				<table id="relationShipTable" class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>RELATION</th>
							<th>FROM_USER</th>
							<th>TO_USER</th>
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

<script src="/js/main/content/relationShip.js"></script>
