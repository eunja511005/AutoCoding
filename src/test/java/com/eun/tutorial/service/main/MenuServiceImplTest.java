package com.eun.tutorial.service.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.eun.tutorial.dto.UserInfoDTO;
import com.eun.tutorial.dto.main.MenuDTO;
import com.eun.tutorial.mapper.main.MenuMapper;
import com.eun.tutorial.service.user.PrincipalDetails;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {

	private MenuService menuService;

	@Mock
	private MenuMapper menuMapper;

	@Mock
	private MessageSource messageSource;
	
    @Mock
    private SecurityContext securityContextMock;

	@BeforeEach
	void setUp() {
		setMenuMock();
		setMessageMock();
		menuService = new MenuServiceImpl(menuMapper, messageSource, null, null, null);
	}

	@Test
	void generateMenuHtml_withSysRole() {

		// Arrange
		Authentication authentication = Mockito.mock(Authentication.class);
		setUserDetailsMock(authentication);
		setRoleMOck(authentication);
		
		Mockito.when(authentication.isAuthenticated()).thenReturn(true);

		SecurityContext securityContext = createSecurityContext(authentication);
		SecurityContextHolder.setContext(securityContext);
		
		// Act
		String actualMenu = menuService.generateMenuHtml();
		
		// Assert
		String expectedMenu = "	<div class=\"sb-sidenav-menu-heading\">Core</div>\n"
				+ "	<a class=\"nav-link a-menu\" href=\"/posts/list\">\n"
				+ "		<div class=\"sb-nav-link-icon\"><i class=\"fas fa-comments\"></i></div>오픈포럼\n"
				+ "	</a>\n"
				+ "	<a class=\"nav-link a-menu\" href=\"/relationShip/list\">\n"
				+ "		<div class=\"sb-nav-link-icon\"><i class=\"fas fa-handshake\"></i></div>관계\n"
				+ "	</a>\n"
				+ "	<div class=\"sb-sidenav-menu-heading\">Admin</div>\n"
				+ "	<a class=\"nav-link a-menu\" href=\"#\">\n"
				+ "		<div class=\"sb-nav-link-icon\"><i class=\"fa fa-user-shield\"></i></div>관리자\n"
				+ "	</a>\n"
				+ "	<div class=\"sb-sidenav-menu-heading\">Interface</div>\n"
				+ "	<a class=\"nav-link collapsed\" data-bs-toggle=\"collapse\" data-bs-target=\"#collapselayout\" aria-expanded=\"false\" aria-controls=\"collapselayout\">\n"
				+ "		<div class=\"sb-nav-link-icon\"><i class=\"fas fa-columns\"></i></div>레이아웃\n"
				+ "		<div class=\"sb-sidenav-collapse-arrow\"><i class=\"fas fa-angle-down\"></i></div>\n"
				+ "	</a>\n"
				+ "	<div class=\"collapse\" id=\"collapselayout\" aria-labelledby=\"headinglayout\" data-bs-parent=\"#sidenavAccordionroot\">\n"
				+ "		<nav class=\"sb-sidenav-menu-nested nav accordion\" id=\"sidenavAccordionlayout\">\n"
				+ "	<a class=\"nav-link a-menu\" href=\"layout-static.html\">\n"
				+ "		<div class=\"sb-nav-link-icon\"><i class=\"N/A\"></i></div>고정 내비게이션\n"
				+ "	</a>\n"
				+ "	<a class=\"nav-link a-menu\" href=\"layout-sidenav-light.html\">\n"
				+ "		<div class=\"sb-nav-link-icon\"><i class=\"N/A\"></i></div>라이트 사이드네비게이션\n"
				+ "	</a>\n"
				+ "		</nav>\n"
				+ "	</div>\n";
		
		assertEquals(expectedMenu, actualMenu);
		verify(menuMapper, times(1)).selectMenuList();
	}
	
	@Test
	void generateMenuHtml_withNoLogin() {
		// Arrange
		// 1) Create the Granted Authority for the anonymous user
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ANONYMOUS");

        // 2) Create the Authentication object for the anonymous user
        Authentication anonymousAuthentication = new AnonymousAuthenticationToken(
                "key", // A unique key for the anonymous user
                "anonymousUser", // The username for the anonymous user
                Collections.singletonList(authority)
        );
        
        // Mock the SecurityContext
        when(securityContextMock.getAuthentication()).thenReturn(anonymousAuthentication);
        SecurityContextHolder.setContext(securityContextMock);

		// Act
		String actualMenu = menuService.generateMenuHtml();
		
		// Assert
		String expectedMenu = "	<div class=\"sb-sidenav-menu-heading\">Core</div>\n"
				+ "	<a class=\"nav-link a-menu\" href=\"/posts/list\">\n"
				+ "		<div class=\"sb-nav-link-icon\"><i class=\"fas fa-comments\"></i></div>오픈포럼\n"
				+ "	</a>\n";
		
		assertEquals(expectedMenu, actualMenu);
		verify(menuMapper, times(1)).selectMenuList();
	}

	private void setMessageMock() {
		when(messageSource.getMessage(anyString(), isNull(), any(Locale.class)))
        .thenAnswer(invocation -> {
            String code = invocation.getArgument(0);
            Object[] args = invocation.getArgument(1);
            Locale locale = invocation.getArgument(2);

            String returnMessage = "No Message";
            if (locale.getLanguage().equals(Locale.KOREA.getLanguage())) {
            	switch (code) {
                	case "openForum":
                		returnMessage = "오픈포럼";
                		break;
                	case "relation":
                		returnMessage = "관계";	
                		break;
                	case "admin":
                		returnMessage = "관리자";	
                		break;
                	case "layout":
                		returnMessage = "레이아웃";	
                		break;
                	case "staticNavigation":
                		returnMessage = "고정 내비게이션";	
                		break;
                	case "lightSidenav":
                		returnMessage = "라이트 사이드네비게이션";	
                		break;
            	}
            }else if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())) {
            	switch (code) {
            	case "openForum":
            		returnMessage = "openForum";
            		break;
            	case "relation":
            		returnMessage = "relation";	
            		break;
            	case "admin":
            		returnMessage = "admin";	
            		break;
            	case "layout":
            		returnMessage = "layout";	
            		break;
            	case "staticNavigation":
            		returnMessage = "staticNavigation";	
            		break;
            	case "lightSidenav":
            		returnMessage = "lightSidenav";	
            		break;
        	}
        }
            return returnMessage;
        });
	}

	private void setMenuMock() {
		List<MenuDTO> expectedMenuList = new ArrayList<>();
		expectedMenuList.add(createMenu("menu_1", "Core", 1, "openForum", "/posts/list", "fas fa-comments", 1, "root", false, "ROLE_ANY"));
		expectedMenuList.add(createMenu("menu_2", "Core", 1, "relation", "/relationShip/list", "fas fa-handshake", 2, "root", false, "ROLE_USER"));
		expectedMenuList.add(createMenu("menu_3", "Admin", 1, "admin", "#", "fa fa-user-shield", 3, "root", false, "ROLE_SYS"));
		expectedMenuList.add(createMenu("menu_4", "Interface", 1, "layout", "#", "fas fa-columns", 4, "root", false, "ROLE_USER"));
		expectedMenuList.add(createMenu("menu_5", "Interface", 2, "staticNavigation", "layout-static.html", "N/A", 1, "layout", false, "ROLE_USER"));
		expectedMenuList.add(createMenu("menu_6", "Interface", 2, "lightSidenav", "layout-sidenav-light.html", "N/A", 2, "layout", false, "ROLE_USER"));
		when(menuMapper.selectMenuList()).thenReturn(expectedMenuList);
	}

	private void setRoleMOck(Authentication authentication) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		authorities.add(new SimpleGrantedAuthority("ROLE_FAMILY"));
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authorities.add(new SimpleGrantedAuthority("ROLE_SYS"));
		when(authentication.getAuthorities()).thenAnswer(invocation -> authorities);
	}

	private void setUserDetailsMock(Authentication authentication) {
		UserDetails userDetails = new PrincipalDetails(UserInfoDTO.builder()
				.language("ko")
				.build());
		when(authentication.getPrincipal()).thenReturn(userDetails);
	}

	private SecurityContext createSecurityContext(Authentication authentication) {
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		return securityContext;
	}

	// Helper method to create a menu DTO
	private MenuDTO createMenu(String id, String category, int menuLevel, String menuId, String menuPath,
			String menuIcon, int menuOrder, String parentMenuId, boolean delYn, String menuAuth) {
		MenuDTO menuDTO = new MenuDTO();
		menuDTO.setId(id);
		menuDTO.setCategory(category);
		menuDTO.setMenuLevel(menuLevel);
		menuDTO.setMenuId(menuId);
		menuDTO.setMenuPath(menuPath);
		menuDTO.setMenuIcon(menuIcon);
		menuDTO.setMenuOrder(menuOrder);
		menuDTO.setParentMenuId(parentMenuId);
		menuDTO.setDelYn(delYn);
		menuDTO.setMenuAuth(menuAuth);

		return menuDTO;
	}
}
