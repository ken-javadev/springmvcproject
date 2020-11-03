package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.gvpt.admintool.bean.jpa.UserAdminEntity;

/**
 * Repository : User.
 */
public interface UserJpaRepository extends PagingAndSortingRepository<UserAdminEntity, String> {
	UserAdminEntity findOneByEmail(String email);
	
	@Query("SELECT x FROM UserAdminEntity x WHERE x.userId=:userId and x.status=:status")
    UserAdminEntity findOneByIDandStatus(@Param("userId")String userId,@Param("status") Long status);
}
