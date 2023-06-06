package com.eun.tutorial.aspect;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eun.tutorial.dto.main.MenuControlDTO;
import com.eun.tutorial.dto.main.UserRequestHistoryDTO;
import com.eun.tutorial.service.main.MenuControlService;
import com.eun.tutorial.service.main.UserRequestHistoryService;

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
	private ThreadLocal<String> logDataYnThreadLocal = new ThreadLocal<>();

	// Pointcut: 모든 Controller의 메서드에 적용
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void controllerPointcut() {
	}

	// Before advice: 메서드 실행 전에 요청 정보 로깅
	@Before("controllerPointcut()")
	public void logRequest(JoinPoint joinPoint) {
		UserRequestHistoryDTO userRequestHistoryDTO = new UserRequestHistoryDTO();

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

		if (attributes != null) {

			HttpServletRequest request = attributes.getRequest();
			String url = request.getRequestURI();
			String method = request.getMethod();

			String logYn = "N";
			String logDataYn = "N";
			Map<String, String> logYnMap = menuControlService.getLogYnByUrlAndMethod(url, method);
			if (logYnMap != null) {
				logYn = logYnMap.get("LOG_YN");
				logDataYn = logYnMap.get("LOG_DATA_YN");
			} else {
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

			if (logYn.equals("Y")) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				// 요청 정보 추출
				String ip = request.getRemoteAddr();
				String user = authentication.getName();
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

				if (logDataYn.equals("Y")) {
					log.info("Request Data: {}", requestData);
					
					if(!requestData.equals("[]")) {
						if (requestData.length() > 2000) {
							requestData = requestData.substring(0, 2000);
						}
						userRequestHistoryDTO.setReqData(requestData);
					}
					logDataYnThreadLocal.set(logDataYn);
					userRequestHistoryThreadLocal.set(userRequestHistoryDTO);
				}
			}
		}
	}

	// AfterReturning advice: 메서드 실행 후에 응답 정보 로깅
	@AfterReturning(pointcut = "controllerPointcut()", returning = "result")
	public void logResponse(JoinPoint joinPoint, Object result) {

		String logDataYn = logDataYnThreadLocal.get();

		if (logDataYn != null && logDataYn.equals("Y")) {
			// 응답 정보 추출
			String responseData = result != null ? result.toString() : "";

			// 응답 정보 로깅
			log.info("Response Data: {}", responseData);

			if (responseData.length() > 2000) {
				responseData = responseData.substring(0, 2000);
			}

			UserRequestHistoryDTO userRequestHistoryDTO = userRequestHistoryThreadLocal.get();
			userRequestHistoryDTO.setResData(responseData);
			userRequestHistoryService.saveUserRequestHistory(userRequestHistoryDTO);
		}

		logDataYnThreadLocal.remove();
		userRequestHistoryThreadLocal.remove();
	}
}
