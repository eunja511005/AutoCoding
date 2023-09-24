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
            <li class="breadcrumb-item active"><fmt:message key="page.title.chat" /></li>
        </ol>
    </div>
    
    <h3 class="page-title mb-3"><i class="fa-brands fa-vimeo"></i> <fmt:message key="page.title.chat" /></h3>    
	<p><fmt:message key="page.subtitle.chat" /></p>

    <div class="row">
	    <div id="chat">
	        <input type="text" id="message" placeholder="메시지를 입력하세요">
	        <button id="send-button">전송</button>
	        <ul id="chat-messages"></ul>
	    </div>
    </div>
	
	<div style="height: 1vh"></div>

</div>

<script src="/js/main/content/chat2.js"></script>