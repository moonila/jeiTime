package org.jeinnov.jeitime.persistence.dao;

import java.io.Serializable;
import java.util.List;

import org.jeinnov.jeitime.utils.HibernateUtil;


import com.trg.dao.hibernate.GenericDAOImpl;

;

public class GenericDAO<T, PK extends Serializable> extends
		GenericDAOImpl<T, PK> {

	public GenericDAO() {
		super();
		this.setSessionFactory(HibernateUtil.getSessionFactory());
	}

	public void removeAll(List<T> objects) {
		// if objects is null, skip removal
		if (objects != null) {
			for (T object : objects) {
				super.remove(object);
			}
		}
	}
}
