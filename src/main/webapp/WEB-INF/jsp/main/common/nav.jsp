        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3 a-menu" href="/initInit">Freedom Main</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">
                <div class="input-group">
                    <input class="form-control" type="text" placeholder="Search for..." aria-label="Search for..." aria-describedby="btnNavbarSearch" />
                    <button class="btn btn-primary" id="btnNavbarSearch" type="button"><i class="fas fa-search"></i></button>
                </div>
            </form>
            <!-- Navbar-->
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false"></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a id="edit-btn" style="display: none;" class="dropdown-item a-menu">Edit</a></li>
                        <li><a id="message-btn" style="display: none;" class="dropdown-item a-menu">Message</a></li>
                        <li><hr class="dropdown-divider" /></li>
                    	<li><a id="login-btn" class="dropdown-item a-menu" href="signinInit"><fmt:message key="nav.loing" /></a></li>
                        <li><a id="logout-btn" style="display: none;" class="dropdown-item a-menu"><fmt:message key="nav.logout" /></a></li>
                    </ul>
                </li>
            </ul>
        </nav>