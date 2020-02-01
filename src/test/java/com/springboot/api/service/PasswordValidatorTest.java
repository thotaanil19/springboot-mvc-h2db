package com.springboot.api.service;

import org.junit.Assert;
import org.junit.Test;

import com.springboot.api.AbstractTest;
import com.springboot.api.util.PasswordValidator;

public class PasswordValidatorTest extends AbstractTest {

	
	@Test
	public void test() {
		PasswordValidator obj = new PasswordValidator();
		Assert.assertFalse(obj.validatePassword("123"));
		Assert.assertTrue(obj.validatePassword("1Aa2345678689"));
		Assert.assertTrue(obj.validatePassword("1@a2345678689"));
		Assert.assertFalse(obj.validatePassword("1@a2345678"));
		Assert.assertTrue(obj.validatePassword("1@a234567822"));
		
		Assert.assertFalse(obj.validateEmail("1@a234567822"));
		Assert.assertFalse(obj.validateEmail(null));
		Assert.assertFalse(obj.validateEmail(""));
		Assert.assertFalse(obj.validateEmail(" "));
		Assert.assertFalse(obj.validateEmail("anil@gmail"));
		Assert.assertFalse(obj.validateEmail("anilgmail.com"));
		Assert.assertFalse(obj.validateEmail("@gmail.com"));
		Assert.assertTrue(obj.validateEmail("anil@gmail.com"));
	}
	

}
