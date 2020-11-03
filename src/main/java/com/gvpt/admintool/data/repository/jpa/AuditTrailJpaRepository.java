package com.gvpt.admintool.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.gvpt.admintool.bean.jpa.AuditTrailEntity;

/**
 * Repository : AuditTrail.
 */
public interface AuditTrailJpaRepository extends PagingAndSortingRepository<AuditTrailEntity, Long> {
	@Query("SELECT x FROM AuditTrailEntity x WHERE x.userId=:userId and x.action='LOGIN' ORDER BY actionDatetime DESC")
	List<AuditTrailEntity> findLastLoginByUserId(@Param("userId")String userId);
}
