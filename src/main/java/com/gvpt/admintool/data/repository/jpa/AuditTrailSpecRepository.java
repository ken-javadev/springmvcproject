package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import com.gvpt.admintool.bean.jpa.AuditTrailEntity;

/**
 * Repository : AuditTrail.
 */

public interface AuditTrailSpecRepository extends Repository<AuditTrailEntity, Integer>, JpaSpecificationExecutor<AuditTrailEntity>{

}