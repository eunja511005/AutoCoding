package com.eun.tutorial.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.LocaleResolver;

import com.eun.tutorial.dto.ZthhErrorDTO;
import com.eun.tutorial.dto.main.MenuControlDTO;
import com.eun.tutorial.exception.CustomException;
import com.eun.tutorial.service.UserService;
import com.eun.tutorial.service.ZthhErrorService;
import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.service.user.CustomOAuth2UserService;
import com.eun.tutorial.service.user.PrincipalDetails;
import com.eun.tutorial.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity //스프링시큐리티 사용을 위한 어노테이션선언
@RequiredArgsConstructor
@Slf4j
public class MyWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	
	private final ZthhErrorService zthhErrorService;
	private final MenuControlService menuControlService;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final LocaleResolver localeResolver;
	private final UserService userService;
	
    // logout -> login max session 1 오류 해결을 위해 추가
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
	
    // 자동 로그아웃 안 될때 사용
    @Bean
    public LogoutHandler logoutHandler() {
        return new SecurityContextLogoutHandler();
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception { // http 관련 인증 설정
        /**
         * 특정 URL에 대해 csrf 토큰 체크 안하도록 설정, 
         * 기본으로 Get은 체크하지 않음, 
         * Post/Put일때는 꼭 csrf 토큰 사용 할 것
         * javascript에서 CSRF 토큰을 사용하가 위해 httpOnlyFalse()로 지정
         */
        http.csrf()
        	.ignoringAntMatchers("/h2/**")
        	.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    	
        /**
         * 1.세션 관련 설정
         *   1) 필요시 세션 생성
         *   2) session fixation 공격 방지를 위해 로그인 시 신규 세션 생성
         *   3) 유효하지 않은 세션으로 접속 시도 시 리다이렉션 페이지 설정
         *   4) 1명의 유저별로 동시 접속 가능한 세션 수 설정
         *   5) 동시 접속 가능한 세션 수 초과 시 접속 불가 설정
         *   6) 세션 만료시 리다이렉션 페이 설정
         *   7) 세션 레지스터리 설정
         */
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().newSession()
//                .invalidSessionUrl("/invalidSession.html")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/sessionExpire.html")
                .sessionRegistry(sessionRegistry());
        
        /**
         * 2.h2-console에서 iframe을 사용하는데 이때 X-Frame-Options 에러가 발생하지 않도록 설정(sameorigin일 경우만 허용)
         */
        http.headers().frameOptions().sameOrigin();
        
        /**
         * Action별 권한 체크
         */
        
        List<MenuControlDTO> menuControlList = menuControlService.getMenuControlList();
        for (MenuControlDTO menuControlDTO : menuControlList) {
        	if("GET".equals(menuControlDTO.getMethod())) {
        		if(menuControlDTO.getRoleId().equals("ANY")) {
        			http.authorizeRequests().antMatchers(HttpMethod.GET, menuControlDTO.getUrl()).permitAll();
        		}else {
        			http.authorizeRequests().antMatchers(HttpMethod.GET, menuControlDTO.getUrl()).hasRole(menuControlDTO.getRoleId());
        		}
        	}else if("POST".equals(menuControlDTO.getMethod())) {
        		if(menuControlDTO.getRoleId().equals("ANY")) {
        			http.authorizeRequests().antMatchers(HttpMethod.POST, menuControlDTO.getUrl()).permitAll();
        		}else {
        			http.authorizeRequests().antMatchers(HttpMethod.POST, menuControlDTO.getUrl()).hasRole(menuControlDTO.getRoleId());
        		}
        	}else if("DELETE".equals(menuControlDTO.getMethod())) {
        		if(menuControlDTO.getRoleId().equals("ANY")) {
        			http.authorizeRequests().antMatchers(HttpMethod.DELETE, menuControlDTO.getUrl()).permitAll();
        		}else {
        			http.authorizeRequests().antMatchers(HttpMethod.DELETE, menuControlDTO.getUrl()).hasRole(menuControlDTO.getRoleId());
        		}
        	}else {
        		throw new CustomException(500, "No HTTP Method");
        	}
		} 
        
        http
        	.authorizeRequests() // 접근에 대한 인증 설정
            .antMatchers("/signinInit", "/assets/**",
            		"/joinInit", "/join", "/js/**", "/img/**", "/css/**",
            		"/h2-console/**", "/error/**", "/favicon.ico", "/layout/test",
            		"/main/**", "/content1", "/content2", "/content3", "/posts/**", "/login-status", "/commonCode/**", "/menu/loadMenu", "/", "/signout").permitAll() // 누구나 접근 허용
            .anyRequest().authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
        
        /**
         * 5.로그인 설정
         *   1) 로그인 페이지 설정
         *   2) 로그인 페이지에서 로그인을 위해 호출 하는 url 설정
         *   3) 나머지는 인증 후 접속 가능토록 설정
         *   4) 로그인 성공시 핸들러 설정
         *   5) 로그인 실패시 핸들서 설정
         *   6) 모두 로그 아웃에 접근 가능
         */
        http
                .formLogin() // 로그인에 관한 설정
                    .loginPage("/main") // 로그인 했을때 이동하는 페이지
                    .loginProcessingUrl("/signin") // 로그인 버튼 클릭 시 호출 되는 URL로 호출시 스프링 시큐리티에서 제공하는 기능 호출
//                	.usernameParameter("userId")
                    .successHandler((request, response, auth)->{
                    	
                        for (GrantedAuthority authority : auth.getAuthorities()){
                            log.info("Authority Information {} ", authority.getAuthority());
                        }
                        
                        if (auth != null && auth.isAuthenticated()) {
                        	PrincipalDetails userDetailsImpl = (PrincipalDetails) auth.getPrincipal();
                        	
                        	String language = "ko";
                        	if(!StringUtils.isBlank(userDetailsImpl.getLanguage())) {
                        		language = userDetailsImpl.getLanguage();
                        	}
                        	
                        	localeResolver.setLocale(request, response, new Locale(language));
                        }
                        
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
                        log.info(" ### {}, Last login time : {} ", auth.getName(), formatter.format(date));
                        
                        userService.updateLastLoginDt(auth.getName());
                        
                        Map<String, String> res = new HashMap<>();
                        res.put("result", "login success");
                        res.put("loginUser", auth.getName());
                        res.put("code", "200");
                        JSONObject json =  new JSONObject(res);
                        response.setContentType("application/json; charset=utf-8");
                        response.getWriter().print(json);
                    })
                    .failureHandler((request, response, exception)->{
                        String errMsg = "";
                        if(exception.getClass().isAssignableFrom(BadCredentialsException.class)){
                            errMsg = "Invalid username or password";
                            //response.setStatus(401);
                        }else{
                            errMsg = "UnKnown error - "+exception.getMessage();
                            //response.setStatus(400);
                        }
                        
                        zthhErrorService.save(ZthhErrorDTO.builder()
                                .errorMessage("MyWebSecurityConfigurerAdapter Error : " + exception.getMessage())
                                .build()
                        );
                        
                        Map<String, String> res = new HashMap<>();
                        res.put("result", errMsg);
                        res.put("code", "401");
                        JSONObject json =  new JSONObject(res);
                        response.setContentType("application/json; charset=utf-8");
                        response.getWriter().print(json);
                        
                    })
                    .permitAll();    
        
        
		http.oauth2Login()
				.userInfoEndpoint()
				.userService(customOAuth2UserService)
				.and()
				.successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication auth) throws IOException, ServletException {
						
                        if (auth != null && auth.isAuthenticated()) {
                        	PrincipalDetails userDetailsImpl = (PrincipalDetails) auth.getPrincipal();
                        	
                        	String language = "ko";
                        	if(!StringUtils.isBlank(userDetailsImpl.getLanguage())) {
                        		language = userDetailsImpl.getLanguage();
                        	}
                        	
                        	localeResolver.setLocale(request, response, new Locale(language));
                        }
						
						log.info("userInfo {}", auth.getPrincipal().toString());
						log.info("authentication {}", auth.toString());
						log.info("authentication Name {}", auth.getName());
						response.sendRedirect("/main");
					}
				});
        
        
        /**
         * 6.로그아웃 설정
         *   1) 로그아웃을 위해 호출 하는 주소
         *   2) 로그아웃 성공시 리다이렉트 주소
         *   3) 로그아웃 성공시 세션 무효화
         *   4) 로그아웃 성공시 쿠키 삭제
         *   5) 모두 로그 아웃에 접근 가능
         */
        http
		        	.logout()
		            .logoutUrl("/signout")
                    .logoutSuccessUrl("/main")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "SESSION")
                    .permitAll();        
        
    }

    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
}