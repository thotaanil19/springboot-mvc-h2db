package com.springboot.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springboot.api.dao.repository.AdminUserRepository;
import com.springboot.api.dao.repository.CustomerUserRepository;
import com.springboot.api.domain.AdminUser;
import com.springboot.api.domain.CustomerUser;
import com.springboot.api.enums.UserRoleCodeEnum;
import com.springboot.api.to.PrincipalUser;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	@Autowired
	private CustomerUserRepository customerUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) {
		LOGGER.info("Loading User by user name : " + userName);
		User user = null;
		boolean isUserFound = false;
		
		AdminUser adminUser = adminUserRepository.findByLoginId(userName);
		
		if (!Util.isNull(adminUser)) {
			LOGGER.info("Found Admin User for user name : " + userName);
			if (Util.isTrue(adminUser.getIsActive())) {
				Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

				// $2a$10$QxLd7vQnoUVYdWnJosb7WOliaM.mLVMQ9/pjj8epVr/8OGaQoOQaG - EquibaseDataSalesApi@123
				// $2a$10$2mbWBs5TmB5zF7ERV5eWhORqm9H2irewrM6iiiJFDH91PfA1130DO - equilar1

				if (adminUser.getAccessLevel() == 1) {
					authorities.add(new SimpleGrantedAuthority(
							UserRoleCodeEnum.LEVEL_1.getValue()));
				} else if (adminUser.getAccessLevel() == 2) {
					authorities.add(new SimpleGrantedAuthority(
							UserRoleCodeEnum.LEVEL_2.getValue()));
				} else if (adminUser.getAccessLevel() == 3) {
					authorities.add(new SimpleGrantedAuthority(
							UserRoleCodeEnum.LEVEL_3.getValue()));
				} else if (adminUser.getAccessLevel() == 4) {
					authorities.add(new SimpleGrantedAuthority(
							UserRoleCodeEnum.LEVEL_4.getValue()));
				} else if (adminUser.getAccessLevel() == 5) {
					authorities.add(new SimpleGrantedAuthority(
							UserRoleCodeEnum.LEVEL_5.getValue()));
				} else {
					throw new RuntimeException(
							"Invalid Admin User Role for user name : "
									+ userName);
				}
				user = new PrincipalUser(userName, adminUser.getPasswordStr(), authorities, adminUser.getId());
				isUserFound = true;
			} else {
				LOGGER.info("Found Admin User for user name : " + userName
						+ " but is_active is false");
			}
		} else {
			 LOGGER.info("Admin User not found for user name : " + userName);
			 LOGGER.info("Checking Customer User for user name : " + userName);
			 CustomerUser customerUser = customerUserRepository.findByLoginId(userName);
			 if(!Util.isNull(customerUser)) {
				 LOGGER.info("Found Customer User for user name : " + userName);
				 if (Util.isTrue(customerUser.getIsActive())) {
					 Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
					 authorities.add(new SimpleGrantedAuthority(UserRoleCodeEnum.CUSTOMER.getValue()));
					user = new PrincipalUser(userName, customerUser.getPasswordStr(), authorities, customerUser.getId());
					 isUserFound = true;
				 } else {
					 LOGGER.info("Found Customer User for user name : " + userName + " but is_active is false");
				 }
			 }
		 }
		 if (!isUserFound) {
			 throw new UsernameNotFoundException("::: User with user name :" + userName + " not found");
		 }   
		 
		return user;

	}


}
