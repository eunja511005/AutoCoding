<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
	<jsp:include page="/WEB-INF/jsp/common/common.jsp"/>
    <title>Signin</title>
  </head>
  
<body id="page-top">
	<!-- Navigation-->
	<jsp:include page="/WEB-INF/jsp/common/nav.jsp" />


	<section class="page-section" id="contact">
		<div class="container my-5">

			<!-- 페이지 제목 -->
			<h2
				class="page-section-heading text-center text-uppercase
				text-secondarymb-0">
				Code Lists</h2>

			<!-- 페이지 제목 아래 구분선 -->
			<div class="divider-custom">
				<div class="divider-custom-line"></div>
				<div class="divider-custom-icon">
					<i class="fas fa-star"></i>
				</div>
				<div class="divider-custom-line"></div>
			</div>

			<!-- 페이지별 본문 -->
			<div class="container-fluid my-4">
			    <table id="codeTable" class="display" style="width:100%">
			        <thead>
					    <tr>
					        <th colspan="13">
								<form id="my-form" action="/code/saveForm" method="post">
								  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								  <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newCodeModal">New</button>
								</form>
					        </th>
					    </tr>       
				        <tr>
				            <th>CodeGroupId</th>
				            <th>CodeGroupName</th>
				            <th>CodeGroupDescription</th>
				            <th>CodeId</th>
				            <th>CodeLevel</th>
				            <th>CodeSecuence</th>
				            <th>CodeName</th>
				            <th>CodeDescription</th>
				            <th>Enable</th>
				            <th>User</th>
				            <th>Time</th>
				            <th>View</th>
				            <th>Delete</th>
				        </tr>
			        </thead>
			        <tbody>
			        </tbody>
			    </table>
			</div>
			
		</div>
	</section>			
			
			<!-- Modal -->
			<div class="modal fade" id="newCodeModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog modal-dialog-centered">
			    <div class="modal-content">
			      <div class="modal-header bg-primary text-white">
			        <h5 class="modal-title" id="exampleModalLabel">Input Fields</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			        <form id="newCodeForm" novalidate>
			          <input type="hidden" id="id" name="id" value="">
			          <div class="mb-3">
			            <label for="codeGroupId" class="form-label">Code Group ID<span class="text-danger">*</span></label>
			            <input type="text" class="form-control" id="codeGroupId" name="codeGroupId" required>
			          </div>
			          <div class="mb-3">
			            <label for="codeGroupName" class="form-label">Code Group Name</label>
			            <input type="text" class="form-control" id="codeGroupName" name="codeGroupName">
			          </div>
			          <div class="mb-3">
			            <label for="codeGroupDescription" class="form-label">Code Group Description</label>
			            <input type="text" class="form-control" id="codeGroupDescription" name="codeGroupDescription">
			          </div>
			          <div class="mb-3">
			            <label for="codeId" class="form-label">Code ID<span class="text-danger">*</span></label>
			            <input type="text" class="form-control" id="codeId" name="codeId" required>
			          </div>
			          <div class="mb-3">
			            <label for="codeLevel" class="form-label">Code Level</label>
			            <input type="number" class="form-control" id="codeLevel" name="codeLevel">
			          </div>
			          <div class="mb-3">
			            <label for="codeSequence" class="form-label">Code Sequence</label>
			            <input type="number" class="form-control" id="codeSequence" name="codeSequence">
			          </div>
			          <div class="mb-3">
			            <label for="codeName" class="form-label">Code Name</label>
			            <input type="text" class="form-control" id="codeName" name="codeName">
			          </div>
			          <div class="mb-3">
			            <label for="codeDescription" class="form-label">Code Description</label>
			            <input type="text" class="form-control" id="codeDescription" name="codeDescription">
			          </div>
			          <div class="mb-3">
			            <div class="form-check">
			              <input class="form-check-input" type="checkbox" id="enable" name="enable">
			              <label class="form-check-label" for="enable">
			                Enable
			              </label>
			            </div>
			          </div>
			        </form>
			      </div>
			      <div class="modal-footer bg-light">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			        <button type="button" class="btn btn-primary">Save changes</button>
			      </div>
			    </div>
			  </div>
			</div>



	<jsp:include page="/WEB-INF/jsp/common/commonScript.jsp"/>

	<!-- this page's javascript -->
	<script type="text/javascript" src="/js/code/codeListForm.js"></script>

</body>
</html>