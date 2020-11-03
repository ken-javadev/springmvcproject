package com.gvpt.admintool.data.repository.specifications;

import com.gvpt.admintool.bean.jpa.AccessRightEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AccessRightSpecifications implements Specification<AccessRightEntity> {

    private final JSONObject criteria;

    public AccessRightSpecifications(JSONObject criteria) {
        this.criteria = criteria;
    }

	public Predicate toPredicate(Root<AccessRightEntity> root,
			CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		Iterator<?> keys = criteria.keys();
        List<Predicate> filters = new ArrayList<Predicate>();

        if (criteria.length() != 0) {

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = null;

                try {
                    value = criteria.get(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (value != null) {
                	if(value instanceof String){
                		filters.add(criteriaBuilder.like(criteriaBuilder.upper(root.<String>get(key)), "%" + value.toString().toUpperCase() + "%"));
                	} else if(value instanceof Date){
                		filters.add(criteriaBuilder.lessThanOrEqualTo(root.<Date>get(key), (Date)value));
                	} else if(value instanceof Integer){
                		filters.add(criteriaBuilder.equal((root.<Integer>get(key)), value));
                	}
                }
            }
        }
        return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
	}

}