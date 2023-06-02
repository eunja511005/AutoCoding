<div class="container-fluid px-4">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="mt-4">Menu</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">Menu</li>
	</ol>

	<div class="card mb-4">
		<div class="card-header" id="fieldHeading">
			<h1 class="mb-0">
				<button id="newField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					New Menu
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<form id="menuForm" class="row g-2 align-items-center">
								<input type="hidden" id="id" name="id">
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="category" name="category" placeholder="Enter category" required>
										<label for="category">CATEGORY</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="number" class="form-control" id="menuLevel" name="menuLevel" placeholder="Enter menuLevel" required>
										<label for="menuLevel">MENU_LEVEL</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<select class="form-select" id="menuAuth" name="menuAuth" required></select>
										<label for="menuAuth">MENU_AUTH</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="menuId" name="menuId" placeholder="Enter menuId" required>
										<label for="menuId">MENU_ID</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="menuPath" name="menuPath" placeholder="Enter menuPath" required>
										<label for="menuPath">MENU_PATH</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="menuIcon" name="menuIcon" placeholder="Enter menuIcon" required>
										<label for="menuIcon">MENU_ICON</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="number" class="form-control" id="menuOrder" name="menuOrder" placeholder="Enter menuOrder" required>
										<label for="menuOrder">MENU_ORDER</label>
									</div>
								</div>
								<div class="col-md">
									<div class="form-floating">
										<input type="text" class="form-control" id="parentMenuId" name="parentMenuId" placeholder="Enter parentMenuId" required>
										<label for="parentMenuId">PARENT_MENU_ID</label>
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
					Search Menu
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body" style="width:100%">
				<table id="menuTable" class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>CATEGORY</th>
							<th>MENU_LEVEL</th>
							<th>MENU_AUTH</th>
							<th>MENU_ID</th>
							<th>MENU_PATH</th>
							<th>MENU_ICON</th>
							<th>MENU_ORDER</th>
							<th>PARENT_MENU_ID</th>
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

<script src="/js/main/content/menu.js"></script>
