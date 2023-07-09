package com.eun.tutorial.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static String getSomeHourAgo(int hour) {
        // 현재 시간을 가져옴
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // 1시간 전으로 설정
        calendar.add(Calendar.HOUR_OF_DAY, - hour);
        Date oneHourAgo = calendar.getTime();

        // 원하는 형식으로 날짜 포맷을 지정
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");

        // 형식에 맞게 날짜를 문자열로 변환
        String formattedDate = dateFormat.format(oneHourAgo);

        return formattedDate;
    }
	
}
