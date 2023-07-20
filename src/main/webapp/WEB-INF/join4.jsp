<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
<jsp:include page="/WEB-INF/jsp/common/common.jsp" />
<title>Sign Up</title>
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.b-example-divider {
	width: 100%;
	height: 3rem;
	background-color: rgba(0, 0, 0, .1);
	border: solid rgba(0, 0, 0, .15);
	border-width: 1px 0;
	box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em
		rgba(0, 0, 0, .15);
}

.b-example-vr {
	flex-shrink: 0;
	width: 1.5rem;
	height: 100vh;
}

.bi {
	vertical-align: -.125em;
	fill: currentColor;
}

.nav-scroller {
	position: relative;
	z-index: 2;
	height: 2.75rem;
	overflow-y: hidden;
}

.nav-scroller .nav {
	display: flex;
	flex-wrap: nowrap;
	padding-bottom: 1rem;
	margin-top: -1px;
	overflow-x: auto;
	text-align: center;
	white-space: nowrap;
	-webkit-overflow-scrolling: touch;
}

.btn-bd-primary {
	--bd-violet-bg: #712cf9;
	--bd-violet-rgb: 112.520718, 44.062154, 249.437846;
	--bs-btn-font-weight: 600;
	--bs-btn-color: var(--bs-white);
	--bs-btn-bg: var(--bd-violet-bg);
	--bs-btn-border-color: var(--bd-violet-bg);
	--bs-btn-hover-color: var(--bs-white);
	--bs-btn-hover-bg: #6528e0;
	--bs-btn-hover-border-color: #6528e0;
	--bs-btn-focus-shadow-rgb: var(--bd-violet-rgb);
	--bs-btn-active-color: var(--bs-btn-hover-color);
	--bs-btn-active-bg: #5a23c8;
	--bs-btn-active-border-color: #5a23c8;
}

.bd-mode-toggle {
	z-index: 1500;
}
</style>

<!-- Custom styles for this template -->
</head>
<body class="bg-body-tertiary">

	<input type="hidden" id="publicKeyString" value="${publicKey}">

	<div class="container my-4">
		<main>

			<div class="row g-5 d-flex justify-content-center">
				<div class="col-md-10">
					<h4 class="my-4">Sign Up</h4>
					<form class="needs-validation" novalidate id="uploadForm"
						enctype="multipart/form-data">
						<div class="row g-3">

							<div class="col-12">
								<label for="username" class="form-label">Username</label>
								<div class="input-group has-validation">
									<span class="input-group-text">@</span> <input type="text"
										class="form-control" name="username" id="username"
										placeholder="Username" required>
									<div class="invalid-feedback">Your username is required.
									</div>
								</div>
							</div>

							<div class="col-12">
								<label for="password" class="form-label">Password</label> <input
									type="password" class="form-control" name="password"
									id="password" required>
								<div class="invalid-feedback">Please enter a valid
									password for sign up.</div>
							</div>

							<div class="col-12">
								<label for="email" class="form-label">Email</label> <input
									type="email" class="form-control" name="email" id="email"
									placeholder="you@example.com" required>
								<div class="invalid-feedback">Please enter a valid email
									address for sign up.</div>
							</div>

							<div class="col-12">
					            <label for="language">Language</label>
					            <select class="form-select" id="language" name="language" required>
					              <option value="ko">Korea</option>
					              <option value="en">English</option>
					            </select>
							</div>

						</div>

						<hr class="my-4">

						<h4 class="mb-3">Role</h4>

						<div class="row my-3">
							<div class="col-sm-2">
								<input class="form-check-input" type="radio" name="role"
									id="role1" value="ROLE_ADMIN,ROLE_FAMILY,ROLE_USER"> <label
									class="form-check-label" for="role1"> Admin </label>
							</div>
							<div class="col-sm-2">
								<input class="form-check-input" type="radio" name="role"
									id="role2" value="ROLE_FAMILY,ROLE_USER" checked> <label
									class="form-check-label" for="role2"> FAMILY </label>
							</div>
							<div class="col-sm-2">
								<input class="form-check-input" type="radio" name="role"
									id="role3" value="ROLE_USER"> <label
									class="form-check-label" for="role3"> USER </label>
							</div>
						</div>

						<hr class="my-4">

						<h4 class="mb-3">Image</h4>

						<div class="container col-md-6">
							<div class="mb-5">
								<label for="Image" class="form-label">Your image Upload
									with Preview</label> <input class="form-control" type="file"
									name="file" id="formFile" onchange="preview()"
									accept="image/jpeg, image/png">
							</div>
							<img id="frame" src="" class="img-fluid" />
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

	<script type="text/javascript" src="/js/join4.js"></script>

	<script>
		function preview() {
			frame.src = URL.createObjectURL(event.target.files[0]);
		}
	</script>

</body>
</html>
