<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/common.jsp" />
<title>Project Lists</title>
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
				Project Lists</h2>

			<!-- 페이지 제목 아래 구분선 -->
			<div class="divider-custom">
				<div class="divider-custom-line"></div>
				<div class="divider-custom-icon">
					<i class="fas fa-star"></i>
				</div>
				<div class="divider-custom-line"></div>
			</div>

			<!-- 페이지별 본문 -->
			<div class="container my-5">
			  <div class="row justify-content-center">
			    <div class="col-md-8">
			      <div id="project-list" class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-5"></div>
			      <nav aria-label="Page navigation">
			        <ul class="pagination justify-content-center" id="pagination"></ul>
			      </nav>
			    </div>
			  </div>
			</div>
			
		</div>
	</section>

	<jsp:include page="/WEB-INF/jsp/common/commonScript.jsp" />

	<!-- this page's javascript -->
	<script type="text/javascript" src="/js/project/projectListForm.js"></script>

</body>
</html>