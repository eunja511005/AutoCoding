<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  


<!-- 다국어 번들 설정(JSP 페이지에서 .properties 파일을 사용) -->
<fmt:setBundle basename="messages" var="msg" />

<!-- JavaScript 내부에 다국어 메시지 변수 선언 -->
<script charset="UTF-8">
	var alertTitle = '<fmt:message key="alert_title" bundle="${msg}" />';
    var noRowSelectedMessage = '<fmt:message key="no_row_selected" bundle="${msg}" />';
    var multiRowSelectedMessage = '<fmt:message key="multi_row_selected" bundle="${msg}" />';
    var designer = '<fmt:message key="role.designer" bundle="${msg}" />';
    var publisher = '<fmt:message key="role.publisher" bundle="${msg}" />';
    var frontendDeveloper = '<fmt:message key="role.frontendDeveloper" bundle="${msg}" />';
    var backendDeveloper = '<fmt:message key="role.backendDeveloper" bundle="${msg}" />';
    var databaseAdmin = '<fmt:message key="role.databaseAdmin" bundle="${msg}" />';
    var systemAdmin = '<fmt:message key="role.systemAdmin" bundle="${msg}" />';
    var descriptionDesigner = '<fmt:message key="role.description.designer" bundle="${msg}" />';
    var descriptionPublisher = '<fmt:message key="role.description.publisher" bundle="${msg}" />';
    var descriptionFrontendDeveloper = '<fmt:message key="role.description.frontendDeveloper" bundle="${msg}" />';
    var descriptionBackendDeveloper = '<fmt:message key="role.description.backendDeveloper" bundle="${msg}" />';
    var descriptionDatabaseAdmin = '<fmt:message key="role.description.databaseAdmin" bundle="${msg}" />';
    var descriptionSystemAdmin = '<fmt:message key="role.description.systemAdmin" bundle="${msg}" />';
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
	
    .role-card {
        border: 1px solid #ddd;
        padding: 20px;
        margin: 10px;
        text-align: center;
        display: flex;
        flex-direction: column;      
        justify-content: space-between;
        height: 95%;          
    }

    .role-icon {
        font-size: 3rem;
        margin-bottom: 10px;
    }	
    
    .role-description {
        max-height: 3.6em; /* Set the maximum height for three lines of text */
        overflow: hidden;
        text-overflow: ellipsis;
        margin-bottom: 10px;
    }  
		 
</style>

<div class="container-fluid px-4">
    <div class="breadcrumb-container">
        <ol class="breadcrumb mt-2 justify-content-end">
            <li class="breadcrumb-item"><i class="fas fa-home"></i></li>
            <li class="breadcrumb-item">Interface</li>
            <li class="breadcrumb-item">Layout</li>
            <li class="breadcrumb-item active"><fmt:message key="page.title.webServerDeveloperRegister" /></li>
        </ol>
    </div>
    
    <h3 class="page-title mb-3"><i class="fa-brands fa-vimeo"></i> <fmt:message key="page.title.webServerDeveloperRegister" /></h3>    
	<p><fmt:message key="page.subtitle.webServerDeveloperRegister" /></p>

    <div class="row">
        <div class="col-md-4">
            <div class="role-card">
                <i class="fas fa-paint-brush role-icon"></i>
                <h3><fmt:message key="role.designer" /></h3>
                <p><fmt:message key="role.description.designer" /></p>
                <button class="btn-custom role-button" data-toggle="modal" data-target="#applicationModal" data-role="designer"><fmt:message key="button.apply" /></a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="role-card">
                <i class="fas fa-desktop role-icon"></i>
                <h3><fmt:message key="role.publisher" /></h3>
                <p><fmt:message key="role.description.publisher" /></p>
                <button class="btn-custom role-button" data-toggle="modal" data-target="#applicationModal" data-role="publisher"><fmt:message key="button.apply" /></a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="role-card">
                <i class="fas fa-code role-icon"></i>
                <h3><fmt:message key="role.frontendDeveloper" /></h3>
                <p><fmt:message key="role.description.frontendDeveloper" /></p>
                <button class="btn-custom role-button" data-toggle="modal" data-target="#applicationModal" data-role="frontendDeveloper"><fmt:message key="button.apply" /></a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="role-card">
                <i class="fas fa-server role-icon"></i>
                <h3><fmt:message key="role.backendDeveloper" /></h3>
                <p><fmt:message key="role.description.backendDeveloper" /></p>
                <button class="btn-custom role-button" data-toggle="modal" data-target="#applicationModal" data-role="backendDeveloper"><fmt:message key="button.apply" /></a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="role-card">
                <i class="fas fa-database role-icon"></i>
                <h3><fmt:message key="role.databaseAdmin" /></h3>
                <p><fmt:message key="role.description.databaseAdmin" /></p>
                <button class="btn-custom role-button" data-toggle="modal" data-target="#applicationModal" data-role="databaseAdmin"><fmt:message key="button.apply" /></a>
            </div>
        </div>
        <div class="col-md-4">
            <div class="role-card">
                <i class="fas fa-cogs role-icon"></i>
                <h3><fmt:message key="role.systemAdmin" /></h3>
                <p><fmt:message key="role.description.systemAdmin" /></p>
                <button class="btn-custom role-button" data-toggle="modal" data-target="#applicationModal" data-role="systemAdmin"><fmt:message key="button.apply" /></a>
            </div>
        </div>
    </div>
	
	<div style="height: 1vh"></div>

</div>

<!-- Modal -->
<div class="modal fade" id="applicationModal" tabindex="-1" aria-labelledby="applicationModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
	        <head>
	            <meta charset="UTF-8"> <!-- 메타 태그를 추가하여 문자 인코딩 설정 -->
	        </head>        
            <div class="modal-header">
                <h5 class="modal-title" id="applicationModalLabel"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" style="font-size: 0.5rem;"></button>
            </div>
            <div class="modal-body">
            	<p id="applicationModalDescription"></p>
                <!-- Application form goes here -->
                <form id="uploadForm" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="username" class="form-label"><fmt:message key="label.username" /></label> <small class="form-text text-danger">*</small>
                        <input type="text" id="username" name="username" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label"><fmt:message key="label.password" /></label> <small class="form-text text-danger">*</small>
                        <input type="password" id="password" name="password" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label"><fmt:message key="label.email" /></label> <small class="form-text text-danger">*</small>
                        <input type="email" id="email" name="email" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="userComment" class="form-label"><fmt:message key="label.userComment" /></label> <small class="form-text text-danger">*</small>
                        <textarea id="userComment" name="userComment" class="form-control" required></textarea>
                    </div>                    
                    <div class="mb-3">
						<div class="mb-2">
							<label for="Image" class="form-label">Your image Upload with Preview</label> <small class="form-text text-danger">*</small>
							<input type="file" class="form-control" id="formFile" name="file" onchange="preview()" accept="image/jpeg, image/png" required>
						</div>
						<img id="frame" src="" class="img-fluid" />
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="button.close" /></button>
                <button type="button" class="btn btn-primary" id="submitBtn"><fmt:message key="button.submit" /></button>
            </div>
        </div>
    </div>
</div>


<script src="/js/main/content/webServerDeveloperRegister.js" charset="UTF-8"></script>