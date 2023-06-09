package com.eun.tutorial.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eun.tutorial.dto.main.MenuControlDTO;
import com.eun.tutorial.dto.main.UserRequestHistoryDTO;
import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.service.main.UserRequestHistoryService;
import com.eun.tutorial.util.AuthUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UserRequestLoggingAspect {

	private final MenuControlService menuControlService;
	private final UserRequestHistoryService userRequestHistoryService;
	private ThreadLocal<UserRequestHistoryDTO> userRequestHistoryThreadLocal = new ThreadLocal<>();
	private ThreadLocal<String> logYnThreadLocal = new ThreadLocal<>();
	private ThreadLocal<String> logDataYnThreadLocal = new ThreadLocal<>();

	//@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controllerPointcut() {
	}

	//@Before("controllerPointcut()")
	public void logRequest(JoinPoint joinPoint) {
		UserRequestHistoryDTO userRequestHistoryDTO = new UserRequestHistoryDTO();

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attributes != null) {

			HttpServletRequest request = attributes.getRequest();
			String url = request.getRequestURI();
			String method = request.getMethod();

			String logYn;
			String logDataYn;
			MenuControlDTO matchMenuControlDTO = getMenuControlDTO(url, method);
			
			if (matchMenuControlDTO==null) {
				logYn = "Y";
				logDataYn = "N";
				saveMenuControl(url, method, logYn, logDataYn);
			}else {
				logYn = matchMenuControlDTO.getLogYn();
				logDataYn = matchMenuControlDTO.getLogDataYn();
			}
			
			if (logYn.equals("Y")) {

				// 요청 정보 추출
				String ip = request.getRemoteAddr();
				String user = AuthUtils.getLoginUser();
				String requestData = Arrays.toString(joinPoint.getArgs());

				// 요청 정보 로깅
				log.info("URL: {}", url);
				log.info("HTTP Method: {}", method);
				log.info("IP Address: {}", ip);
				log.info("User: {}", user);

				userRequestHistoryDTO.setUrl(url);
				userRequestHistoryDTO.setMethod(method);
				userRequestHistoryDTO.setReqIp(ip);
				userRequestHistoryDTO.setReqUser(user);

				log.info("Request Data by joinPoint.getArgs(): {}", requestData);

				if (logDataYn.equals("Y") && (!requestData.equals("[]"))) {
					if (requestData.length() > 2000) {
						requestData = requestData.substring(0, 2000);
					}
					userRequestHistoryDTO.setReqData(requestData);
				}
			}
			
			logYnThreadLocal.set(logYn);
			logDataYnThreadLocal.set(logDataYn);
			userRequestHistoryThreadLocal.set(userRequestHistoryDTO);
		}
	}

	private MenuControlDTO getMenuControlDTO(String url, String method) {
	    List<MenuControlDTO> menuControlDTOList = menuControlService.getMenuControlList();

	    for (MenuControlDTO menuControlDTO : menuControlDTOList) {
	        if (menuControlDTO.getMethod().equals(method)) {
	            if (menuControlDTO.getUrl().endsWith("*")) {
	                String prefix = menuControlDTO.getUrl().substring(0, menuControlDTO.getUrl().length() - 1);
	                if (url.startsWith(prefix)) {
	                    return menuControlDTO;
	                }
	            } else {
	                if (url.equals(menuControlDTO.getUrl())) {
	                    return menuControlDTO;
	                }
	            }
	        }
	    }

	    return null;
	}


	private void saveMenuControl(String url, String method, String logYn, String logDataYn) {
		MenuControlDTO menuControlDTO = new MenuControlDTO();
		menuControlDTO.setId("menuControl_" + UUID.randomUUID());
		menuControlDTO.setLogYn(logYn);
		menuControlDTO.setLogDataYn(logDataYn);
		menuControlDTO.setMethod(method);
		menuControlDTO.setRoleId("ANY");
		menuControlDTO.setUrl(url);
		menuControlDTO.setCreateId("UserRequestLoggingAspect.java");
		menuControlDTO.setUpdateId("UserRequestLoggingAspect.java");
		menuControlService.saveMenuControl(menuControlDTO);
	}

	//@AfterReturning(pointcut = "controllerPointcut()", returning = "result")
	public void logResponse(JoinPoint joinPoint, Object result) {

		String logYn = logYnThreadLocal.get();
		String logDataYn = logDataYnThreadLocal.get();

		String responseData = "";
		if (logDataYn != null && logDataYn.equals("Y")) {
			// 응답 정보 추출
			responseData = result != null ? result.toString() : "";

			// 응답 정보 로깅
			log.info("Response Data: {}", responseData);

			if (responseData.length() > 2000) {
				responseData = responseData.substring(0, 2000);
			}
		}
		
		UserRequestHistoryDTO userRequestHistoryDTO = userRequestHistoryThreadLocal.get();
		userRequestHistoryDTO.setResData(responseData);
		
		if(logYn != null && logYn.equals("Y")) {
			userRequestHistoryService.saveUserRequestHistory(userRequestHistoryDTO);
		}

		logYnThreadLocal.remove();
		logDataYnThreadLocal.remove();
		userRequestHistoryThreadLocal.remove();
	}
}
