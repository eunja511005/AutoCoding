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
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

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

	@BeforeEach
	void setUp() {
		setMenuMock();
		setMessageMock();
		menuService = new MenuServiceImpl(menuMapper, messageSource);
	}

	@Test
	void generateMenuHtml_withUserRole() {

		// Arrange
		Authentication authentication = Mockito.mock(Authentication.class);
		setUserDetailsMock(authentication);
		setRoleMOck(authentication);

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
        	}
        }
            return returnMessage;
        });
	}

	private void setMenuMock() {
		List<MenuDTO> expectedMenuList = new ArrayList<>();
		expectedMenuList.add(createMenu("menu_1", "Core", 1, "openForum", "/posts/list", "fas fa-comments", 1, "root",
				false, "ROLE_ANY"));
		expectedMenuList.add(createMenu("menu_2", "Core", 1, "relation", "/relationShip/list", "fas fa-handshake", 2,
				"root", false, "ROLE_ANY"));
		expectedMenuList
				.add(createMenu("menu_3", "Admin", 1, "admin", "#", "fa fa-user-shield", 3, "root", false, "ROLE_SYS"));
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
