package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;
import com.gvpt.admintool.bean.jpa.CodeConfigEntity;
import com.gvpt.admintool.bean.jpa.CodeConfigEntityKey;

/**
 * Repository : CodeConfig.
 */

public interface CodeConfigSpecRepository extends Repository<CodeConfigEntity, Integer>, JpaSpecificationExecutor<CodeConfigEntity>{

}