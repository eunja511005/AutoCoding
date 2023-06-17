var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
    debugger;
    
    // 롤 셀렉트 박스 변경시 다시 조회
    // 초기 로딩시 selectedValue 값이 null 이어서 초기 로딩 시에는 아래 아래 함수에서 따로 한번 호출
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
    
    // 초기 화면 로딩시 ROLE_ANY로 따로 한번 호출
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
    
    // 저장 버튼 클릭시
	$('#save-btn').on('click', function() {
		savePermissions();
	});

	initSelectBox('roleId', '/commonCode/ROLE', false);

	$('#roleHeader, #menuMappingHeader').click(handleCollapseClick);
	
});

// 하위 메뉴 열고 닫기 기능
function bindCheckboxEvent() {
    const checkboxes = document.querySelectorAll('#menu input[type="checkbox"]');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const submenu = this.parentNode.nextElementSibling;

            // 하위 메뉴중 1개라도 선택 되면 상위 메뉴는 자동으로 선택 되어야 한다.
            // 하위 메뉴가 하나도 선택 된게 없으면 상위 메뉴도 선택 해제 되어야 한다.
            updateParentCheckbox(this); 
            
            // 상위 메뉴 선택 해제 시 모든 하위 메뉴 체크 해제
            // submenu가 있을때문 동작 하도록 해야 submenu.querySelectorAll() 할때 null 오류 없음(최하위 메뉴)
            if (!this.checked && submenu) {
                const childCheckboxes = submenu.querySelectorAll('input[type="checkbox"]');
                childCheckboxes.forEach(childCheckbox => {
                    childCheckbox.checked = false;
                });
            }
        });
    });
    
    // 라벨 클릭시 하위 메뉴 열리고 닫히도록, event.preventDefault() 통해 라벨 클릭시 메뉴 체크는 막음
    const labels = document.querySelectorAll('#menu label');
    labels.forEach(label => {
      label.addEventListener('click', function(event) {
    	event.preventDefault(); // 클릭 이벤트의 기본 동작인 체크박스 변경을 막음
    	
        const icon = label.children[0];
        if(icon){
            icon.classList.toggle("fa-caret-down");
            icon.classList.toggle("fa-caret-right");
        }
    	  
        const submenu = this.parentNode.nextElementSibling;
        if (submenu) {
          submenu.classList.toggle('show');
        }
      });
    });
    
}

function updateParentCheckbox(checkbox) {
  const parent = checkbox.closest('.list-group').parentNode;

  if (parent.id === 'menuMappingContainer') {
	return; // 특정 ID를 가진 부모에 도달했을 경우 종료
  }
  
  const parentCheckbox = parent.querySelector('input[type="checkbox"]');
  
  const childCheckboxes = checkbox.closest('.list-group').querySelectorAll('input[type="checkbox"]');
  let isChecked = false;
  
  childCheckboxes.forEach(childCheckbox => {
    if (childCheckbox.checked) {
      isChecked = true;
    }
  });
  
  parentCheckbox.checked = isChecked;    
  updateParentCheckbox(parentCheckbox); //재귀적으로 수행하여 모든 상위 계층의 부모 체크박스를 업데이트

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
		    	swal({
			    		title: "Success",
			    		text: "Menu mapping saved.",
			    		icon: "success",
		      		  button: "OK",
		      	})
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