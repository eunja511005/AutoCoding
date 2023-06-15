var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
    debugger;
    
    $('#roleId').change(function() {
    	
    	var selectedValue = $('#roleId').val();
    	
	    $.ajax({
	        url: "/menu/mapping/"+ selectedValue,
	        type: "GET",
	        success: function(response) {            
	            // 받은 HTML을 동적으로 추가합니다.
	            $("#menuMappingContainer").html(response);
	
	            // 이벤트 핸들러를 추가합니다.
	            bindCheckboxEvent();
	        },
	        error: function(xhr, status, error) {
	            console.log("메뉴 매핑에 실패했습니다: " + error);
	        }
	    });
    });  
    
    $.ajax({
        url: "/menu/mapping/"+ "ROLE_ANY",
        type: "GET",
        success: function(response) {            
            // 받은 HTML을 동적으로 추가합니다.
            $("#menuMappingContainer").html(response);

            // 이벤트 핸들러를 추가합니다.
            bindCheckboxEvent();
        },
        error: function(xhr, status, error) {
            console.log("메뉴 매핑에 실패했습니다: " + error);
        }
    });
    
	$('#save-btn').on('click', function() {
		savePermissions();
	});

	initSelectBox('roleId', '/commonCode/ROLE', false);

	$('#roleHeader, #menuMappingHeader').click(handleCollapseClick);
	
});

// 하위 메뉴 열고 닫기 기능
function bindCheckboxEvent() {
    const checkboxes = document.querySelectorAll('#menuMappingContainer input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const submenu = this.parentNode.nextElementSibling;

            updateParentCheckbox(this); // 상위 메뉴 체크 상태 갱신(하위 메뉴중 1개라도 선택 되면 상위 메뉴는 자동으로 선택 되어야 한다.)
            
            // 상위 메뉴 선택 해제 시 모든 하위 메뉴 체크 해제
            if (!this.checked) {
                const childCheckboxes = submenu.querySelectorAll('input[type="checkbox"]');
                childCheckboxes.forEach(childCheckbox => {
                    childCheckbox.checked = false;
                });
            }
        });
    });
    
    // 라벨 클릭시 하위 메뉴 열리고 닫히도록, event.preventDefault() 통해 라벨 클릭시 메뉴 체크는 막음
    const labels = document.querySelectorAll('#menuMappingContainer label');
    labels.forEach(label => {
      label.addEventListener('click', function() {
    	
    	event.preventDefault(); // 클릭 이벤트의 기본 동작인 체크박스 변경을 막음
    	  
        const submenu = this.parentNode.nextElementSibling;
        if (submenu) {
          submenu.classList.toggle('show');
        }
      });
    });
    
}

function updateParentCheckbox(checkbox) {
  const parentCheckbox = checkbox.closest('.list-group').parentNode.querySelector('input[type="checkbox"]');
  if (parentCheckbox) {
    const childCheckboxes = checkbox.closest('.list-group').querySelectorAll('input[type="checkbox"]');
    let isChecked = false;
    childCheckboxes.forEach(childCheckbox => {
      if (childCheckbox.checked) {
        isChecked = true;
      }
    });
    parentCheckbox.checked = isChecked;
  }
}

function handleCollapseClick() {
	var $cardBody = $(this).parents('.card').find('.card-body');
	$cardBody.slideToggle();
}

function handleRoleChange() {
    const role = document.getElementById('role').value;
    const menuItems = document.querySelectorAll('#menu li[data-role]');

    menuItems.forEach(item => {
      const allowedRoles = item.getAttribute('data-role').split(' ');

      if (allowedRoles.includes(role)) {
        item.style.display = 'block';
      } else {
        item.style.display = 'none';
      }
    });
  }

  function savePermissions() {
    const role = $('#roleId').val();
    const menuItems = document.querySelectorAll('#menu input[type="checkbox"]:checked');
    const allowedMenuItems = [];

    menuItems.forEach(item => {
      const id = item.getAttribute('data-menu-id');
      allowedMenuItems.push(id);
    });
    
    // AJAX 호출
    $.ajax({
      url: '/menu/savePermissions',
      type: 'POST',
	  beforeSend: function(xhr) {
		  xhr.setRequestHeader(csrfheader, csrftoken);
      },      
      data: {
        role: role,
        allowedMenuItems: allowedMenuItems
      },
      success: function(response) {
    	  if (response.success) {
				//successCallback(response);
		    } else {
		    	swal({
		      		  title: "Application Error",
		      		  text: response.errorMessage,
		      		  icon: "warning",
		      		  button: "OK",
		      	})
		    }
      },
      error: function(error) {
	  	    swal({
	    		  title: error.responseJSON.status + ", " +error.responseJSON.code,
	    		  text: error.responseJSON.message,
	    		  icon: "warning",
	    		  button: "OK",
	    	})
		}
    }); 

  }