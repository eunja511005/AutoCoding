<nav
	class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top"
	id="mainNav">
	<div class="container">
		<a class="navbar-brand" href="/initInit">Do Together</a>
		<button
			class="navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded"
			type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarResponsive" aria-controls="navbarResponsive"
			aria-expanded="false" aria-label="Toggle navigation">
			Menu <i class="fas fa-bars"></i>
		</button>
		<div class="collapse navbar-collapse" id="navbarResponsive">
			<ul class="navbar-nav ms-auto">
				<li class="nav-item mx-0 mx-lg-1"><a
					class="nav-link py-3 px-0 px-lg-3 rounded" href="#portfolio">Success
						Story</a></li>
				<li class="nav-item mx-0 mx-lg-1"><a
					class="nav-link py-3 px-0 px-lg-3 rounded" href="#about">About</a></li>
				<li class="nav-item mx-0 mx-lg-1"><a
					class="nav-link py-3 px-0 px-lg-3 rounded" href="#contact">Contact</a></li>
				<li class="nav-item mx-0 mx-lg-1"><a
					class="nav-link py-3 px-0 px-lg-3 rounded" href="/board/listForm">Board</a></li>


				<li class="nav-item mx-0 mx-lg-1 dropdown"><a
					class="nav-link py-3 px-0 px-lg-3 rounded dropdown-toggle" href="#"
					role="button" data-bs-toggle="dropdown" aria-expanded="false">
						PROJECT </a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="/project/listForm">Project
								Lists</a></li>
						<li><a class="dropdown-item" href="/project/inputForm">Project
								creation</a></li>
					</ul></li>


				<li class="nav-item mx-0 mx-lg-1 dropdown"><a
					class="nav-link py-3 px-0 px-lg-3 rounded dropdown-toggle" href="#"
					role="button" data-bs-toggle="dropdown" aria-expanded="false">
						ADMIN </a>
					<ul class="dropdown-menu">
						<li><a class="dropdown-item" href="/code/listForm">Code</a></li>
						<li><a class="dropdown-item" href="/code/mapping/listForm">CodeMapping</a></li>
					</ul></li>


				<li class="nav-item mx-0 mx-lg-1"><a
					class="nav-link py-3 px-0 px-lg-3 rounded"
					href="javascript:logout()"> <img src=""
						alt="logout" class="rounded-circle" width="35"
						height="35" id="userProfileImg">
				</a></li>
			</ul>
		</div>

		<!--
		    	<div class="user-info ms-auto me-3">
		            <img src="" alt="User Profile Image" class="rounded-circle" width="50" height="50" id="userProfileImg">
		        </div>    
		        
				<div class="btn-group">
				  <button type="button" class="btn btn-success dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" id="userSpan">
				    Action
				  </button>
				  <ul class="dropdown-menu">
				    <li><a class="dropdown-item" href="#">Action</a></li>
				    <li><a class="dropdown-item" href="#">Another action</a></li>
				    <li><a class="dropdown-item" href="#">Something else here</a></li>
				    <li><hr class="dropdown-divider"></li>
				    <li><a class="dropdown-item" href="javascript:document.logoutForm.submit()">Sign Out</a></li>
				  </ul>
				</div>  
				-->
	</div>
</nav>


<div class="container">
	<form action="/signout" method="post" name="logoutForm"
		style="display: none;">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<button type="submit"></button>
	</form>
</div>