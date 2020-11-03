package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import com.gvpt.admintool.bean.jpa.AccessRightEntity;

/**
 * Repository : AccessRight.
 */

public interface AccessRightSpecRepository extends Repository<AccessRightEntity, Integer>, JpaSpecificationExecutor<AccessRightEntity>{

}