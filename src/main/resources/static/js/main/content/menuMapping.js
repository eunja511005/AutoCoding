var csrfheader = $("meta[name='_csrf_header']").attr("content");
var csrftoken = $("meta[name='_csrf']").attr("content");
var table;

$(document).ready(function() {
	debugger;
	
    $.ajax({
        url: "/menu/mapping/"+ "ROLE_ANY",
        type: "GET",
        success: function(response) {            
            // 받은 HTML을 동적으로 추가합니다.
            $("#menuMappingContainer").html(response);
        },
        error: function(xhr, status, error) {
            console.log("Failed to menuMapping: " + error);
        }
    });
	
    // Collapse 하위 메뉴 열고 닫기
    const menuItems = document.querySelectorAll('#menu li.list-group-item');

    menuItems.forEach(item => {
      const submenu = item.querySelector('.list-group');

      if (submenu) {
        const checkbox = item.querySelector('input[type="checkbox"]');
        checkbox.addEventListener('change', function () {
          if (this.checked) {
            submenu.classList.add('show');
          } else {
            submenu.classList.remove('show');
          }

          // 부모 체크박스 선택 해제 시 자식 체크박스 선택 해제
          if (!this.checked) {
            const childCheckboxes = submenu.querySelectorAll('input[type="checkbox"]');
            childCheckboxes.forEach(childCheckbox => {
              childCheckbox.checked = false;
            });
          }
        });

        // 부모 체크박스 클릭을 위한 이벤트 핸들러 추가
        const parentCheckbox = item.closest('.list-group').parentNode.querySelector('input[type="checkbox"]');
        checkbox.addEventListener('click', function () {
          if (!parentCheckbox.checked) {
            this.checked = false;
          }
        });
      }
    });
});

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
    const role = document.getElementById('role').value;
    const menuItems = document.querySelectorAll('#menu input[type="checkbox"]:checked');
    const allowedMenuItems = [];

    menuItems.forEach(item => {
      const allowedRoles = item.closest('li').getAttribute('data-role').split(' ');

      if (allowedRoles.includes(role)) {
        allowedMenuItems.push(item.id);
      }
    });

    console.log('권한이 저장되었습니다:');
    console.log(allowedMenuItems);
  }