package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.gvpt.admintool.bean.jpa.UserAdminEntity;

/**
 * Repository : UserAdmin.
 */
public interface UserAdminJpaRepository extends PagingAndSortingRepository<UserAdminEntity, Long> {

}
