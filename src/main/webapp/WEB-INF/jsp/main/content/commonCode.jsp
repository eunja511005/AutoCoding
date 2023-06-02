<div class="container my-4">
	<div class="row">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h1 class="mt-4">Common Code</h1>
		</div>
	</div>
	<div class="row my-4">
    <div class="col-md-6">
      <form id="commonCodeForm">
        <div class="row my-4">
          <div class="col-md-6">
            <div class="form-floating">
              <input type="text" class="form-control" id="codeGroup" name="codeGroup" placeholder="Enter code group" required>
              <label for="codeGroup"><i class="fas fa-code-branch"></i> Code Group</label>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-floating">
              <input type="text" class="form-control" id="code" name="code" placeholder="Enter code" required>
              <label for="code"><i class="fas fa-code"></i> Code</label>
            </div>
          </div>
        </div>        
        
        <div class="row my-4">
          <input type="hidden" id="id" name="id">
          <div class="col-md-6">
            <div class="form-floating">
              <input type="text" class="form-control" id="value" name="value" placeholder="Enter value" required>
              <label for="value"><i class="fas fa-pen"></i> Value</label>
            </div>
          </div>
          <div class="col-md-6">
            <div class="form-floating">
              <input type="number" class="form-control" id="codeOrder" name="codeOrder" placeholder="Enter code" required>
              <label for="code"><i class="fas fa-code"></i> Code Order</label>
            </div>
          </div>
        </div>
        <div class="row my-4">
          <div class="col-md-12">
            <button type="submit" class="btn btn-outline-primary"><i class="fas fa-save"></i> Save</button>
            <button type="reset" class="btn btn-outline-secondary"><i class="fas fa-undo"></i> Clear</button>
          </div>      
        </div>
      </form>
    </div>
    <div class="col-md-6">
      <table id="commonCodeTable" class="table table-hover" style="width:100%">
        <thead>
          <tr>
            <th>Code Group</th>
            <th>Code</th>
            <th>Value</th>
            <th>Code Order</th>
			<th>Edit</th>
	        <th>Delete</th>
          </tr>
        </thead>
        <tbody>
          <!-- Common code data will be populated here -->
        </tbody>
      </table>
    </div>
  </div>
</div>

<script src="/js/main/content/commonCode.js"></script>