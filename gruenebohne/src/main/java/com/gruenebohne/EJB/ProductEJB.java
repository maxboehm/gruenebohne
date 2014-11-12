package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Product;

@Stateless
@LocalBean
public class ProductEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB StartupBean beanStartup;


	public List<Product> getAllProductsWithCategory() {
		beanStartup.startup();
		return em.createNamedQuery("AllProducts", Product.class).getResultList();
	}

	public Product getProduct(int prodId) {

		List<Product> product = em
				.createNamedQuery("GetProduct", Product.class)
				.setParameter("prodId", prodId).getResultList();

		if (product.isEmpty()) {
			return null;
		}
		return product.get(0);
	}

}
