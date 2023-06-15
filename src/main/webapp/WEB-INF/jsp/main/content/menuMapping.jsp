<div class="container-fluid px-4">
	<div class="d-flex justify-content-between align-items-center mb-4">
		<h1 class="mt-4">MenuMapping</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">MenuMapping</li>
	</ol>
	
	<div class="card mb-4" style="width:100%">
		<div class="card-header" id="roleHeader">
	        <i class="fas fa-check"></i> Role
		</div>	
		<div class="card-body" style="width:100%">
			<div class="container-fluid">
				<div class="row">
					<div class="col-12">
						<form id="menuControlForm" class="row g-2 align-items-center">
							<div class="col-md">
								<div class="form-floating">
									<select class="form-select" id="roleId" name="roleId" required></select>
									<label for="roleId">ROLE_ID</label>
								</div>									
							</div>
							<div class="col-md text-center">
								<button id="save-btn" class="btn btn-outline-primary"><i class="fas fa-save"></i></button>
							</div>
						</form>
					</div>
				</div>
			</div>			
		</div>
	</div>	

	<div class="card mb-4" style="width:100%">
		<div class="card-header" id="menuMappingHeader">
	        <i class="fas fa-border-all"></i> Menu Mapping
		</div>	
		<div class="card-body" style="width:100%">
			<div id="menuMappingContainer"></div>
		</div>
	</div>

</div>

<script src="/js/main/content/menuMapping.js"></script>
