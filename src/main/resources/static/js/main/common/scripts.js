/*!
    * Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2023 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
    // 
// Scripts
// 
document.addEventListener('DOMContentLoaded', event => {
	debugger;
	
    let isSidebarOpen = true; // 초기에 사이드바를 열린 상태로 시작합니다.
    document.addEventListener('click', outsideSidebarClick);

    const sidebarToggle = document.querySelector('#sidebarToggle');
    const sidebar = document.querySelector('#sidenavAccordion');
    const localStorageKey = 'sb|sidebar-toggle';

    if (sidebarToggle) {
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            toggleSidebar();
        });
    }

    function toggleSidebar() {
    	debugger;
        // 사이드바 상태를 토글
		event.preventDefault();
        document.body.classList.toggle('sb-sidenav-toggled');
        localStorage.setItem(localStorageKey, isSidebarOpen);
        isSidebarOpen = !isSidebarOpen;

        // 이벤트 핸들러를 등록 또는 해제
        if (isSidebarOpen) {
        	document.addEventListener('click', outsideSidebarClick);
        } else {
        	document.removeEventListener('click', outsideSidebarClick);
        }
    }

    function outsideSidebarClick(event) {
        // 사이드바 외부를 클릭할 때 사이드바를 닫습니다.
        if (!isClickInsideSidebar(event) && !isClickInsideToggle(event)) {
            toggleSidebar();
        }
    }

    function isClickInsideSidebar(event) {
        return sidebar && sidebar.contains(event.target);
    }

    function isClickInsideToggle(event) {
        return sidebarToggle && sidebarToggle.contains(event.target);
    }

});