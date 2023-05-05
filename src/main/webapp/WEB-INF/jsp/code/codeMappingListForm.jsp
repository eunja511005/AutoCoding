<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
				Code Mapping</h2>

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
			    <table id="codeMappingTable" class="display" style="width:100%">
			        <thead>
					    <tr>
					        <th colspan="9">
								<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#newCodeMappingModal">New</button>
					        </th>
					    </tr>       
				        <tr>
				            <th>CodeMappingName</th>
				            <th>CodeMappingDescription</th>
				            <th>FromCodeId</th>
				            <th>ToCodeId</th>
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
<div class="modal fade" id="newCodeMappingModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title" id="exampleModalLabel">Input Fields</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form id="newCodeMappingForm" novalidate>
          <input type="hidden" id="codeMappingId" name="codeMappingId" value="">
          <div class="mb-3">
            <label for="codeMappingName" class="form-label">Code Mapping Name</label><span class="text-danger">*</span></label>
			<select class="form-select form-select-sm" id="codeMappingName" name="codeMappingName" required>
			  <option value="">Select an option...</option>
			</select>            
          </div>
          <div class="mb-3">
            <label for="codeMappingDescription" class="form-label">Code Mapping Description</label>
            <input type="text" class="form-control" id="codeMappingDescription" name="codeMappingDescription">
          </div>
          <div class="mb-3">
            <label for="fromCodeId" class="form-label">From Code ID<span class="text-danger">*</span></label>
      		<select class="form-select form-select-sm" id="fromCodeId" name="fromCodeId" required>
			  <option value="">Select an option...</option>
			</select> 
          </div>
          <div class="mb-3">
            <label for="toCodeId" class="form-label">To Code ID<span class="text-danger">*</span></label>
      		<select class="form-select form-select-sm" id="toCodeId" name="toCodeId" required>
			  <option value="">Select an option...</option>
			</select>
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
	<script type="text/javascript" src="/js/code/codeMappingListForm.js"></script>

</body>
</html>