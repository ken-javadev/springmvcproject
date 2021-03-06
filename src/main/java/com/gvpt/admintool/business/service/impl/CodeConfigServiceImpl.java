/*
 * Created on 7 Oct 2017 ( Time 18:41:59 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.gvpt.admintool.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gvpt.admintool.bean.CodeConfig;
import com.gvpt.admintool.bean.jpa.CodeConfigEntity;
import com.gvpt.admintool.bean.jpa.CodeConfigEntityKey;
import com.gvpt.admintool.business.service.CodeConfigService;
import com.gvpt.admintool.business.service.mapping.CodeConfigServiceMapper;
import com.gvpt.admintool.data.repository.jpa.CodeConfigJpaRepository;
import com.gvpt.admintool.data.repository.jpa.CodeConfigSpecRepository;
import com.gvpt.admintool.data.repository.specifications.CodeConfigSpecifications;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of CodeConfigService
 */
@Component
@Transactional
public class CodeConfigServiceImpl implements CodeConfigService {

	@Resource
	private CodeConfigJpaRepository codeConfigJpaRepository;

	@Resource
	private CodeConfigSpecRepository codeConfigSpecRepository;

	@Resource
	private CodeConfigServiceMapper codeConfigServiceMapper;
	
	
	public CodeConfig findById(Long codeConfigId, String codeType, String codeName) {
		CodeConfigEntityKey id = new CodeConfigEntityKey(codeConfigId, codeType, codeName);
		CodeConfigEntity codeConfigEntity = codeConfigJpaRepository.findOne(id);
		return codeConfigServiceMapper.mapCodeConfigEntityToCodeConfig(codeConfigEntity);
	}

	
	public List<CodeConfig> findAll() {
		Iterable<CodeConfigEntity> entities = codeConfigJpaRepository.findAll();
		List<CodeConfig> beans = new ArrayList<CodeConfig>();
		for(CodeConfigEntity codeConfigEntity : entities) {
			beans.add(codeConfigServiceMapper.mapCodeConfigEntityToCodeConfig(codeConfigEntity));
		}
		return beans;
	}

	
	public CodeConfig save(CodeConfig codeConfig) {
		return update(codeConfig) ;
	}

	
	public CodeConfig create(CodeConfig codeConfig) {
		CodeConfigEntityKey id = new CodeConfigEntityKey(codeConfig.getCodeConfigId(), codeConfig.getCodeType(), codeConfig.getCodeName());
		CodeConfigEntity codeConfigEntity = codeConfigJpaRepository.findOne(id);
		if( codeConfigEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		codeConfigEntity = new CodeConfigEntity();
		codeConfigServiceMapper.mapCodeConfigToCodeConfigEntity(codeConfig, codeConfigEntity);
		CodeConfigEntity codeConfigEntitySaved = codeConfigJpaRepository.save(codeConfigEntity);
		return codeConfigServiceMapper.mapCodeConfigEntityToCodeConfig(codeConfigEntitySaved);
	}

	
	public CodeConfig update(CodeConfig codeConfig) {
		CodeConfigEntityKey id = new CodeConfigEntityKey(codeConfig.getCodeConfigId(), codeConfig.getCodeType(), codeConfig.getCodeName());
		CodeConfigEntity codeConfigEntity = codeConfigJpaRepository.findOne(id);
		codeConfigServiceMapper.mapCodeConfigToCodeConfigEntity(codeConfig, codeConfigEntity);
		CodeConfigEntity codeConfigEntitySaved = codeConfigJpaRepository.save(codeConfigEntity);
		return codeConfigServiceMapper.mapCodeConfigEntityToCodeConfig(codeConfigEntitySaved);
	}

	
	public void delete(Long codeConfigId, String codeType, String codeName) {
		CodeConfigEntityKey id = new CodeConfigEntityKey(codeConfigId, codeType, codeName);
		codeConfigJpaRepository.delete(id);
	}

	public CodeConfigJpaRepository getCodeConfigJpaRepository() {
		return codeConfigJpaRepository;
	}

	public void setCodeConfigJpaRepository(CodeConfigJpaRepository codeConfigJpaRepository) {
		this.codeConfigJpaRepository = codeConfigJpaRepository;
	}

	public CodeConfigServiceMapper getCodeConfigServiceMapper() {
		return codeConfigServiceMapper;
	}

	public void setCodeConfigServiceMapper(CodeConfigServiceMapper codeConfigServiceMapper) {
		this.codeConfigServiceMapper = codeConfigServiceMapper;
	}

	public Long countAll() {
		return codeConfigJpaRepository.count();
	}

	public List<CodeConfig> findAll(Pageable pageable) {
		List<CodeConfig> result = new ArrayList<CodeConfig>();
		Page<CodeConfigEntity> entities = codeConfigJpaRepository.findAll(pageable);
		for(CodeConfigEntity codeConfigEntity : entities) {
			result.add(codeConfigServiceMapper.mapCodeConfigEntityToCodeConfig(codeConfigEntity));
		}
		return result;
	}

	public List<CodeConfig> findWithSpec(CodeConfigSpecifications specifications, Pageable pageable){
		List<CodeConfig> result = new ArrayList<CodeConfig>();
		Page<CodeConfigEntity> entities = codeConfigSpecRepository.findAll(specifications, pageable);
		for(CodeConfigEntity codeConfigEntity : entities) {
			result.add(codeConfigServiceMapper.mapCodeConfigEntityToCodeConfig(codeConfigEntity));
		}
		return result;
	}

	public Long countWithSpec(CodeConfigSpecifications specifications){
		return codeConfigSpecRepository.count(specifications);
	}
}
