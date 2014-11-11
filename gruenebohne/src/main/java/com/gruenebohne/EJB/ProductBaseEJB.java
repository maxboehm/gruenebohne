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

		List <ProductBase> product = em.createNamedQuery("GetProductBase", ProductBase.class).setParameter("prodId", prodId).getResultList();

		if(product.isEmpty()){
			return null;
		}
		return product.get(0);
	}


}
