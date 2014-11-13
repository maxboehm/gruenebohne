package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.ProductCategory;

@Stateless
@LocalBean
public class CategoryEJB {

	@PersistenceContext
	private EntityManager em;
	
	@EJB StartupBean beanStartup;

	public List<ProductCategory> getAllCat() {
		beanStartup.startup();
		return em.createNamedQuery("AllCategories", ProductCategory.class).getResultList();
	}

}
