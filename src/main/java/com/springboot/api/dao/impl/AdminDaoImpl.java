package com.springboot.api.dao.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.springboot.api.dao.AdminDao;
import com.springboot.api.dao.repository.AdminUserRepository;
import com.springboot.api.domain.AdminUser;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;
import com.springboot.api.util.Util;

/**
 * 
 * @author anilt
 *
 */
@Repository
public class AdminDaoImpl implements AdminDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);
	
	@Autowired
	private AdminUserRepository adminUserRepository;
	
	/**
	 * Create Admin User
	 * @param adminUser
	 * @return
	 */
	@Override
	@Transactional
	public AdminUser createAdmin(AdminUser adminUser) {
		LOGGER.info("Crearting Admin...");
		if(!Util.isNull(adminUser)) {
			AdminUser existedUser = adminUserRepository.findByLoginId(adminUser.getLoginId());
			if(Util.isNull(existedUser)) {
				adminUser = adminUserRepository.save(adminUser);
			} else {
				throw new RuntimeException("User Name already exists in AdminUsers table");
			}
		}
		return adminUser;
	}
	
	/**
	 * Fetches all Admin Users From DB
	 * @param pageNum
	 * @param pageSize
	 * @param sortingCriteria
	 * @return PaginationResultsTo<AdminUser> - List of Admin Users 
	 */
	@Override
	public PaginationResultsTo<AdminUser> getAdminUsers(Integer pageNum, Integer pageSize, SortingCriteria sortingCriteria) {

		LOGGER.info("Getting Admin Users...");
		LOGGER.info("Page Number : " + pageNum);
		LOGGER.info("Page Size : " + pageSize);
		LOGGER.info("Sorting Criteria : " + sortingCriteria);

		PaginationResultsTo<AdminUser> paginationResults = null;

		if(Util.isNull(pageNum) || Util.isNull(pageNum)) {
			throw new IllegalAccessError("Missing pageNum/pageSize method arguments");
		}

		try {
			Sort sort = null;
			if (sortingCriteria != null
					&& !Util.isEmptyString(sortingCriteria.getSortingField())
					&& !Util.isEmptyString(sortingCriteria.getSortOrder())) {

				if(!"ASC".equalsIgnoreCase(sortingCriteria
						.getSortOrder())
						&& !"DESC".equalsIgnoreCase(sortingCriteria
								.getSortOrder())) {
					LOGGER.warn("Invalid Sort Order. Sort Order Shold be ASC or DESC");
					throw new IllegalArgumentException("Invalid Sort Order. Sort Order Shold be ASC or DESC");
				}

				Order o1 = new Order(Sort.Direction.valueOf(sortingCriteria
						.getSortOrder().toUpperCase()), sortingCriteria.getSortingField())
				.ignoreCase();
				Order o2 = null;
				if ("name".equalsIgnoreCase(sortingCriteria.getSortingField())
						|| "accessLevel".equalsIgnoreCase(sortingCriteria.getSortingField())
						|| "isActive".equalsIgnoreCase(sortingCriteria.getSortingField())) {
					o2 = new Order(Direction.ASC, "loginId");
					sort = new Sort(new Order[] { o1, o2 });
				} else {
					sort = new Sort(new Order[] { o1 });
				}

			} else {
				sort = new Sort(Sort.Direction.DESC, "id");
			}

			PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, sort);

			Page<AdminUser> adminUsers = adminUserRepository.findAll(pageRequest);
			
			if(!Util.isNull(adminUsers)) {
				paginationResults = new PaginationResultsTo<AdminUser>();
				paginationResults.setPageNumber(pageNum);
				paginationResults.setPageSize(pageSize);
				paginationResults.setTotalNumberOfPages(adminUsers.getTotalPages());
				paginationResults.setTotalNumberOfResults(adminUsers.getTotalElements());
				paginationResults.setResults(adminUsers.getContent());
			}


		} catch (Exception e) {
			LOGGER.error(
					"Error while retreiving all Admin Users for Pagination -- page Number : "
							+ pageNum + " , Page Size : " + pageSize, e);
		}
		
		return paginationResults;

	}

	@Override
	public Boolean updateAdmin(AdminUser adminUser) {
		LOGGER.info("updating Admin...");
		Integer updatedRecords = 0;
		if(Util.isEmptyString(adminUser.getLoginId())){
			LOGGER.warn("invlaid loginid");
			throw new IllegalArgumentException("invlaid loginid");
		}
		if(Util.isEmptyString(adminUser.getName())){
			LOGGER.warn("invlaid name");
			throw new IllegalArgumentException("invlaid name");
		}
		if(!Util.isNull(adminUser)) {
			updatedRecords = adminUserRepository.updateAdminUser(adminUser.getLoginId(), adminUser.getName(), 
					adminUser.getAccessLevel(), adminUser.getId());
		}
		return updatedRecords > 0 ? true : false;
		
	}

	/**
	 * Admin password change
	 * @return
	 */
	@Override
	public Boolean changePassword(AdminUser adminUser) {
		Boolean status = false;
		if(!Util.isNull(adminUser)) {
			AdminUser au = adminUserRepository.findOne(adminUser.getId());
			au.setPassword(adminUser.getPassword());
			adminUserRepository.save(au);
			status = true;
		}
		return status;
	}

	/**
	 * Get AdminUser object by id
	 * @return
	 */
	@Override
	public AdminUser getAdminUserById(Long userId) {
		LOGGER.info("Getting AdminUser object by id : " + userId);
		AdminUser adminUser = null;
		if(!Util.isNull(userId)) { 
			AdminUser au =  adminUserRepository.findOne(userId);
			if(!Util.isNull(au)) {
				adminUser = new AdminUser();
				adminUser.setId(au.getId());
				adminUser.setLoginId(au.getLoginId());
				adminUser.setName(au.getName());
				adminUser.setPassword(null);
				adminUser.setIsActive(au.getIsActive());
				adminUser.setAccessLevel(au.getAccessLevel());
			}
		}
		return adminUser;
	}
	
	/**
	 * Edit Admin User
	 * @param adminUser
	 * @return
	 */
	@Override
	public AdminUser editAdminUser(AdminUser adminUser) {
		AdminUser updatedAdminUser = null;
		if(!Util.isNull(adminUser)) {
			LOGGER.info("Editing Admin User id : " + adminUser.getId());
			updatedAdminUser = adminUserRepository.findOne(adminUser.getId());
			if(!Util.isNull(updatedAdminUser)) {
				updatedAdminUser.setAccessLevel(adminUser.getAccessLevel());
				updatedAdminUser.setIsActive(adminUser.getIsActive());
				updatedAdminUser.setName(adminUser.getName());
				updatedAdminUser = adminUserRepository.save(updatedAdminUser);
			}
		}
		return updatedAdminUser;
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
			AdminUser au = adminUserRepository.findByLoginId(loginId);
			if(!Util.isNull(au)) {
				LOGGER.info("Admin User already exists with Login Id : " + loginId);
				userAlreadyExists = true;
			}
		}
		return userAlreadyExists;
	}

	/**
	 * Get Admin User by admin Login Id
	 * @param loginId
	 * @return AdminUser
	 */
	@Override
	public AdminUser getAdminUserByLoginId(String loginId) {
		AdminUser au = null;
		LOGGER.info("Getting AdminUser object by admin Login Id : " + loginId);
		if(!Util.isEmptyString(loginId)) {
			au = adminUserRepository.findByLoginId(loginId);
		}
		return au;
	}
	
}
