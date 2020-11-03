/*
 * Created on 20 Jun 2017 ( Time 10:16:11 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.gvpt.admintool.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gvpt.admintool.bean.AccessRole;
import com.gvpt.admintool.bean.jpa.AccessRoleEntity;
import com.gvpt.admintool.business.service.AccessRoleService;
import com.gvpt.admintool.business.service.mapping.AccessRoleServiceMapper;
import com.gvpt.admintool.data.repository.jpa.AccessRoleJpaRepository;
import com.gvpt.admintool.data.repository.jpa.AccessRoleSpecRepository;
import com.gvpt.admintool.data.repository.specifications.AccessRoleSpecifications;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of AccessRoleService
 */
@Component
@Transactional
public class AccessRoleServiceImpl implements AccessRoleService {

	@Resource
	private AccessRoleJpaRepository accessRoleJpaRepository;

	@Resource
	private AccessRoleSpecRepository accessRoleSpecRepository;

	@Resource
	private AccessRoleServiceMapper accessRoleServiceMapper;
	
	
	public AccessRole findById(Long accessRoleId) {
		AccessRoleEntity accessRoleEntity = accessRoleJpaRepository.findOne(accessRoleId);
		return accessRoleServiceMapper.mapAccessRoleEntityToAccessRole(accessRoleEntity);
	}

	
	public List<AccessRole> findAll() {
		Iterable<AccessRoleEntity> entities = accessRoleJpaRepository.findAll();
		List<AccessRole> beans = new ArrayList<AccessRole>();
		for(AccessRoleEntity accessRoleEntity : entities) {
			beans.add(accessRoleServiceMapper.mapAccessRoleEntityToAccessRole(accessRoleEntity));
		}
		return beans;
	}

	
	public AccessRole save(AccessRole accessRole) {
		return update(accessRole) ;
	}

	
	public AccessRole create(AccessRole accessRole) {
		AccessRoleEntity accessRoleEntity=null;
		if( accessRole.getAccessRoleId() != null ) {
			accessRoleEntity = accessRoleJpaRepository.findOne(accessRole.getAccessRoleId());
		}
		if( accessRoleEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		accessRoleEntity = new AccessRoleEntity();
		accessRoleServiceMapper.mapAccessRoleToAccessRoleEntity(accessRole, accessRoleEntity);
		AccessRoleEntity accessRoleEntitySaved = accessRoleJpaRepository.save(accessRoleEntity);
		return accessRoleServiceMapper.mapAccessRoleEntityToAccessRole(accessRoleEntitySaved);
	}

	
	public AccessRole update(AccessRole accessRole) {
		AccessRoleEntity accessRoleEntity = accessRoleJpaRepository.findOne(accessRole.getAccessRoleId());
		accessRoleServiceMapper.mapAccessRoleToAccessRoleEntity(accessRole, accessRoleEntity);
		AccessRoleEntity accessRoleEntitySaved = accessRoleJpaRepository.save(accessRoleEntity);
		return accessRoleServiceMapper.mapAccessRoleEntityToAccessRole(accessRoleEntitySaved);
	}

	
	public void delete(Long accessRoleId) {
		accessRoleJpaRepository.delete(accessRoleId);
	}

	public AccessRoleJpaRepository getAccessRoleJpaRepository() {
		return accessRoleJpaRepository;
	}

	public void setAccessRoleJpaRepository(AccessRoleJpaRepository accessRoleJpaRepository) {
		this.accessRoleJpaRepository = accessRoleJpaRepository;
	}

	public AccessRoleServiceMapper getAccessRoleServiceMapper() {
		return accessRoleServiceMapper;
	}

	public void setAccessRoleServiceMapper(AccessRoleServiceMapper accessRoleServiceMapper) {
		this.accessRoleServiceMapper = accessRoleServiceMapper;
	}

	public Long countAll() {
		return accessRoleJpaRepository.count();
	}

	public List<AccessRole> findAll(Pageable pageable) {
		List<AccessRole> result = new ArrayList<AccessRole>();
		Page<AccessRoleEntity> entities = accessRoleJpaRepository.findAll(pageable);
		for(AccessRoleEntity accessRoleEntity : entities) {
			result.add(accessRoleServiceMapper.mapAccessRoleEntityToAccessRole(accessRoleEntity));
		}
		return result;
	}

	public List<AccessRole> findWithSpec(AccessRoleSpecifications specifications, Pageable pageable){
		List<AccessRole> result = new ArrayList<AccessRole>();
		Page<AccessRoleEntity> entities = accessRoleSpecRepository.findAll(specifications, pageable);
		for(AccessRoleEntity accessRoleEntity : entities) {
			result.add(accessRoleServiceMapper.mapAccessRoleEntityToAccessRole(accessRoleEntity));
		}
		return result;
	}

	public Long countWithSpec(AccessRoleSpecifications specifications){
		return accessRoleSpecRepository.count(specifications);
	}
}