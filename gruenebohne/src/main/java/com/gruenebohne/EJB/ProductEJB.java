package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Product;
import com.gruenebohne.model.ProductCategory;

@Stateless
@LocalBean
public class ProductEJB {

	@PersistenceContext
	private EntityManager em;
	
	private void initializeData() {
		
		ProductCategory cat1 = new ProductCategory(1, "Gemüse");
		em.persist(cat1);
		em.flush();
		ProductCategory cat2 = new ProductCategory(2, "Obst");
		em.persist(cat2);
		em.flush();
		ProductCategory cat3 = new ProductCategory(3, "Milchprodukte");
		em.persist(cat3);
		em.flush();
		ProductCategory cat4 = new ProductCategory(4, "Roggenmischbrot");
		em.persist(cat4);
		em.flush();
		ProductCategory cat5 = new ProductCategory(5, "Fleischprodukte");
		em.persist(cat5);
		em.flush();
		
		
		Product product1 = new Product();
		product1.setProdId(1);
		product1.setProdName("Tomate");
		product1.setPrice(2.90);
		product1.setPictureURL("03.jpg.xhtml?ln=content");
		product1.setCategory(cat1);
		em.persist(product1);
		
		Product product2 = new Product();
		product2.setProdId(2);
		product2.setProdName("Kartoffeln");
		product2.setPrice(2.80);
		product2.setPictureURL("20.jpg.xhtml?ln=content");
		product2.setCategory(cat2);
		em.persist(product2);
		
		Product product3 = new Product();
		product3.setProdId(2);
		product3.setProdName("Karotten");
		product3.setPrice(5.90);
		product3.setPictureURL("21.jpg.xhtml?ln=content");
		product3.setCategory(cat2);
		
		Product product4 = new Product(4,"Radieschen","TestDescription", 0.95,"05.jpg.xhtml?ln=content", cat1);
		em.persist(product4);
		Product product5 = new Product(5,"Dunkle Trauben","TestDescription", 3.95,"02.jpg.xhtml?ln=content", cat2);
		em.persist(product5);
		Product product6 = new Product(6,"Helle Trauben","TestDescription", 3.95,"06.jpg.xhtml?ln=content", cat2);
		em.persist(product6);
		Product product7 = new Product(7,"Milch","TestDescription", 1.95,"07.jpg.xhtml?ln=content", cat3);
		em.persist(product7);
		Product product8 = new Product(8,"Champignos","TestDescription", 1.95,"08.jpg.xhtml?ln=content", cat1);
		em.persist(product8);
		Product product9 = new Product(9,"Roggenmischbrot","TestDescription", 1.95,"11.jpg.xhtml?ln=content", cat4);
		em.persist(product9);
		Product product10 = new Product(10,"Salami","TestDescription", 2.95,"14.jpg.xhtml?ln=content", cat5);
		em.persist(product10);
		em.flush();
		
	}
	
	public List<Product> getAllProductsWithCategory() {

		if(em.createNamedQuery("AllProducts", Product.class).getResultList().isEmpty() && 
				em.createNamedQuery("AllCategories", ProductCategory.class).getResultList().isEmpty()){
			initializeData();
		}
		
		List<Product> products = em.createNamedQuery("AllProducts", Product.class).getResultList();
		return products;
	}
	
	public Product getProduct(int prodId){
		
		List <Product> product = em.createNamedQuery("GetProduct", Product.class).setParameter("prodId", prodId).getResultList();
		
		if(product.isEmpty()){
			return null;
		}
		return product.get(0);
	}
	

}
