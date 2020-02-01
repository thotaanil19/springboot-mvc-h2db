package com.springboot.api.dao.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.api.domain.AdminUser;

/**
 * 
 * @author anilt
 *
 */
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
	
	AdminUser findByLoginId(String loginId);
	
	@Modifying
	@Query(nativeQuery = true, value = "insert into eqbdataapi_admin_users(id,login_id,password,active,access_level,name) values (:id,:loginId,:password,:active,:accessLevel,:name)")
	int save(@Param("id") Long id, @Param("loginId") String loginId,
			@Param("password") byte[] password,
			@Param("active") Integer active,
			@Param("accessLevel") Integer accessLevel,
			@Param("name") String name);
	
	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_admin_users")
	Long getNextSequenceValue();
	
	@Query("select au from AdminUser au order by accessLevel asc")
	List<AdminUser> getAdminUsers();
	
	@Modifying
	@Query("update AdminUser au set au.loginId = lower(:loginId),au.name = :name, " +
			"au.accessLevel = :accessLevel where au.id = :id ")
	Integer updateAdminUser(@Param("loginId") String loginId,@Param("name") String name,
			@Param("accessLevel") Integer accessLevel,@Param("id") Long id);

	@Modifying
	@Query("update AdminUser au set au.password = :newPassword where au.id = :id")
	Integer changePassword(@Param("id")Long id, @Param("newPassword") String newPassword);

}
