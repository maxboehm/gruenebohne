package com.gruenebohne.EJB;

import java.util.List;

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

	public List<ProductCategory> getAllCat() {
		System.out.println("Number of Categories: "+em.createNamedQuery("AllCategories", ProductCategory.class).getResultList().size());
		return em.createNamedQuery("AllCategories", ProductCategory.class).getResultList();
	}

}
