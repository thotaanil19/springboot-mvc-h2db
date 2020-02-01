package com.springboot.api.service;

import java.util.List;

import com.springboot.api.domain.AdminUser;
import com.springboot.api.to.EquibaseDataSelesApiError;
import com.springboot.api.to.PaginationResultsTo;
import com.springboot.api.to.SortingCriteria;

/**
 * 
 * @author anilt
 *
 */
public interface AdminService {

	AdminUser createAdmin(AdminUser adminUser);

	PaginationResultsTo<AdminUser> getAdminUsers(Integer pageNum, Integer pageSize, SortingCriteria sortingCriteria);

	Boolean changePassword(AdminUser adminUser);

	AdminUser getAdminProfile();

	Boolean updateAdmin(AdminUser adminUser);

	AdminUser editAdminUser(AdminUser adminUser);

	Boolean verifyUserAlreadyExists(String loginId);

	List<EquibaseDataSelesApiError> validateAdminUserObject(AdminUser adminUser);

	AdminUser getAdminUserByLoginId(String loginId);

}
