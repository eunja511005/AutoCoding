<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
<jsp:include page="/WEB-INF/jsp/common/common.jsp" />
<title>Sign Up</title>
<style>
	/* 로딩바를 감싸는 컨테이너 스타일 */
	.loading-container {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(255, 255, 255, 0.7);
		display: flex;
		align-items: center;
		justify-content: center;
		z-index: 9999;
		display: none; /* 초기에는 보이지 않도록 설정 */
	}
</style>

<!-- Custom styles for this template -->
</head>
<body class="bg-body-tertiary">

	<input type="hidden" id="publicKeyString" value="${publicKey}">

	<!-- 로딩바를 감싸는 컨테이너 -->
	<div id="loadingContainer" class="loading-container">
	  <!-- GIF 파일을 표시하는 이미지 태그 -->
	  <img src="/assets/img/1488.gif" alt="Loading...">
	</div>

	<div class="container my-4">
		<main>

			<div class="row g-5 d-flex justify-content-center">
				<div class="col-md-10">
					<h4 class="my-4">Password Recovery</h4>
					<form id="uploadForm" class="needs-validation" novalidate enctype="multipart/form-data">
						<div class="row g-3">

							<div class="col-12">
								<label for="username" class="form-label">Username</label> <small class="form-text text-danger">*</small>
								<div class="input-group">
									<span class="input-group-text">@</span> 
									<input type="text" class="form-control" name="username" id="username"
										placeholder="Username" required>
									<div class="invalid-feedback">Your username is required.
									</div>
								</div>
							</div>

							<div class="col-12">
								<label for="email" class="form-label">Email</label> <small class="form-text text-danger">*</small>
								<input
									type="email" class="form-control" name="email" id="email"
									placeholder="you@example.com" required>
								<div class="invalid-feedback">Please enter a valid email
									address for sign up.</div>
							</div>

						</div>

						<hr class="my-4">
						
						<div class="text-center">
							<button class="btn btn-primary" type="submit">Submit</button>
						</div>
					</form>
				</div>
			</div>
		</main>

	</div>

	<jsp:include page="/WEB-INF/jsp/common/commonScript.jsp" />

	<script type="text/javascript" src="/js/recoveryPassword.js"></script>

</body>
</html>
