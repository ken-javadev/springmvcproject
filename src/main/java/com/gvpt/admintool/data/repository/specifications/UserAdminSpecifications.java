package com.gvpt.admintool.data.repository.specifications;

import com.gvpt.admintool.bean.jpa.UserAdminEntity;
import com.gvpt.admintool.web.common.enumerations.AppConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class UserAdminSpecifications implements Specification<UserAdminEntity> {

    private String nameFilter;
    private Long accessRoleFilter;

    public UserAdminSpecifications(){}

	public UserAdminSpecifications(String nameFilter, Long accessRole) {
		super();
		this.nameFilter = nameFilter;
		this.accessRoleFilter = accessRole;
	}

	public String getNameFilter() {
		return nameFilter;
	}

	public void setNameFilter(String nameFilter) {
		this.nameFilter = nameFilter;
	}

	public Long getAccessRoleFilter() {
		return accessRoleFilter;
	}

	public void setAccessRoleFilter(Long accessRole) {
		this.accessRoleFilter = accessRole;
	}

	public Predicate toPredicate(Root<UserAdminEntity> root,
			CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
        List<Predicate> filters = new ArrayList<Predicate>();
        if(nameFilter  != null && !nameFilter.isEmpty()){
        	Predicate predicateName = criteriaBuilder.like(criteriaBuilder.upper(root.<String>get("name")), "%" + nameFilter.toUpperCase() + "%");
        	Predicate predicateUserId = criteriaBuilder.like(criteriaBuilder.upper(root.<String>get("userId")), "%" + nameFilter.toUpperCase() + "%");
			filters.add(criteriaBuilder.or(predicateName, predicateUserId));
        }
       
        if(accessRoleFilter != null){
        	filters.add(criteriaBuilder.equal(root.join("accessRole").get("accessRoleId"), accessRoleFilter));
        }
        filters.add(criteriaBuilder.equal(root.<Long>get("status"), AppConstants.ACTIVE));
        
        return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}