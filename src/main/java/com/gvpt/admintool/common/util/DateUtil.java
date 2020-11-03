/*
 * Created on 20 Jun 2017 ( Time 10:16:13 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.gvpt.admintool.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	
	private static SimpleDateFormat ddMMMyyyy = new SimpleDateFormat("dd-MMM-yyyy");
	private static SimpleDateFormat MMddyyyy = new SimpleDateFormat("MM/dd/yyyy");
	private static SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat ddMMyy = new SimpleDateFormat("dd/MM/yy");
	
	public static Date parse(String date) {
		try {
			return ddMMMyyyy.parse(date);
		} catch (ParseException e1) {
			try {
				return MMddyyyy.parse(date);
			} catch (ParseException e2) {
				try {
					return ddMMyyyy.parse(date);
				} catch (ParseException e3) {
					try {
						return ddMMyy.parse(date);
					} catch (ParseException e4) {
						return null;
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String toString(Date date){
		return ddMMMyyyy.format(date);
	}
	
	public static String toStringPattern(Date date, String pattern){
		return (new SimpleDateFormat(pattern)).format(date);
	}
	
	public static Calendar getTodayDate(){
		return setStartDate(new Date());
	}

	/**
	 * 
	 * calc diff day by 2 date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return number of day
	 */
	public static Number getDateDiff(Date startDate, Date endDate){
		Calendar start = setStartDate(startDate);
		Calendar end = setEndDate(endDate);
		long diff = end.getTimeInMillis()-start.getTimeInMillis();
		return diff / (24 * 60 * 60 * 1000);
	}
	

	public static long getHoursDiff(Date startDate, Date endDate){
		long diff =  endDate.getTime() - startDate.getTime();
		long diffHours = diff / (60 * 60 * 1000) % 24;
		return diffHours;
	}
	
	public static long getMinutesDiff(Date startDate, Date endDate){
		long diff =  endDate.getTime() - startDate.getTime();
		long diffMinutes = diff / (60 * 1000) % 60;
		return diffMinutes;
	}
	public static long getMinutesDiffCustom(Date startDate, Date endDate){
		long diff =  endDate.getTime() - startDate.getTime();
		long diffMinutes=0;
		if(diff>=3600000){
			return diffMinutes=60;
		}else{
			return diffMinutes = diff / (60 * 1000) % 60;
		}
	}
	
	/**
	 * set date time to all zero
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar setStartDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	public static Calendar setStartDateCinema(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	/**
	 * set date time to end date
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar setEndDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar;
	}
	
	public static Calendar setEndDateCinema(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 5);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar;
	}
	
	public static Date addMinutes(Date date, int add){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + add);
		return calendar.getTime();
	}
	
	public static Date addHours(Date date, int add){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + add);
		return calendar.getTime();
	}
	
	public static Date setHourAndMinute(Date date, int hour, int minute){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	
	public static Date subtractMinutes(Date date, int subtract){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - subtract);
		return calendar.getTime();
	}
	
	public static Date getStartOfDay(Date date){
		return setStartDate(date).getTime();
	}
	
	public static Date getCinemaStartOfDay(Date date){
		return setStartDateCinema(date).getTime();
	}
	
	public static Date getEndOfDay(Date date){
		return setEndDate(date).getTime();
	}
	
	public static Date getCinemaEndOfDay(Date date){
		return setEndDateCinema(date).getTime();
	}
	
	public static List<String> getDayBetweenOf(Date startDate, Date endDate) {
		List<String> result = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.get(Calendar.DAY_OF_WEEK);
		
		cal.setTime(startDate);
		result.add((new SimpleDateFormat("EE")).format(startDate));
		while (true) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			result.add((new SimpleDateFormat("EE")).format(cal.getTime()));
			if(cal.getTime().equals(endDate)){
				break;
			}
		}
		return result;
	}
}