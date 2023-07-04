<div class="container-fluid px-4">
    <div class="breadcrumb-container">
        <ol class="breadcrumb mt-2 justify-content-end">
            <li class="breadcrumb-item">Interface</li>
            <li class="breadcrumb-item">Layout</li>
            <li class="breadcrumb-item active">Real Estate Price</li>
        </ol>
    </div>
    
    <h1 class="page-title mb-4">Real Estate Price</h1>    

	<div class="card mb-4" style="width:100%">
	  <div class="card-header">
	    <i class="fas fa-search"></i> Search
	  </div>
	  <div class="card-body">
	    <form id="searchForm" >
	      <div class="row mb-1">
	        <label for="searchMonth" class="col-sm-1 col-form-label" style="padding-left: 20px;">거래월</label>
	        <div class="col-sm-5">
				<div class="col">
					<input type="month" class="form-control" id="searchMonth" name="searchMonth">
				</div>
	        </div>
	        <label for="lawCode" class="col-sm-1 col-form-label" style="padding-left: 20px;">법정동</label>
	        <div class="col-sm-5">
	          <select class="form-select" id="lawCode" name="lawCode"></select>
	        </div>	        
	      </div>
	      <div class="row mt-3">
	        <div class="col text-end">
	          <button class="btn btn-primary" id="searchButton"><i class="fas fa-search"></i> Search</button>
	        </div>
	      </div>
	    </form>
	  </div>
	</div>
	
	<div class="card mb-4" style="width:100%">
		<div class="card-header">
	        <i class="fas fa-border-all"></i> Table Lists
		</div>
		<div class="card-body" style="width:100%">
		    <div class="d-flex justify-content-end mb-3">
		      <button id="addButton" class="btn btn-primary"><i class="fas fa-plus"></i> Add</button>
		    </div>
			<table id="dataTable" class="table table-hover" style="width:100%">
				<thead>
					<tr>
						<th>거래금액</th>
						<th>거래유형</th>
						<th>건축년도</th>
						<th>년</th>
						<th>법정동</th>
						<th>아파트</th>
						<th>전용면적</th>
						<th>층</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>    
	
    
	<div class="card mb-4" style="width:100%">
	  <div class="card-header">
	    <i class="fas fa-border-all"></i> Table Contents
	  </div>
	  <div class="card-body" style="width:100%">
	    <ul class="nav nav-tabs" id="myTab" role="tablist"></ul>
	    <div class="tab-content mt-2" id="myTabContent"></div>
	  </div>
	</div>	
    
    <div class="card mb-4">
		<div class="card-body">
		    <div class="container mt-5">
		        <div class="mb-3">
		        	<div class="row">
		            	<div class="col">
		                	<div class="form-group">
		                    	<input type="file" class="form-control-file" id="fileInput" name="file" multiple>
		                	</div>
		               </div>
		               <div class="col text-end">
		                    <button id="uploadButton" class="btn btn-primary">
		                        <i class="fas fa-cloud-upload-alt me-2"></i> Upload
		                    </button>
		            	</div>
		        	</div>
		        </div>
		        
		        <div class="border p-3">
		            <h4>Selected Files</h4>
		            <ul class="list-group" id="fileList"></ul>
		        </div>
		        
				<div class="border p-3 mt-4" id="fileListContainer">
					<h4>Uploaded Files</h4>
					<ul id="fileList" class="list-group"></ul>
				</div>		        
		    </div>
		</div>
    </div>	  
    
    <div style="height: 10vh"></div>
        
  
    <div class="card mb-4">
        <div class="card-body">
            <p class="mb-0">
                This page is an example of using static navigation. By removing the
                <code>.sb-nav-fixed</code>
                class from the
                <code>body</code>
                , the top navigation and side navigation will become static on scroll. Scroll down this page to see an example.
            </p>
        </div>
    </div>  
  
</div>

<script src="/js/external/pubdata/realEstatePrice.js"></script>