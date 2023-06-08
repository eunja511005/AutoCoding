package com.eun.tutorial.service.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eun.tutorial.aspect.annotation.CheckAuthorization;
import com.eun.tutorial.aspect.annotation.CreatePermission;
import com.eun.tutorial.dto.main.MenuDTO;
import com.eun.tutorial.mapper.main.MenuMapper;
import com.eun.tutorial.service.user.PrincipalDetails;
import com.eun.tutorial.util.AuthUtils;
import com.eun.tutorial.util.StringUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

	private final MenuMapper menuMapper;
	private final MessageSource messageSource;

	@Override
	public List<MenuDTO> getMenuList() {
		return menuMapper.selectMenuList();
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

}