<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  


<!-- 다국어 번들 설정(JSP 페이지에서 .properties 파일을 사용) -->
<fmt:setBundle basename="messages" var="msg" />

<!-- JavaScript 내부에 다국어 메시지 변수 선언 -->
<script>
	var alertTitle = '<fmt:message key="alert_title" bundle="${msg}" />';
    var noRowSelectedMessage = '<fmt:message key="no_row_selected" bundle="${msg}" />';
    var multiRowSelectedMessage = '<fmt:message key="multi_row_selected" bundle="${msg}" />';
</script>

<style>
	/* 스타일이 적용되는 상황에 따라 브레드크럼 글자 크기 조정 */
	.breadcrumb-container .breadcrumb-item {
	    font-size: 10px; /* 원하는 글자 크기로 변경 */
	}

	/* Custom styles for buttons */
	.btn-custom {
	    background-color: #c0c0c0; /* 기본 배경색 (회색 계통) */
	    border-radius: 5px; /* 라운드 모서리 */
	    color: #ffffff; /* 텍스트 색상 (흰색) */
	    font-size: 13px;
	    font-weight: bold; /* 텍스트 굵게 표시 */
	    padding: 5px, 10px;
	    height: 40px; /* 원하는 높이 값 지정 */
	}
	
	/* 스타일이 적용되는 상황에 따라 버튼의 색 변화 */
	.btn-custom:hover {
	    background-color: #a0a0a0;
	    background-image: linear-gradient(to bottom, #a0a0a0, #c0c0c0);
	}
	.btn-custom:focus {
	    background-color: #808080;
	    background-image: linear-gradient(to bottom, #808080, #a0a0a0);
	}
	
    .small-font {
      font-size: 12px;
    }	
    
    .small-bold-font {
      font-size: 13px;
      font-weight: bold;
    }	
    
	/* styles.css 파일에 스타일 정의 또는 <style> 태그 내부에 삽입 */
	#dataTable th:nth-child(1),
	#dataTable td:nth-child(1),
	#dataTable th:nth-child(2),
	#dataTable td:nth-child(2) {
	    /* 첫번째와 두번째 컬럼에는 30px 너비 설정 */
	    min-width: 40px;
	}
	
	#dataTable th:not(:nth-child(1)):not(:nth-child(2)),
	#dataTable td:not(:nth-child(1)):not(:nth-child(2)) {
	    /* 나머지 컬럼에는 100px 너비 설정 */
	    min-width: 120px;
	}

	#dataTable th {
	    text-align: center;
	}  
	
</style>

<div class="container-fluid px-4">
    <div class="breadcrumb-container">
        <ol class="breadcrumb mt-2 justify-content-end">
            <li class="breadcrumb-item"><i class="fas fa-home"></i></li>
            <li class="breadcrumb-item">Admin</li>
            <li class="breadcrumb-item">Master Data</li>
            <li class="breadcrumb-item active"><fmt:message key="page.title.batchMaster" /></li>
        </ol>
    </div>
    
    <h3 class="page-title mb-3"><i class="fa-brands fa-vimeo"></i> <fmt:message key="page.title.batchMaster" /></h3>    
	<p><fmt:message key="page.subtitle.batchMaster" /></p>

	<div class="card mb-4" style="width:100%">
	  <div class="card-header">
	    <i class="fas fa-search"></i> <fmt:message key="search" />
	  </div>
	  <div class="card-body">
	    <form id="searchForm" >
	      <div class="row mb-1">
	        <label for="batchName" class="col-sm-1 col-form-label" style="padding-left: 20px;"><fmt:message key="search.label.batchName" /></label>
	        <div class="col-sm-5">
	          <input type="text" class="form-control" id="batchName" name="batchName">
	        </div>
	        <label for="startDate" class="col-sm-1 col-form-label" style="padding-left: 20px;"><fmt:message key="search.label.batchDate" /></label>
	        <div class="col-sm-5">
			    <div class="row">
			      <div class="col">
			        <input type="date" class="form-control" id="startDate" name="startDate">
			      </div>
			      <div class="col">
			        <input type="date" class="form-control" id="endDate" name="endDate">
			      </div>
			    </div>
	        </div>	                
	      </div>
	      <div class="row mt-3">
	        <div class="col text-end">
	          <button class="btn btn-custom" id="searchButton"><i class="fas fa-search"></i> <fmt:message key="search" /></button>
	        </div>
	      </div>
	    </form>
	  </div>
	</div>
	
	<div class="card mb-4" style="width:100%">
	    <div class="card-header d-flex justify-content-between align-items-center">
	        <div>
	            <i class="fas fa-border-all"></i> <fmt:message key="table.list" />
	        </div>
	    </div>
		<div class="card-body" style="width:100%">
			<div class="row">
			    <div class="col text-end">
			        <!-- 우측 정렬된 버튼 추가 -->
			        <button class="btn btn-custom mr-2" id="addRowButton">
			            <i class="fas fa-plus"></i> <fmt:message key="table.add" />
			        </button>
			        <button class="btn btn-custom mr-2" id="editRowButton">
			            <i class="fas fa-edit"></i> <fmt:message key="table.edit" />
			        </button>
			        <button class="btn btn-custom mr-2" id="deleteRowsButton">
			            <i class="fas fa-trash-alt"></i> <fmt:message key="table.delete" />
			        </button>
			        <button class="btn btn-custom" id="saveRowsButton">
			            <i class="fas fa-save"></i> <fmt:message key="table.save" />
			        </button>
			    </div>		
			</div>
			<table id="dataTable" class="table table-hover" style="width:100%">
				<thead>
					<tr>
						<th class="dt-head-center"><input type="checkbox" class="form-check-input" id="selectAllCheckBox"> <fmt:message key="field.label.itemCheck" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.no" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.Name" /><span class="text-danger">*</span></th>
						<th class="dt-head-center"><fmt:message key="field.label.Description" /><span class="text-danger">*</span></th>
						<th class="dt-head-center"><fmt:message key="field.label.Cycle" /><span class="text-danger">*</span></th>
						<th class="dt-head-center"><fmt:message key="field.label.StartDate" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.EndDate" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.Status" /><span class="text-danger">*</span></th>
						<th class="dt-head-center"><fmt:message key="field.label.Manager" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.LogYn" /></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div> 

</div>

<script src="/js/main/content/batchMaster.js"></script>