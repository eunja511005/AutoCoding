
<div class="container-fluid px-4">
	<h1 class="mt-4">Tables</h1>
	<ol class="breadcrumb mb-4">
		<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
		<li class="breadcrumb-item active">Tables</li>
	</ol>
	<div class="card mb-4">
		<div class="card-body">
		    <div class="row">
		        <div class="col-md-4 mb-3">
		            <label for="title">Title:</label>
		            <input type="text" id="title" name="title" class="form-control">
		        </div>
		        <div class="col-md-4 mb-3">
		            <label for="is_secret">Is Secret:</label>
		            <select id="is_secret" name="is_secret" class="form-control">
		                <option value="">전체</option>
		                <option value="1">비밀글</option>
		                <option value="0">일반글</option>
		            </select>
		        </div>
		        <div class="col-md-4 mb-3">
		            <label for="created_at">Created At:</label>
		            <input type="text" id="created_at" name="created_at" class="form-control">
		        </div>
		    </div>
		    <div class="text-end">
		        <button id="search_btn" class="btn btn-primary">Search</button>
		    </div>
		</div>
	</div>
	
	<div class="card mb-4">
		<div class="card-header">
			<i class="fas fa-table me-1"></i> DataTable Example
		</div>
		<div class="card-body">
		    <table id="table_id">
		        <thead>
		            <tr>
		                <th>ID</th>
		                <th>Title</th>
		                <th>Is Secret</th>
		                <th>Created At</th>
		            </tr>
		        </thead>
		        <tbody></tbody>
		    </table>
		</div>
	</div>
</div>