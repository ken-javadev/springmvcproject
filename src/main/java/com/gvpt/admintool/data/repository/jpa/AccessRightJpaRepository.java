package com.gvpt.admintool.data.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.gvpt.admintool.bean.jpa.AccessRightEntity;

/**
 * Repository : AccessRight.
 */
public interface AccessRightJpaRepository extends PagingAndSortingRepository<AccessRightEntity, Long> {
	@Query("SELECT x FROM AccessRightEntity x ORDER BY x.accessRightId ASC")
	List<AccessRightEntity> findAllOrderByID();
}
