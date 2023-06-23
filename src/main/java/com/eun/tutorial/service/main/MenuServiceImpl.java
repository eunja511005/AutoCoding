package com.eun.tutorial.service.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.aspect.annotation.CreatePermission;
import com.eun.tutorial.dto.main.MenuDTO;
import com.eun.tutorial.mapper.main.MenuMapper;
import com.eun.tutorial.util.AuthUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenuMapper menuMapper;
	private final MessageSource messageSource;
	private final MenuControlService menuControlService;
	private final HttpSecurity http;
	private final PlatformTransactionManager transactionManager;

	@Override
	public List<MenuDTO> getMenuList() {
		return menuMapper.selectMenuList();
	}
	
	@Override
	public String getMenuAuthByRole(String role) {
		List<MenuDTO> menuDTOList = menuMapper.getMenuAuthByRole(role);
		Map<String, List<MenuDTO>> submenuDTOListMap = new HashMap<String, List<MenuDTO>>();
		for (MenuDTO menuDTO : menuDTOList) {
			if(submenuDTOListMap.get(menuDTO.getParentMenuId())==null) {
				List<MenuDTO> menuList = new ArrayList<>();
				menuList.add(menuDTO);
				submenuDTOListMap.put(menuDTO.getParentMenuId(), menuList);
			}else {
				(submenuDTOListMap.get(menuDTO.getParentMenuId())).add(menuDTO);
			}
		}
		
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<ul class=\"list-group\" id=\"menu\">\n");
		generateMenuHtmlRecursive(htmlBuilder, submenuDTOListMap, "root");
		htmlBuilder.append("</ul>\n");
		
		return htmlBuilder.toString();
	}

	@Override
	@CreatePermission
	public int saveMenu(MenuDTO menuDTO) {
		menuDTO.setId("menu_" + UUID.randomUUID());
		return menuMapper.insertMenu(menuDTO);
	}

	@Override
	@CheckAuthorization
	public int updateMenu(MenuDTO menuDTO) {
		return menuMapper.updateMenu(menuDTO);
	}

	@Override
	@CheckAuthorization
	public int deleteMenu(String id) {
		return menuMapper.deleteMenu(id);
	}

	@Override
	public MenuDTO getMenuListById(String id) {
		return menuMapper.getMenuListById(id);
	}

	@Override
	public String generateMenuHtml() {
		List<MenuDTO> menus = menuMapper.selectMenuList();
		return generateMenuHtml(menus);
	}
	
    private void generateMenuHtmlRecursive(StringBuilder htmlBuilder, Map<String, List<MenuDTO>> submenuDTOListMap, String parentMenuId) {
        
    	List<MenuDTO> menuDTOList = submenuDTOListMap.get(parentMenuId);
    			
        for (MenuDTO menuDTO : menuDTOList) {
        	String id = menuDTO.getId();
            String menuId = menuDTO.getMenuId();
            String menuAuth = menuDTO.getMenuAuth();

            htmlBuilder.append("<li class=\"list-group-item\">\n");
            htmlBuilder.append("<div class=\"custom-control custom-checkbox\">\n");
            htmlBuilder.append("<input type=\"checkbox\" class=\"custom-control-input\" id=\"").append(menuId).append("\" data-menu-id=\""+id+"\"");
            if ("Y".equals(menuAuth)) {
                htmlBuilder.append(" checked");
            }
            htmlBuilder.append(">\n");
            htmlBuilder.append("<label class=\"custom-control-label\" for=\"").append(menuId).append("\">\n");
            
            // 하위 메뉴 있는 경우 아이콘 표시
            if (submenuDTOListMap.containsKey(menuId)) {
            	htmlBuilder.append("<i class=\"fas fa-caret-right\"></i> ");
            }
            
            htmlBuilder.append(menuId).append("</label>\n");
            htmlBuilder.append("</div>\n");

            // 하위 메뉴가 있는 경우 재귀 호출
            if (submenuDTOListMap.get(menuId)!=null) {
                htmlBuilder.append("<ul class=\"list-group collapse show\" id=\"").append(menuId).append("Menu\">\n");
                generateMenuHtmlRecursive(htmlBuilder, submenuDTOListMap, menuId);
                htmlBuilder.append("</ul>\n");
            }

            htmlBuilder.append("</li>\n");
        }
    }

	private String generateMenuHtml(List<MenuDTO> menus) {
		StringBuilder sb = new StringBuilder();

		Map<String, List<MenuDTO>> subMenuMap = new HashMap<>();

		for (MenuDTO menu : menus) {
			String parentMenuId = menu.getParentMenuId();
			if (parentMenuId != null) {
				subMenuMap.computeIfAbsent(parentMenuId, k -> new ArrayList<>()).add(menu);
			}
		}

		Locale locale = AuthUtils.getLocale();

		Collection<? extends GrantedAuthority> authorities = AuthUtils.getAuthorities();
		String preCategory = "";
		for (MenuDTO menu : menus) {
			if (menu.getMenuLevel() == 1 && (checkAuth(authorities, menu))) {

				if (!preCategory.equals(menu.getCategory())) { // 첫번째 category에대해서만 category 그려 주도록
					sb.append("\t<div class=\"sb-sidenav-menu-heading\">" + menu.getCategory() + "</div>\n");
				}

				generateMenuItemHtml(sb, menu, subMenuMap, locale, authorities);
				preCategory = menu.getCategory();

			}
		}

		return sb.toString();
	}

	private boolean checkAuth(Collection<? extends GrantedAuthority> authorities, MenuDTO menu) {
		
		if(menu.getMenuAuth().equals("ROLE_ANY")) {
			return true;
		}
		
		return authorities.stream().anyMatch(authority -> authority.getAuthority().equals(menu.getMenuAuth()));
	}

	private void generateMenuItemHtml(StringBuilder sb, MenuDTO menu, Map<String, List<MenuDTO>> subMenuMap,
			Locale locale, Collection<? extends GrantedAuthority> authorities) {
		List<MenuDTO> subMenuList = subMenuMap.get(menu.getMenuId());

		String menuName = messageSource.getMessage(menu.getMenuId(), null, locale);

		if (subMenuList == null) {

			sb.append("\t<a class=\"nav-link a-menu");
			sb.append("\" href=\"").append(menu.getMenuPath()).append("\">\n");

			sb.append("\t\t<div class=\"sb-nav-link-icon\"><i class=\"").append(menu.getMenuIcon())
					.append("\"></i></div>").append(menuName).append("\n");

			sb.append("\t</a>\n");

			return;
		}

		sb.append("\t<a class=\"nav-link collapsed");
		sb.append("\" data-bs-toggle=\"collapse\" data-bs-target=\"#collapse").append(menu.getMenuId())
				.append("\" aria-expanded=\"false\" aria-controls=\"collapse").append(menu.getMenuId()).append("\">\n");

		sb.append("\t\t<div class=\"sb-nav-link-icon\"><i class=\"").append(menu.getMenuIcon()).append("\"></i></div>")
				.append(menuName).append("\n");

		sb.append("\t\t<div class=\"sb-sidenav-collapse-arrow\"><i class=\"fas fa-angle-down\"></i></div>\n");

		sb.append("\t</a>\n");

		String parentMenuId = "";
		if (!menu.getParentMenuId().equals("N/A")) {
			parentMenuId = menu.getParentMenuId();
		}

		sb.append("\t<div class=\"collapse\" id=\"collapse").append(menu.getMenuId())
				.append("\" aria-labelledby=\"heading").append(menu.getMenuId())
				.append("\" data-bs-parent=\"#sidenavAccordion" + parentMenuId + "\">\n")
				.append("\t\t<nav class=\"sb-sidenav-menu-nested nav accordion\" id=\"sidenavAccordion"
						+ menu.getMenuId() + "\">\n");

		for (MenuDTO subMenu : subMenuList) {
			
			if(checkAuth(authorities, subMenu)) {
				generateMenuItemHtml(sb, subMenu, subMenuMap, locale, authorities);// 재귀 함수 호출
			}
		}

		sb.append("\t\t</nav>\n");
		sb.append("\t</div>\n");

	}

	@Override
	@Transactional
	public void savePermissions(String role, List<String> allowedMenuItems) throws Exception{
	    TransactionStatus status = transactionManager.getTransaction(TransactionDefinition.withDefaults());
	    
		List<MenuDTO> menuDTOList = menuMapper.getMenuAuthByRole(role);
		List<String> authList = new ArrayList<>();
		for (MenuDTO menuDTO : menuDTOList) {
			if("Y".equals(menuDTO.getMenuAuth())) {
				authList.add(menuDTO.getId());
			}
		}
		
		// 체크 O -> X (기존에 해당 롤에 있던 권한 중 빠진 권한은 차 상위 롤로 업데이트)
		List<String> noAuthList = new ArrayList<>();
		for (String id : authList) {
			if(!allowedMenuItems.contains(id)) {
				noAuthList.add(id);
			}
		}
		String upperRole = getUpperRole(role);
		if(!noAuthList.isEmpty()) {
			menuMapper.updateMenuAuthByMenuIds(upperRole, noAuthList);
		    transactionManager.commit(status);
		}
		
		// 체크 X -> O (요청된 권한 중 기존에 없던 권한만 업데이트 해줌, 그래야 하위 롤이 가지고 있던 권한이 안 없어 짐)
		List<String> addAuthList = new ArrayList<>();
		for (String id : allowedMenuItems) {
			if(!authList.contains(id)) {
				addAuthList.add(id);
			}
		}
		if(!addAuthList.isEmpty()) {
			menuMapper.updateMenuAuthByMenuIds(role, addAuthList);
			transactionManager.commit(status);
		}
		
		// 메뉴 권한에 맞게 메뉴 콘트롤러 테이블 업데이트
		menuMapper.updateMenuControl();
		
		// 서버 리스타트 없이 권한 업데이트 
		menuControlService.updateMenuControlList(http);
	}

	private String getUpperRole(String role) {
		if("ROLE_ANY".equals(role)) {
			return "ROLE_USER";
		}
		if("ROLE_USER".equals(role)) {
			return "ROLE_FAMILY";
		}
		if("ROLE_FAMILY".equals(role)) {
			return "ROLE_ADMIN";
		}
		if("ROLE_ADMIN".equals(role)) {
			return "ROLE_SYS";
		}
		
		return null;
	}

}