package com.springboot.api.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.api.domain.AdminAction;

public interface AdminActionsRepository extends JpaRepository<AdminAction, Long> {

	@Query(nativeQuery = true, value = "select (case when max(id) = null then 1 else max(id) + 1 end) from eqbdataapi_admin_actions")
	Long getNextSequenceValue();
	
}
