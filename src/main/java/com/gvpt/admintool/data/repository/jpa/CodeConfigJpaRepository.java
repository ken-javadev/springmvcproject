package com.gvpt.admintool.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.gvpt.admintool.bean.jpa.CodeConfigEntity;
import com.gvpt.admintool.bean.jpa.CodeConfigEntityKey;

/**
 * Repository : CodeConfig.
 */
public interface CodeConfigJpaRepository extends PagingAndSortingRepository<CodeConfigEntity, CodeConfigEntityKey> {

}
