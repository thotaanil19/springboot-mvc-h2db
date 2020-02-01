package com.springboot.api.util;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import com.springboot.api.enums.UserRoleCodeEnum;
import com.springboot.api.to.PrincipalUser;

/**
 * 
 * @author anilt
 *
 */
public class Util {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);
	
	private static final BCryptPasswordEncoder PASSWORD_ENCODER =  new BCryptPasswordEncoder();

	public static final String NA = "N/A";
	public static final int DB_NA = -999999999;
	public static final int DB_NM = -888888888;
	public static final double DB_NA_NUM = -999999999;
	public static final String USER_ID_ERROR_MSG = "userId should not be null";
	public static final String OBJECT_ERROR_MSG = "Object should not be null";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String CONTENT_VALUE = "application/json; charset=utf-8";
	public static final String HTTP_STATUS = "HttpStatus:";
	public static final String PRODUCT_ID_ERROR_MSG = "Product Id should not be null or not less than zero (0) ";

	public static final String PRODUCT_IDS_ERROR_MSG = "Product Ids should not be null or empty";

	public static final String PRODUCT_LABEL_ERROR_MSG = "Product Label should not be null or empty";
	public static final String PRODUCT_TYPE_ERROR_MSG = "Product Type should not be null or empty";
	public static final String PRODUCT_LEVEL_ERROR_MSG = "Product Level should not be null or empty";
	public static final String PRODUCT_CACHETIME_ERROR_MSG = "Product CacheTime should not be null or empty";
	
	public static final int PASSWORD_LENGTH = 12;
	public static final Long INTERNAL_CUSTOMER_ID = 1L;
	public static final Long DEFAULT_ADMIN_ID = 1L;
	
	
	/**
	 * Utility to convert byte array to String
	 * @param ary
	 * @return
	 */
	public static String byteAryToString(byte[] ary) {
		String string = null;
		if(!Util.isNull(ary)) {
			string = new String(ary, Charset.forName("UTF-8"));
		}
		return string;
	}
	
	/**
	 * Utility to convert String to byte array
	 * @param string
	 * @return
	 */
	public static byte[] stringToByteAry(String string) {
		byte[] ary = null;
		if(!Util.isNull(string)) {
			ary = string.getBytes(Charset.forName("UTF-8"));
		}
		return ary;
	}
	/**
	 * To check if object is null or not
	 * @param object 
	 * @return  rue if the Object is null, else false
	 */
	public static Boolean isNull(Object object){
		return null == object;
	}

	/**
	 * To check if string is empty or not
	 * @param String 
	 * @return true if the String is empty, otherwise false
	 */
	public static Boolean isEmptyString(String value) {
		return ((isNull(value)) || (value.trim().length()==0)) ? true : false;
	}

	/**
	 * To Check the collection object is null or not
	 * @param collection
	 * @return true if collection is null or empty else false
	 */
	public static boolean isNullOrEmptyCollection(Collection<?> collection ){
		return  collection == null || collection.isEmpty() ;
	}

	/**
	 * To Check the collection object is null or not
	 * @param collection
	 * @return true if collection is null or empty else false
	 */
	public static boolean isNullOrEmptyMap(Map<?, ?> map){
		return  map == null || map.isEmpty() ;
	}
	
	/**
	 * Returns true if the Boolean is not null and true, false otherwise
	 * @param bool
	 * @return
	 */
	public static boolean isTrue(Boolean bool) {
		return bool != null && bool;
	}
	
	/**
	 * Returns logged-in user role
	 * @return
	 */
	public static String getLoggedUserRole() {

		String loggedInuserauthority = null;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		// Get logged in user Role

		if (!Util.isNull(authentication)
				&& !Util.isNullOrEmptyCollection(authentication
						.getAuthorities())) {
			for (GrantedAuthority authority : authentication.getAuthorities()) {
				if (authority.getAuthority().equals(
						UserRoleCodeEnum.LEVEL_1.getValue())
						|| authority.getAuthority().equals(
								UserRoleCodeEnum.LEVEL_2.getValue())
						|| authority.getAuthority().equals(
								UserRoleCodeEnum.LEVEL_3.getValue())
						|| authority.getAuthority().equals(
								UserRoleCodeEnum.LEVEL_4.getValue())
						|| authority.getAuthority().equals(
								UserRoleCodeEnum.LEVEL_5.getValue())
						|| authority.getAuthority().equals(
								UserRoleCodeEnum.CUSTOMER.getValue())) {
					loggedInuserauthority = authority.getAuthority();
				}
			}
		}
		return loggedInuserauthority;
	}
	
	/**
	 * Returns logged-in user object
	 * @return
	 */
	public static PrincipalUser getLoggedUserObject() {
		PrincipalUser principalUser = null;
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (!Util.isNull(authentication) && !"anonymousUser".equals(authentication.getPrincipal())) {
			principalUser = (PrincipalUser) authentication.getPrincipal();
		} else {
			throw new RuntimeException("Authentication is missing...");
		}
		return principalUser;
	}
	
	
	/**
	 * Returns logged-in user id
	 * @return
	 */
	public static Long getLoggedUserId() {
		
		Long userId = null;
		
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (!Util.isNull(authentication) && isUserAuthenticated()) {
			PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();
			if(!Util.isNull(principalUser) && !Util.isNull(principalUser.getUserId())) {
				userId = principalUser.getUserId();
			} else {
				throw new RuntimeException("Authentication is missing...");
			}
		} else {
			throw new RuntimeException("Authentication is missing...");
		}
		return userId;
	}
	
	/**
	 * Checks user is logged-in or not
	 * @return
	 */
	public static boolean isUserAuthenticated() {
		
		String userRole = getLoggedUserRole();
		
		return !Util.isEmptyString(userRole);
	}
	
	/**
	 * Utility to encode password for password reset functionality.
	 * @param password
	 * @return BCrypted password
	 */
	public static String getBCrptPassword (String password) {
		LOGGER.info("inside getBCrptPassword");
		String result = null;
		try {
			result = PASSWORD_ENCODER.encode(password);
		} catch(Exception e) {
			LOGGER.error("PASSWORD_ENCODER.encode has some problem", e);
		}
		LOGGER.info("after getBCrptPassword:"+result);
		return result;
	}
	
	/**
	 * Utility to generate dynamic BCrypted password
	 * @return BCrypted password
	 */
	public static String generateDynamicPassword (int passwordLength) {
		String newPassword = RandomStringUtils.random(passwordLength, true, true);
		LOGGER.info("Dynamic Pwd :" + newPassword);
		return getBCrptPassword(newPassword);
	}
	
	/**
	 * Utility to generate dynamic new api key
	 * @return new api key
	 */
	public static String generateDynamicApiKey () {
		String apiKey = RandomStringUtils.random(40, true, true);
		LOGGER.info("Dynamic Api key :" + apiKey);
		return apiKey;
	}
	
	/**
	 * Checks client IP address for Admin login
	 * @param request
	 * @return true if IP address is within range(Mentioned in 'ip_address.txt') else false
	 */
	public static boolean isValidrequestByIpAddess(HttpServletRequest request) {
		LOGGER.info("isValidrequestByIpAddess(HttpServletRequest) " + request);
		IpAddressMatcher matcher = null;
		List<String> ips = new Util().getIpAddress();
		boolean flag = false;
		if(!Util.isNullOrEmptyCollection(ips)) {
			for (String ip : ips) {
				matcher = new IpAddressMatcher(ip);
				try {
					flag = matcher.matches(request);
					if(flag) {
						break;
					}
				} catch (UnsupportedOperationException e) { 
					return false;
				}
			}
		}
		return flag;
		
	}
	
	/**
	 * Get IP address from 'ip_address.txt' file
	 * @return List<String>
	 */
	public List<String> getIpAddress() {
		LOGGER.info("getIpAddress()");
		List<String> ips = null;
		try {
			LOGGER.info("Reading ip_address.txt file from class path for IP address...");
			ClassLoader classLoader = getClass().getClassLoader();
			URL url = classLoader.getResource("ip_address.txt");
			if(!Util.isNull(url)) {
				ips = new ArrayList<String>();
				String path = URLDecoder.decode(url.getFile(), "utf-8");

				Reader	fileReader = new InputStreamReader(new FileInputStream(path), "utf-8");

				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line;
				while ((line = bufferedReader.readLine()) != null) {
					ips.add(line.replace(",", ""));
				}
				bufferedReader.close();
				fileReader.close();
			}
		} catch (Exception e) {
			LOGGER.error("Error while rerading file : ip_address.txt for IP address to restrict Admin Interface", e);
		}
		return ips;
	}
	
	/**Converts String to Date
	 * 
	 * @param dateInString
	 * @return Date
	 */
	public static Date stringToDateConversion(String dateInString) {
		Date date = null;
		if(!Util.isEmptyString(dateInString)) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			try {
				date = formatter.parse(dateInString);
			} catch (ParseException e) {
				LOGGER.error("Exception in converting String to Date...", e);
			}
		}
		return date;
	}
	
	/**Converts String to Date
	 * 
	 * @param dateInString
	 * @return Date
	 */
	public static Date stringToRaceDateConversion(String dateInString) {
		Date date = null;
		if(!Util.isEmptyString(dateInString)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = formatter.parse(dateInString);
			} catch (ParseException e) {
				LOGGER.error("Exception in converting String to Date...", e);
			}
		}
		return date;
	}
	
	/**
	 * Converts Date to specified format
	 * @param dateInString
	 * @param dateFormat
	 * @return - Date
	 */
	public static Date stringToDateConversion(String dateInString, String dateFormat) {
		Date date = null;
		if(!Util.isEmptyString(dateInString) && !Util.isEmptyString(dateFormat)) {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			try {
				date = formatter.parse(dateInString);
			} catch (ParseException e) {
				LOGGER.error("Exception in converting String to Date...", e);
			}
		}
		return date;
	}
	
	/**Converts Date to String
	 * 
	 * @param Date
	 * @return date in MM/dd/yyyy format
	 */
	public static String dateToStringInMMDDYYYY(Date date) {
		String dateInString = null;
		if(!Util.isNull(date)) {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			dateInString = formatter.format(date);
		}
		return dateInString;
	}
	
	/**Converts Date to yyyy-MM-dd HH:mm:ss format format
	 * 
	 * @param Date
	 * @return Date in yyyy-MM-dd HH:mm:ss format
	 */
	public static String dateToStringInYYYYMMDDHHMMSS(Date date) {
		String dateInString = null;
		if(!Util.isNull(date)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateInString = formatter.format(date);
		}
		return dateInString;
	}
	
	/**
	 * Converts date object to String format 'yyyy-MM-dd'
	 * @param date
	 * @return date as string in yyyy-MM-dd format
	 */
	public static String dateToStringInYYYYMMDD(Date date) {
		String dateInString = null;
		if(!Util.isNull(date)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			dateInString = formatter.format(date);
		}
		return dateInString;
	}
	
	
	/**Converts Date to String
	 * 
	 * @param Date
	 * @return date in dd-mmm-yyyy format(ex:12-May-2016)
	 */
	public static String dateToStringInDDMMMYYYY(Date date) {
		String dateInString = null;
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			dateInString = formatter.format(date);
		return dateInString;
	}
	
	/**
	 * Returns January 1st date of last year
	 * @return Date
	 */
	public static Date getFirstDayDateOfLastYear() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		
		calendar.add(Calendar.YEAR, -1);

		Date firstDayOfMonth = calendar.getTime();

		return firstDayOfMonth;
	}
	
	
	/**
	 * Calculates First day of current month
	 * @return Date - First day(date) of current month
	 */
	public static Date getFirstDateOfCurrentMonth() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date firstDayOfMonth = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		LOGGER.info("Today (MM/dd/yyyy)          : " + sdf.format(today));
		LOGGER.info("First Day of Month (MM/dd/yyyy): "
				+ sdf.format(firstDayOfMonth));

		return firstDayOfMonth;
	}
	
	/**
	 * Calculates Last day of current month
	 * @return Date - last day(date) of month
	 */
	public static Date getLastDateOfCurrentMonth() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, 0);
		

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, -1);

		Date lastDayOfMonth = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		LOGGER.info("Today (MM/dd/yyyy)          : " + sdf.format(today));
		LOGGER.info("Last Day of Month (MM/dd/yyyy): " + sdf.format(lastDayOfMonth));

		return lastDayOfMonth;
	}
	
	/**
	 * Build and returns Date Object based on Day number in current Month
	 * @param day
	 * @return Date 
	 */
	public static Date getFullDateFromDayNumberInCurrentMonth(Integer day) {

		Date fullDateObject = null;
		if (day != null) {

			Date today = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(today);

			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			fullDateObject = calendar.getTime();

			DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			LOGGER.info("Full Date Object (MM/dd/yyyy): "
					+ sdf.format(fullDateObject));

		}
		return fullDateObject;
	}
	
	
	/**
	 * Build and returns next month Date Object from fromDate param
	 * @param fromDate
	 * @return Date 
	 */
	public static Date getNextOneMonthDate(Date fromDate) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fromDate);

		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		Date nextMonthDate = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		LOGGER.info("Next month date (MM/dd/yyyy): " + sdf.format(nextMonthDate));

		return nextMonthDate;
		
	}
	
	/**
	 * Go back to number of days form today date and get that date.
	 * @param days
	 * 
	 * @return Date 
	 * 
	 */
	public static Date getBeforeDate(Integer days) {
		if(days != null) {
			Date date = new Date();
			Calendar cal = Calendar.getInstance();  
			cal.setTime(date);  
			cal.add(Calendar.DATE, -(days)); 
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			return  cal.getTime(); 
		}
		return null;
	}
	
	/**
	 * Go after to number of days form today date and get that date.
	 * @param days
	 * 
	 * @return Date 
	 * 
	 */
	public static Date getAfterDate(Integer days) {
		if(days != null) {
			Date date = new Date();
			Calendar cal = Calendar.getInstance();  
			cal.setTime(date);  
			cal.add(Calendar.DATE, +(days)); 
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			return  cal.getTime(); 
		}
		return null;
	}
	
	
	public static Date getTodayDateWithoutTimestamp() {
		Calendar cal = Calendar.getInstance();  
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return  cal.getTime(); 
		
	}
	
	/**
	 * Check current system Date
	 * @return year
	 */
	public static int currentSysDate() {
		int year = Calendar.getInstance().get(Calendar.YEAR);

		return year;
	}

}

