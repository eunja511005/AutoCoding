<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<div class="container-fluid px-4">
    <div class="breadcrumb-container">
        <ol class="breadcrumb mt-2 justify-content-end">
            <li class="breadcrumb-item">Interface</li>
            <li class="breadcrumb-item">Layout</li>
            <li class="breadcrumb-item active"><fmt:message key="page.title.realEstatePrice" /></li>
        </ol>
    </div>
    
    <h1 class="page-title mb-4"><fmt:message key="page.title.realEstatePrice" /></h1>    

	<div class="card mb-4" style="width:100%">
	  <div class="card-header">
	    <i class="fas fa-search"></i><fmt:message key="search" />
	  </div>
	  <div class="card-body">
	    <form id="searchForm" >
	      <div class="row mb-1">
	        <label for="searchMonth" class="col-sm-1 col-form-label" style="padding-left: 20px;"><fmt:message key="search.label.dealMonth" /></label>
	        <div class="col-sm-5">
				<div class="col">
					<input type="month" class="form-control" id="searchMonth" name="searchMonth">
				</div>
	        </div>
	        <label for="lawCode" class="col-sm-1 col-form-label" style="padding-left: 20px;"></i><fmt:message key="search.label.lawCode" /></label>
	        <div class="col-sm-5">
	          <select class="form-select" id="lawCode" name="lawCode"></select>
	        </div>	        
	      </div>
	      <div class="row mt-3">
	        <div class="col text-end">
	          <button class="btn btn-primary" id="searchButton"><i class="fas fa-search"></i></i><fmt:message key="search" /></button>
	        </div>
	      </div>
	    </form>
	  </div>
	</div>
	
	<div class="card mb-4" style="width:100%">
		<div class="card-header">
	        <i class="fas fa-border-all"></i> <fmt:message key="table.list" />
		</div>
		<div class="card-body" style="width:100%">
			<table id="dataTable" class="table table-hover" style="width:100%">
				<thead>
					<tr>
						<th><fmt:message key="field.label.transactionAmount" /></th>
						<th><fmt:message key="field.label.transactionType" /></th>
						<th><fmt:message key="field.label.constructionYear" /></th>
						<th><fmt:message key="field.label.judicialDong" /></th>
						<th><fmt:message key="field.label.apartment" /></th>
						<th><fmt:message key="field.label.exclusiveArea" /></th>
						<th><fmt:message key="field.label.floor" /></th>
						<th><fmt:message key="field.label.edit" /></th>
						<th><fmt:message key="field.label.delete" /></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>    
  
</div>

<script src="/js/external/pubdata/realEstatePrice.js"></script>