package com.springboot.api.util;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;

import com.springboot.api.AbstractTest;

/**
 * 
 * @author anilt
 *
 */
public class UtilTest extends AbstractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilTest.class);
	
	@Test
	public void testByteAryToStringAndViceVersa() {
		
		String string1 = "menlo@123";
		byte[] byteAry = Util.stringToByteAry(string1);
		
		Assert.assertNotNull(byteAry);
		
		String string2 = Util.byteAryToString(byteAry);
		
		Assert.assertNotNull(string2);
		Assert.assertEquals(string1, string2);
		
		
	}
	
	@Test
	public void testUtility() {
		
		Assert.assertFalse(Util.isNull("Hai"));
		Assert.assertTrue(Util.isNull(null));
		
		Assert.assertFalse(Util.isEmptyString("Hai"));
		Assert.assertTrue(Util.isEmptyString(null));
		Assert.assertTrue(Util.isEmptyString(" "));
		
		Assert.assertTrue(Util.isNullOrEmptyCollection(null));
		Assert.assertTrue(Util.isNullOrEmptyCollection(new ArrayList<Long>()));
		List<Long> list = new ArrayList<Long>();
		list.add(1L);
		Assert.assertFalse(Util.isNullOrEmptyCollection(list));
		
		Assert.assertTrue(Util.isNullOrEmptyMap(null));
		Assert.assertTrue(Util.isNullOrEmptyMap(new HashMap<Long, Long>()));
		Map<Long, Long> map = new HashMap<Long, Long>();
		map.put(1L, 1L);
		Assert.assertFalse(Util.isNullOrEmptyMap(map));
		
		Assert.assertFalse(Util.isTrue(null));
		Assert.assertFalse(Util.isTrue(false));
		Assert.assertTrue(Util.isTrue(true));
		
		Assert.assertNotNull(Util.generateDynamicApiKey());
		
		Assert.assertNotNull(Util.generateDynamicPassword(12));
		
	}
	
	@Test
	public void testDates() {
		
		Date todayDate = new Date();
		
		Date oneMonthFromToday = Util.getNextOneMonthDate(todayDate);
		
		LOGGER.info(todayDate + " - " +oneMonthFromToday);
		
		Assert.assertNotNull(oneMonthFromToday);
		
		Assert.assertNotNull(Util.getFullDateFromDayNumberInCurrentMonth(1));
		
		Assert.assertNotNull(Util.getLastDateOfCurrentMonth());
		
		Assert.assertNotNull(Util.getFirstDateOfCurrentMonth());
		
		String todayDateInYYYYMMDDHHMMSS = Util.dateToStringInYYYYMMDDHHMMSS(todayDate);
		LOGGER.info("Today (yyyy-MM-dd HH:mm:ss) : " + todayDateInYYYYMMDDHHMMSS);
		Assert.assertNotNull(todayDateInYYYYMMDDHHMMSS);
		
		String todayDateInMMDDYYYY = Util.dateToStringInMMDDYYYY(todayDate);
		LOGGER.info("Today (MM/dd/yyyy) : " + todayDateInMMDDYYYY);
		Assert.assertNotNull(todayDateInMMDDYYYY);
		
		Date dateInYYYYMMDD = Util.stringToDateConversion("2016-02-16", "yyyy-MM-dd");
		LOGGER.info("Date (yyyy-MM-dd) : " + dateInYYYYMMDD);
		Assert.assertNotNull(dateInYYYYMMDD);
		
		Date dateInMMDDYYYY = Util.stringToDateConversion("02/16/2016");
		LOGGER.info("Date (MM/DD/2016) : " + dateInMMDDYYYY);
		Assert.assertNotNull(dateInMMDDYYYY);
		
	}
	
	
	@Test
	public void testIsValidrequestByIpAddess() {

		MockHttpServletRequest req1 = new MockHttpServletRequest();

		req1.setRemoteAddr("199.115.16.0");

		boolean isValidIpAddress1 = Util.isValidrequestByIpAddess(req1);

		Assert.assertTrue(isValidIpAddress1);

		MockHttpServletRequest req2 = new MockHttpServletRequest();

		req2.setRemoteAddr("1.1.1.1");

		boolean isValidIpAddress2 = Util.isValidrequestByIpAddess(req2);

		Assert.assertFalse(isValidIpAddress2);

	}
	
	@Test
	public void testGetLoggedUserRole() {
		
		getMockAuthentication();
		
		Assert.assertNotNull(Util.getLoggedUserRole());
		
		Assert.assertNotNull(Util.getLoggedUserObject());
		
		Assert.assertNotNull(Util.getLoggedUserId());
		
		Assert.assertNotNull(Util.isUserAuthenticated());
		
		Assert.assertTrue(Util.isUserAuthenticated());
		
		
		
		
	}
	
	
}
