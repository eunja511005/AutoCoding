package com.eun.tutorial.service.main;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UserManageServiceImplTest {

	@Test
	void testGetUserManageList_Seoul() {
		log.info(System.getProperty("user.timezone"));
		
		String timezone = System.getProperty("user.timezone");
		ZoneId asIsZoneId = ZoneId.of(timezone);
		ZoneId toBeZoneId = ZoneId.of("Asia/Seoul");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2023-06-05 00:28:58", formatter);
		
		ZonedDateTime gmtZonedDateTime = ZonedDateTime.of(dateTime, asIsZoneId);
        ZonedDateTime gmtPlus9ZonedDateTime = gmtZonedDateTime.withZoneSameInstant(toBeZoneId);
        
        log.info(gmtPlus9ZonedDateTime.toString());
	}
	
	@Test
	void testGetUserManageList_London() {
		log.info(System.getProperty("user.timezone"));
		
		String timezone = System.getProperty("user.timezone");
		ZoneId asIsZoneId = ZoneId.of(timezone);
		ZoneId toBeZoneId = ZoneId.of("Europe/London");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2023-06-05 00:28:58", formatter);
		
		ZonedDateTime gmtZonedDateTime = ZonedDateTime.of(dateTime, asIsZoneId);
        ZonedDateTime gmtPlus9ZonedDateTime = gmtZonedDateTime.withZoneSameInstant(toBeZoneId);
        
        log.info(gmtPlus9ZonedDateTime.toString());
	}
	
	@Test
	void testGetUserManageList_New_York() {
		log.info(System.getProperty("user.timezone"));
		
		String timezone = System.getProperty("user.timezone");
		ZoneId asIsZoneId = ZoneId.of(timezone);
		ZoneId toBeZoneId = ZoneId.of("America/New_York");
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2023-06-05 07:50:00", formatter);
		
		ZonedDateTime gmtZonedDateTime = ZonedDateTime.of(dateTime, asIsZoneId);
        ZonedDateTime gmtPlus9ZonedDateTime = gmtZonedDateTime.withZoneSameInstant(toBeZoneId);
        
        log.info(gmtPlus9ZonedDateTime.toString());
	}
	
	@Test
	void testGetUserManageList_Seoul2() {
		ZoneId asIsZoneId = ZoneId.of("GMT");
		ZoneId toBeZoneId = ZoneId.of("Asia/Seoul");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse("2023-06-05 00:28:58", formatter);
		
		ZonedDateTime gmtZonedDateTime = ZonedDateTime.of(dateTime, asIsZoneId);
		ZonedDateTime gmtPlus9ZonedDateTime = gmtZonedDateTime.withZoneSameInstant(toBeZoneId);
		
		log.info(gmtPlus9ZonedDateTime.toString());
	}

}
