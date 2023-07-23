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
	/* Custom styles for buttons */
	.btn-custom {
	    background-color: #c0c0c0; /* 기본 배경색 (회색 계통) */
	    background-image: linear-gradient(to bottom, #c0c0c0, #a0a0a0); /* 그라데이션 효과 */
	    box-shadow: 0px 3px 3px rgba(0, 0, 0, 0.2); /* 그림자 효과 */
	    border-radius: 5px; /* 라운드 모서리 */
	    color: #ffffff; /* 텍스트 색상 (흰색) */
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
	.btn-custom:active {
	    background-color: #606060;
	    background-image: linear-gradient(to bottom, #606060, #808080);
	}

</style>

<div class="container-fluid px-4">
    <div class="breadcrumb-container">
        <ol class="breadcrumb mt-2 justify-content-end">
            <li class="breadcrumb-item">Interface</li>
            <li class="breadcrumb-item">Layout</li>
            <li class="breadcrumb-item active"><fmt:message key="page.title.insertRow" /></li>
        </ol>
    </div>
    
    <h1 class="page-title mb-4"><fmt:message key="page.title.insertRow" /></h1>    

	<div class="card mb-4" style="width:100%">
	  <div class="card-header">
	    <i class="fas fa-search"></i> <fmt:message key="search" />
	  </div>
	  <div class="card-body">
	    <form id="searchForm" >
	      <div class="row mb-1">
	        <label for="sigunCd" class="col-sm-1 col-form-label" style="padding-left: 20px;"></i><fmt:message key="search.label.lawCode" /></label>
	        <div class="col-sm-5">
	          <select class="form-select" id="sigunCd" name="sigunCd"></select>
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
		    <div>
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
		<div class="card-body" style="width:100%">
			<table id="dataTable" class="table table-hover" style="width:100%">
				<thead>
					<tr>
						<th><fmt:message key="field.label.checkBox" /></th>
						<th><fmt:message key="field.label.Name" /></th>
						<th><fmt:message key="field.label.Age" /></th>
						<th><fmt:message key="field.label.email" /></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>    
  
</div>

<script src="/js/main/content/insertRow.js"></script>