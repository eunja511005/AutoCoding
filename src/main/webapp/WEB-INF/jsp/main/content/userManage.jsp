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
		<h1 class="mt-4">UserManage</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">UserManage</li>
	</ol>

	<div class="card mb-4">
		<div class="card-header" id="fieldHeading">
			<h1 class="mb-0">
				<button id="newField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					New UserManage
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-12">
							<form id="userManageForm" class="row g-3 align-items-center">
							  <div class="col-md-6">
							    <div class="form-floating">
							      <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" required>
							      <label for="username">Username</label>
							    </div>
							  </div>
							  <div class="col-md-6">
							    <div class="form-floating">
							      <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
							      <label for="password">Password</label>
							    </div>
							  </div>
							  <div class="col-md-6">
							    <div class="form-floating">
							      <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required>
							      <label for="email">Email</label>
							    </div>
							  </div>
							  <div class="col-md-6">
							    <div class="form-floating">
							      <input type="text" class="form-control" id="role" name="role" placeholder="Enter role" required>
							      <label for="role">Role</label>
							    </div>
							  </div>
							  <div class="col-md-6">
							    <div class="d-flex">
							      <input type="file" class="form-control" id=formFile name="file" accept="image/*" required>
							      <img id="picture-preview" src="" alt="Picture Preview" style="max-height: 40px; margin-left: 10px; display: none;">
							    </div>
							  </div>
							  <div class="col-md-6 d-flex align-items-center justify-content-center">
							    <div class="form-check">
							      <input class="form-check-input" type="checkbox" id="enable" name="enable">
							      <label class="form-check-label" for="enable">Enable</label>
							    </div>
							  </div>
							  <div class="col-12 text-center">
							    <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Save</button>
							    <button type="reset" class="btn btn-secondary" id="clear-btn"><i class="fas fa-undo"></i> Reset</button>
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
					Search UserManage
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body" style="width:100%">
				<table id="userManageTable" class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>USERNAME</th>
							<th>EMAIL</th>
							<th>ROLE</th>
							<th>ENABLE</th>
							<th>LastLoginDT</th>
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

<script src="/js/main/content/userManage.js"></script>
