package com.springboot.api.dao;

import com.springboot.api.domain.AdminUser;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;

/**
 * 
 * @author anilt
 *
 */
public interface AdminDao {

	AdminUser createAdmin(AdminUser adminUser);

	PaginationResultsTo<AdminUser> getAdminUsers(Integer pageNum, Integer pageSize, SortingCriteria sortingCriteria);

	Boolean changePassword(AdminUser adminUser);

	AdminUser getAdminUserById(Long userId);

	Boolean updateAdmin(AdminUser adminUser);

	AdminUser editAdminUser(AdminUser adminUser);

	Boolean verifyUserAlreadyExists(String loginId);

	AdminUser getAdminUserByLoginId(String loginId);

}
