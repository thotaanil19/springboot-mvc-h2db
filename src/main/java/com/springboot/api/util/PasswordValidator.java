package com.springboot.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
/**
 * 
 * @author anilt
 *
 */
@Component
public class PasswordValidator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordValidator.class);
	
	  private Pattern pattern_1;
	  private Matcher matcher_1;
	  private Pattern pattern_2;
	  private Matcher matcher_2;
	  private Pattern pattern_3;
	  private Matcher matcher_3;
	  private Pattern pattern_4;
	  private Matcher matcher_4;
	  
	  private Pattern emailPattern;
	  private Matcher emailMatcher;
 
	  private static final String PASSWORD_PATTERN_1 = 
			  "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{12,})";
	  private static final String PASSWORD_PATTERN_2 = 
			  "((?=.*\\d)(?=.*[a-z])(?=.*[!@#$_%]).{12,})";
	  private static final String PASSWORD_PATTERN_3 = 
			  "((?=.*\\d)(?=.*[A-Z])(?=.*[!@#$_%]).{12,})";
	  private static final String PASSWORD_PATTERN_4 = 
			  "((?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$_%]).{12,})";
	  
	  
	  private static final String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	  
	  public PasswordValidator(){
		  pattern_1 = Pattern.compile(PASSWORD_PATTERN_1);
		  pattern_2 = Pattern.compile(PASSWORD_PATTERN_2);
		  pattern_3 = Pattern.compile(PASSWORD_PATTERN_3);
		  pattern_4 = Pattern.compile(PASSWORD_PATTERN_4);
		  emailPattern = Pattern.compile(EMAIL_PATTERN);
	  }
	  
	  /**
	   * Validate password with regular expression
	   * @param password password for validation
	   * @return true valid password, false invalid password
	   */
	public boolean validatePassword(final String password) {
		
		LOGGER.info("Validating password...");

		matcher_1 = pattern_1.matcher(password);
		matcher_2 = pattern_2.matcher(password);
		matcher_3 = pattern_3.matcher(password);
		matcher_4 = pattern_4.matcher(password);

		return matcher_1.matches() 
				|| matcher_2.matches()
				|| matcher_3.matches()
				|| matcher_4.matches();

	}
	  
	/**
	 * Validates email address
	 * @param email
	 * @return true if given parameter is in email format else returns false
	 */
	public boolean validateEmail(String email) {
		LOGGER.info("Validating Email...");
		if(email != null) {
			emailMatcher = emailPattern.matcher(email.trim());
			return emailMatcher.matches();
		} else {
			return false;
		}
	}
	
	
}