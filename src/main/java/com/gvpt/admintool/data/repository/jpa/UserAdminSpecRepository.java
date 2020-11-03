package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;
import com.gvpt.admintool.bean.jpa.UserAdminEntity;

/**
 * Repository : UserAdmin.
 */

public interface UserAdminSpecRepository extends Repository<UserAdminEntity, Integer>, JpaSpecificationExecutor<UserAdminEntity>{

}