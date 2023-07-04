<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <!-- Favicon-->
    	<link rel="icon" type="image/x-icon" href="/assets/favicon.ico" />
        <title>Auto Coding Main</title>
        <link href="/css/main/common/styles.css" rel="stylesheet" />
        <link href="/css/main/common/datatables.min.css" rel="stylesheet" />
        
        <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.20/summernote-bs5.min.css">-->
        <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        
        <!-- 자동 생성된 소스 코드 예쁘게 보이게 -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/prismjs@1.27.0/themes/prism-okaidia.min.css">
    </head>
	<body class="sb-nav-fixed">
    <%-- Top Menu --%>
    <%@ include file="/WEB-INF/jsp/main/common/nav.jsp" %>
    
    <div id="layoutSidenav">
    	<%-- Left Menu --%>
    	<%@ include file="/WEB-INF/jsp/main/common/leftNav2.jsp" %>
    	<div id="layoutSidenav_content">
        	<main>
    			<div id="dynamic-content"></div>
        	</main>
        	<%@ include file="/WEB-INF/jsp/main/common/footer.jsp" %>
        </div>	
    </div>	
    
    <%@ include file="/WEB-INF/jsp/main/common/script.jsp" %>
    <!-- <script src="/js/main/common/leftNav.js"></script>  -->
    <script src="/js/main/common/main.js"></script>    
    <script src="/js/main/common/message.js"></script>    
    
	</body>
	
</html>
