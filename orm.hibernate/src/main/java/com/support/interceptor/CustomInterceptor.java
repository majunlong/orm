package com.support.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;

@SuppressWarnings("serial")
public class CustomInterceptor extends EmptyInterceptor {

	@Override
	public Object getEntity(String entityName, Serializable id) {
		return super.getEntity(entityName, id);
	}
	
}
