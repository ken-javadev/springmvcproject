package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.gvpt.admintool.bean.jpa.AccessRoleEntity;

/**
 * Repository : AccessRole.
 */
public interface AccessRoleJpaRepository extends PagingAndSortingRepository<AccessRoleEntity, Long> {

}
