package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import com.gvpt.admintool.bean.jpa.AccessRoleEntity;

/**
 * Repository : AccessRole.
 */

public interface AccessRoleSpecRepository extends Repository<AccessRoleEntity, Integer>, JpaSpecificationExecutor<AccessRoleEntity>{

}