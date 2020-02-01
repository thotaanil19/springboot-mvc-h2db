package com.springboot.api.service.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.api.dao.AdminDao;
import com.springboot.api.domain.AdminUser;
import com.springboot.api.service.AdminService;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.EquibaseDataSelesApiErrorCodes;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.PrincipalUser;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.PasswordValidator;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Service
public class AdminServiceImpl implements AdminService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private PasswordValidator validator;
	
	@PostConstruct
	public void setUpTestUser() {
		try {
			AdminUser adminUser = new AdminUser();
			adminUser.setLoginId("admin");
			adminUser.setPasswordStr("admin");
			adminUser.setAccessLevel(1);
			adminUser.setName("admin");
			adminUser.setId(1L);
			createAdmin(adminUser);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 * Create Admin User
	 * @param adminUser
	 * @return
	 */
	@Override
	@Transactional
	public AdminUser createAdmin(AdminUser adminUser) {
		LOGGER.info("Crearting Admin...");

		if(Util.isNull(adminUser)) {
			LOGGER.info("To create Admin, AdminUser object should not be null...");
			throw new IllegalArgumentException("To create Admin, AdminUser object should not be null...");
		} else if(Util.isEmptyString(adminUser.getPasswordStr())) {
			LOGGER.info("To create Admin, Admin User password should not be null...");
			throw new IllegalArgumentException("To create Admin, Admin User password should not be null...");
		}

		LOGGER.info("Becrypting the password...");
		String becryptedPwd = Util.getBCrptPassword(adminUser.getPasswordStr());
		LOGGER.info("Becrypting the password is done...");
		if(!Util.isNull(becryptedPwd)) {
			LOGGER.info("Converting password String to Byte Array...");
			adminUser.setPassword(becryptedPwd.getBytes(Charset.forName("UTF-8")));
		}

		LOGGER.info("Adding new Admin User in admin_users table...");
		adminUser = adminDao.createAdmin(adminUser);
		return adminUser;
	}
	
	/**
	 * Check Admin user already exists or not for login Id
	 * @param adminUser
	 * @return
	 */
	@Override
	public Boolean verifyUserAlreadyExists(String loginId) {
		Boolean userAlreadyExists = false;
		LOGGER.info("Verifing any admin user exists with login id");
		if(!Util.isEmptyString(loginId)) {
			LOGGER.info("Login Id : " + loginId);
			userAlreadyExists = adminDao.verifyUserAlreadyExists(loginId);
		}
		return userAlreadyExists;
	}
	
	
	/**
	 * Fetches all Admin Users From DB
	 * @param pageNum
	 * @param pageSize
	 * @param sortingCriteria
	 * @return PaginationResultsTo<AdminUser>  - List of Admin Users
	 */
	@Override
	public PaginationResultsTo<AdminUser> getAdminUsers(Integer pageNum, Integer pageSize, SortingCriteria sortingCriteria) {
		PaginationResultsTo<AdminUser> adminUsers = null;
		LOGGER.info("Getting Admin Users...");
		adminUsers = adminDao.getAdminUsers(pageNum, pageSize, sortingCriteria);
		if(!Util.isNull(adminUsers) && !Util.isNullOrEmptyCollection(adminUsers.getResults())) {
			for (AdminUser adminUser : adminUsers.getResults()) {
				adminUser.setPassword(null);
				adminUser.setPasswordStr(null);
			}
		}
		return adminUsers;
	}


	@Override
	public AdminUser getAdminProfile() {
		LOGGER.info("Getting AdminUser Profile object");
		PrincipalUser user = Util.getLoggedUserObject();
		AdminUser adminUser = null;
		if(!Util.isNull(user)) {
			LOGGER.info("LoggedIn user name : " + user.getUsername());
			adminUser = adminDao.getAdminUserById(user.getUserId());
		}
		return adminUser;
	}
	
	/**
	 * Gets AdminUser object by Admin login id
	 * @param loginId
	 * @return AdminUser
	 */
	@Override
	public AdminUser getAdminUserByLoginId(String loginId) {
		LOGGER.info("Getting AdminUser object by login id : " + loginId);
		AdminUser adminUser = null;
		if(!Util.isNull(loginId)) {
			adminUser = adminDao.getAdminUserByLoginId(loginId);
		}
		return adminUser;
	}

	/**
	 * Changing Admin password
	 * @param AdminUser
	 * @return True is password is updated  Successfully otherwise false
	 */
	@Override
	@Transactional
	public Boolean changePassword(AdminUser adminUser) {
		Boolean pwdUpdateStatus = null;
		if(!Util.isNull(adminUser) && !Util.isNull(adminUser.getId())) {
			LOGGER.info("Validating Password");
			Boolean isValidPwd = validator.validatePassword(adminUser.getPasswordStr());
			if(Util.isTrue(isValidPwd)) {
				String becryptedPwd = Util.getBCrptPassword(adminUser.getPasswordStr());
				adminUser.setPassword(becryptedPwd.getBytes(Charset.forName("UTF-8")));
				pwdUpdateStatus = adminDao.changePassword(adminUser);
				if(Util.isTrue(pwdUpdateStatus)) {
					LOGGER.info("Password Updated Successfully...");
				} else  {
					LOGGER.info("Password Updation is Un-Successfull...");
				}
			} else {
				throw new IllegalArgumentException("password validations failed");
			}
		} else {
			LOGGER.error("Admin User id is missing to update password...");
			throw new IllegalArgumentException("Admin User id is missing to update password...");
		}
		return pwdUpdateStatus;
	}

	/**
	 * Create Admin User
	 * @param adminUser
	 * @return
	 */
	@Override
	public Boolean updateAdmin(AdminUser adminUser) {
		Boolean isUpdate = false;
		LOGGER.info("Crearting Admin...");
		if(!Util.isNull(adminUser)) {
			isUpdate = adminDao.updateAdmin(adminUser);
		}
		return isUpdate;
	}


	/**
	 * Validates AdminUser object in Admin User Creation
	 * @param adminUser
	 * @return List<EquibaseDataSelesApiError>
	 */
	@Override
	public List<EquibaseDataSelesApiError> validateAdminUserObject(AdminUser adminUser) {
		
		EquibaseDataSelesApiErrorCodes equibaseDataSelesApiErrorCode = null;
		List<EquibaseDataSelesApiError> equibaseDataSelesApiErrors = new ArrayList<EquibaseDataSelesApiError>();
		
		if (null == adminUser) {
			equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_METHOD_ARGUMENTS;
			EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode,
					equibaseDataSelesApiErrorCode.getId(),
					equibaseDataSelesApiErrorCode.getDescription(), "Missing adminUser");
			equibaseDataSelesApiErrors.add(error);
		} else {
			String name = adminUser.getName();
	        String loginId = adminUser.getLoginId();
	        Integer accessLevel = adminUser.getAccessLevel();
	        Boolean isActive = adminUser.getIsActive();
			 // Validate name
	        if (Util.isNull(name) || Util.isEmptyString(name.trim())) {
	        	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_NAME;
	        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
	                    , equibaseDataSelesApiErrorCode.getId()
	                    , equibaseDataSelesApiErrorCode.getDescription()
	                    , "Admin User Name should not be null or empty");
	            equibaseDataSelesApiErrors.add(error);
	        } 
	        //Validate loginId
	        if (Util.isNull(loginId) || Util.isEmptyString(loginId.trim())) {
	        	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_LOGIN_ID;
	        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
	                    , equibaseDataSelesApiErrorCode.getId()
	                    , equibaseDataSelesApiErrorCode.getDescription()
	                    , "Admin User Login should not be null or empty");
	            equibaseDataSelesApiErrors.add(error);
	        } else {
	        	Pattern whitespace = Pattern.compile("\\s");
        		Matcher matcher = whitespace.matcher(loginId);
            	 if (matcher.find()) {
            			LOGGER.info("Admin Login Id is having white spaces, Remove spaces from Login Id String");
                 		equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.WHITE_SPACES_IN_LOGIN_ID;
                 		EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                 				, equibaseDataSelesApiErrorCode.getId()
                 				, equibaseDataSelesApiErrorCode.getDescription()
                 				, "Admin Login Id is having white spaces, Remove spaces from Login Id String");
                 		equibaseDataSelesApiErrors.add(error);
            	 } else {
            		 AdminUser adminUsr = adminDao.getAdminUserByLoginId(loginId);
                 	if(!Util.isNull(adminUsr)) {
                 		LOGGER.info("Admin User Login Id is already in Use, Choose diffent login id for new Admin User");
                 		equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.ADMIN_ALREADY_EXISTS_WITH_LOGIN_ID;
                 		EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                 				, equibaseDataSelesApiErrorCode.getId()
                 				, equibaseDataSelesApiErrorCode.getDescription()
                 				, "Admin Login Id is already in Use, Choose diffent login id for new Admin User");
                 		equibaseDataSelesApiErrors.add(error);
                 	}
            	 }
            }
	        
	        //Validate Password
	        if (Util.isEmptyString(adminUser.getPasswordStr())) {
            	LOGGER.info("In Admin User Creation, Admin User login password is missing");
            	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_PASSWORD;
            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                        , equibaseDataSelesApiErrorCode.getId()
                        , equibaseDataSelesApiErrorCode.getDescription()
                        , "Missing Password in Admin Creation");
            	equibaseDataSelesApiErrors.add(error);
            } else {
            	LOGGER.info("In Admin User Creation, Validating Admin Password");
            	Boolean isValidPwd = validator.validatePassword(adminUser.getPasswordStr());
            	if(!Util.isTrue(isValidPwd)) {
            		LOGGER.info("Password Validations are failed");
            		equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.PASSWORD_VALIDATIONS_FAILED;
                	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
                            , equibaseDataSelesApiErrorCode.getId()
                            , equibaseDataSelesApiErrorCode.getDescription()
                            , "Password Validations are failed");
                	equibaseDataSelesApiErrors.add(error);
            	}
            }

	        //Validate accessLevel
	        if (Util.isNull(accessLevel)) {
	        	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_ACCESS_LEVEL;
	        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
	                    , equibaseDataSelesApiErrorCode.getId()
	                    , equibaseDataSelesApiErrorCode.getDescription()
	                    , "Missing AccessLevel");
	            equibaseDataSelesApiErrors.add(error);
	        } else {
	            if (accessLevel < 0) {
	            	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.INVALID_ACCESS_LEVEL;
	            	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
	                        , equibaseDataSelesApiErrorCode.getId()
	                        , equibaseDataSelesApiErrorCode.getDescription()
	                        , "Invalid AccessLevel");
	                equibaseDataSelesApiErrors.add(error);
	            }
	        }

	        //Validate isActive
	        if (null == isActive) {
	        	equibaseDataSelesApiErrorCode = EquibaseDataSelesApiErrorCodes.MISSING_REQUIRED_FIELD_IS_ACTIVE;
	        	EquibaseDataSelesApiError error = new EquibaseDataSelesApiError(equibaseDataSelesApiErrorCode
	                    , equibaseDataSelesApiErrorCode.getId()
	                    , equibaseDataSelesApiErrorCode.getDescription()
	                    , "Missing isActive");
	            equibaseDataSelesApiErrors.add(error);
	        }
		}
		
		return equibaseDataSelesApiErrors;
	}


	/**
	 * Edit Admin User
	 * @param adminUser
	 * @return
	 */
	@Override
	@Transactional
	public AdminUser editAdminUser(AdminUser adminUser) {
		if(!Util.isNull(adminUser)) {
			LOGGER.info("Editing Admin User id : " + adminUser.getId());
			adminUser = adminDao.editAdminUser(adminUser);
		}
		return adminUser;
	}
	
}
