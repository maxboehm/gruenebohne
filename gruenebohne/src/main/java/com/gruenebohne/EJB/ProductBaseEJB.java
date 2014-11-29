package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.ProductBase;

@Stateless
@LocalBean
public class ProductBaseEJB {

	@PersistenceContext
	private EntityManager em;

	public ProductBase getProduct(int prodId){
		// get product by id
		List <ProductBase> product = em.createNamedQuery("GetProductBase", ProductBase.class).setParameter("prodId", prodId).getResultList();

		// no product? return null
		if(product.isEmpty())
			return null;

		// otherwise return product
		return product.get(0);
	}


}
