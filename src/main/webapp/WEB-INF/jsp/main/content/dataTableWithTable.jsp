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
	    padding: 5px, 10px;
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
            <li class="breadcrumb-item">Interface</li>
            <li class="breadcrumb-item">Layout</li>
            <li class="breadcrumb-item active"><fmt:message key="page.title.insertRow" /></li>
        </ol>
    </div>
    
    <h3 class="page-title mb-3"><i class="fa-brands fa-vimeo"></i> <fmt:message key="page.title.insertRow" /></h3>    

	<div class="card mb-4" style="width:100%">
	  <div class="card-header">
	    <i class="fas fa-tag"></i> <fmt:message key="card.header.order" />
	  </div>
	  <div class="card-body">
	    <form id="orderForm" >
			<div class="row mb-1">
			  <div class="col-sm-6">
			    <div class="row" id="customerCdRow">
			      <div class="col-sm-3">
			        <label for="customerCd" class="col-form-label small-bold-font" style="padding-left: 20px;"><fmt:message key="search.label.customerCd" /><span class="text-danger">*</span></label>
			      </div>
			      <div class="col-sm-9">
			        <select class="form-select small-font" id="customerCd" name="customerCd" required></select>
			      </div>
			    </div>
			  </div>
			  <div class="col-sm-6">
			    <div class="row" id="poNoRow">
			      <div class="col-sm-3">
			        <label for="poNo" class="col-form-label small-bold-font" style="padding-left: 20px;"><fmt:message key="search.label.poNo" /><span class="text-danger">*</span></label>
			      </div>
			      <div class="col-sm-9">
			        <input type="text" class="form-control small-font" id="poNo" name="poNo" required>
			      </div>
			    </div>
			  </div>
			</div>
			<div class="row mb-1">
			  <div class="col-sm-6">
			    <div class="row" id="shipToCdRow">
			      <div class="col-sm-3">
			        <label for="shipToCd" class="col-form-label small-bold-font" style="padding-left: 20px;"><fmt:message key="search.label.Ship-To" /><span class="text-danger">*</span></label>
			      </div>
			      <div class="col-sm-9">
			        <select class="form-select small-font" id="shipToCd" name="shipToCd" required></select>
			      </div>
			    </div>
			  </div>
			  <div class="col-sm-6">
			    <div class="row" id="reqDeliveryDateRow">
			      <div class="col-sm-3">
			        <label for="reqDeliveryDate" class="col-form-label small-bold-font" style="padding-left: 20px;"><fmt:message key="search.label.RDD" /><span class="text-danger">*</span></label>
			      </div>
			      <div class="col-sm-9">
			        <input type="date" class="form-control small-font" id="reqDeliveryDate" name="reqDeliveryDate" required>
			      </div>
			    </div>
			  </div>
			</div>
	    </form>
	  </div>
	</div>
	
	<div class="card mb-4" style="width:100%">
	    <div class="card-header d-flex justify-content-between align-items-center">
	        <div>
	            <i class="fas fa-shopping-cart"></i> <fmt:message key="card.header.orderItems" />
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
			<table id="dataTable" class="table table-hover small-font" style="width:100%">
				<thead>
					<tr>
						<th class="dt-head-center"><input type="checkbox" class="form-check-input" id="selectAllCheckBox"> <fmt:message key="field.label.itemCheck" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.no" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.Ava" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.modelCd" /><span class="text-danger">*</span></th>
						<th class="dt-head-center"><fmt:message key="field.label.modelDesc" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.partNo" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.eanCd" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.cartQty" /><span class="text-danger">*</span></th>
						<th class="dt-head-center"><fmt:message key="field.label.rrp" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.basicPrice_view" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.adjustPrice_view" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.specialPrice_view" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.confirmPrice_view" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.freightCharge" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.adjustAmount_view" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.tax_view" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.adjustTaxAmount_view" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.totalAdjustAmount_view" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.currency" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.onHandQty" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.inTransitQty" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.etaDt" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.W0" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.W1" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.W2" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.W3" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.truckPoint" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.trkPtDummy" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.itemRdd" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.platnCd" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.site" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.popNo" /></th>
						<th class="dt-head-center"><fmt:message key="field.label.popItemNo" /></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col text-end">
			<button class="btn btn-custom" id="saveOrderButton">
				<i class="fas fa-cart-plus"></i> <fmt:message key="saveOrder" />
			</button>
		</div>
	</div>
	
	<div style="height: 1vh"></div>

</div>

<script src="/js/main/content/dataTableWithTable.js"></script>