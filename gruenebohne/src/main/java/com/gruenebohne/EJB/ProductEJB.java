package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Product;
import com.gruenebohne.model.ProductCategory;
import com.gruenebohne.model.Recipe;

@Stateless
@LocalBean
public class ProductEJB {

	@PersistenceContext
	private EntityManager em;

	private void cleanDatabase(){
		for(Recipe rec:em.createNamedQuery("AllRecipes", Recipe.class).getResultList())
			em.remove(rec);

		em.flush();

		for(Product p:em.createNamedQuery("AllProducts", Product.class).getResultList())
			em.remove(p);

		em.flush();


		for(ProductCategory cat:em.createNamedQuery("AllCategories", ProductCategory.class).getResultList())
			em.remove(cat);


		em.flush();
	}

	private void initializeData() {
		ProductCategory cat1 = createCategory(1, "Gemüse");
		ProductCategory cat2 = createCategory(2, "Obst");
		ProductCategory cat3 = createCategory(3, "Milchprodukte");
		ProductCategory cat4 = createCategory(4, "Roggenmischbrot");
		ProductCategory cat5 = createCategory(5, "Fleischprodukte");

		createProduct(1,  2.95, cat1, "Tomate");
		createProduct(2,  2.85, cat2, "Kartoffeln");
		createProduct(3,  5.95, cat2, "Karotten");
		createProduct(4,  0.95, cat1, "Radieschen");
		createProduct(5,  3.95, cat2, "Dunkle Trauben");
		createProduct(6,  4.95, cat2, "Helle Trauben");
		createProduct(7,  1.95, cat3, "Milch");
		createProduct(8,  0.95, cat1, "Champignos");
		createProduct(9,  4.42, cat4, "Roggenmischbrot");
		createProduct(10, 1.86, cat5, "Salami");

		createRecipe(11, 3.33, "Orientalische Zucchini-Zwiebel-Pfanne mit Bulgur");
		createRecipe(12, 4.99, "Afrikanischer Süßkartoffeleintopf");
		createRecipe(13, 2.77, "Jägerrouladen");
		createRecipe(14, 8.89, "Kürbisstrudel mit Pute");
	}

	private void createRecipe(long id, Double dPrice, String sName) {
		em.persist(new Recipe(id, sName, dPrice));
		em.flush();
	}

	private void createProduct(long id,Double dPrice, ProductCategory cat, String sName) {
		em.persist(new Product(id, sName, dPrice, cat));
		em.flush();
	}

	private ProductCategory createCategory(int nID, String sName) {
		ProductCategory cat = new ProductCategory(nID, sName);
		em.persist(cat);
		em.flush();
		return cat;
	}

	public List<Product> getAllProductsWithCategory() {

		if (em.createNamedQuery("AllProducts", Product.class).getResultList()
				.isEmpty()
				&& em.createNamedQuery("AllCategories", ProductCategory.class)
						.getResultList().isEmpty()) {
			initializeData();
		}

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
