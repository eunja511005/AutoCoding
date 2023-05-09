	<div class="container-fluid px-4">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h1 class="mt-4">Forum</h1>
			<button id="newPostModalButton" class="btn btn-light bg-primary text-white"><i class="fas fa-plus"></i> New</button>
		</div>
		<ol class="breadcrumb mb-4">
			<li class="breadcrumb-item"><a href="index.html">Dashboard</a></li>
			<li class="breadcrumb-item active">Tables</li>
		</ol>

		<div class="card mb-4">
			<div class="card-body">
				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="col-4 mb-3">
								<label for="title">Title</label> 
								<input type="text" id="title" name="title" class="form-control">
							</div>
							<div class="col-4 mb-3">
								<label for="is_secret">Category</label> 
								<select id="is_secret" name="is_secret" class="form-control">
									<option value="">ALL</option>
									<option value="1">Secret</option>
									<option value="0">Normal</option>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="col-6 mb-3">
								<label for="start_date">Start Date</label> 
								<input type="date" id="start_date" name="start_date" class="form-control">
							</div>
							<div class="col-6 mb-3">
								<label for="end_date">End Date</label> 
								<input type="date" id="end_date" name="end_date" class="form-control">
							</div>
						</div>
					</div>
				</div>
				<div class="text-end">
					<button id="search_btn" class="btn btn-light"><i class="fas fa-search"></i> Search</button>
				</div>
			</div>
		</div>

		<div class="card mb-4">
			<div class="card-header">
				<i class="fas fa-table me-1"></i> DataTable Example
			</div>
			<div class="card-body">
			    <table id="example" class="display" style="width:100%">
			        <thead>			        
			            <tr>
			                <th>Title</th>
			                <th>Category</th>
			                <th>Date</th>
			                <th>Contents</th>
	            			<th>Delete</th>
			            </tr>
			        </thead>
			        <tbody></tbody>
			    </table>
			</div>
		</div>
	</div>
	
	
<div class="modal fade" id="newPostModal" tabindex="-1" aria-labelledby="newPostModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="newPostModalLabel">New Post</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="card mb-4">
          <div class="card-body">
            <form id="postForm">
              <input type="hidden" id="modalId" name="id" value="" />
              <div class="row my-4">
                <div class="col-md-12">
                  <div class="form-check">
                    <label class="form-check-label" for="secret">Secret</label> 
                    <input type="checkbox" class="form-check-input" id="modaSecret" name="secret">
                  </div>
                </div>
              </div>
              <div class="row my-4">
                <div class="col-md-12">
                  <div>
                    <label for="title" class="form-label">Title</label> 
                    <input type="text" class="form-control" id="modaTitle" name="title" placeholder="title" required>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-md-12">
                  <div>
                    <label for="content" class="form-label">Content</label>
                    <textarea class="form-control" id="modalContent" name="content" rows="10"></textarea>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>

        <!-- 댓글 입력창과 댓글 달기 버튼 -->
        <div class="row mb-4">
          <div class="col-md-12">
            <div class="card" id="commentArea">
              <div class="card-body">
                <form id="commentForm">
                  <div class="row">
                    <div class="col-md-12">
                      <div>
                        <label for="comment" class="form-label">Your comment</label>
                        <textarea class="form-control" id="commentContent" name="content" rows="3" placeholder="Please log in to leave a comment"></textarea>
                      </div>
                    </div>
                  </div>
                </form>
              </div>
              <div class="card-footer">
                <button type="button" class="btn btn-outline-primary" id="commentButton" style="display: none;">Add Comment</button>
              </div>
            </div>
          </div>
        </div>

		<!-- 댓글 리스트 펼치기/접기 -->
		<div class="mb-3 d-flex justify-content-between align-items-center">
		  <h5 class="mb-0">Comments</h5>
		  <button class="btn btn-sm btn-link" id="comment-list-toggle"><i class="fas fa-chevron-down"></i></button>
		</div>
		<div id="comment-list" style="display: none;"></div>    

      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>

	
	<script src="/js/main/content/list.js"></script>
