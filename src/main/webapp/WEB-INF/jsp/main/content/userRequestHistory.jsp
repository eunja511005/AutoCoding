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
		<h1 class="mt-4">UserRequestHistory</h1>
	</div>

	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">UserRequestHistory</li>
	</ol>
	
	<div class="card mb-4" style="max-height: none;">
        <div class="card-header" id="reqField" aria-expanded="true" aria-controls="fieldCollapse">
            <i class="fas fa-upload"></i>
            REQ_DATA
        </div>	
	    <div class="card-body" aria-labelledby="fieldHeading">
	        <div class="form-control form-control-plaintext" id="reqData" name="reqData" style="overflow: auto;" readonly></div>
	    </div>
	</div>
	
	<div class="card mb-4" style="max-height: none;">
        <div class="card-header" id="resField" aria-expanded="true" aria-controls="fieldCollapse">
            <i class="fas fa-download"></i>
            RES_DATA
        </div>	
	    <div class="card-body" aria-labelledby="fieldHeading">
	        <div class="form-control form-control-plaintext" id="resData" name="resData" style="overflow: auto;" readonly></div>
	    </div>
	</div>

	<div class="card mb-4" style="width:100%">
		<div class="card-header" id="fieldHeading">
			<h1 class="mb-0">
				<button id="searchField" class="btn btn-link" type="button" aria-expanded="true" aria-controls="fieldCollapse">
					Search UserRequestHistory
				</button>
			</h1>
		</div>
		<div id="fieldCollapse" class="collapse show" aria-labelledby="fieldHeading">
			<div class="card-body" style="width:100%">
				<table id="userRequestHistoryTable" class="table table-hover" style="width:100%">
					<thead>
						<tr>
							<th>URL</th>
							<th>METHOD</th>
							<th>REQ_USER</th>
							<th>REQ_IP</th>
							<th>CREATE_DT</th>
							<th>Content</th>
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

<script src="/js/main/content/userRequestHistory.js"></script>
