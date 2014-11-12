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

	private void initializeData() {

		ProductCategory cat1 = createCategory(15, "Gemüse");
		ProductCategory cat2 = createCategory(16, "Obst");
		ProductCategory cat3 = createCategory(17, "Milchprodukte");
		ProductCategory cat4 = createCategory(18, "Roggenmischbrot");
		ProductCategory cat5 = createCategory(19, "Fleischprodukte");

		createProduct(1, "Tomate", "TestDescription", 2.95,
				"/Users/Max/Desktop/w360/360-03.jpg", cat1);
		createProduct(2, "Kartoffeln", "TestDescription", 2.85,
				"/Users/Max/Desktop/w360/360-20.jpg", cat2);
		createProduct(3, "Karotten", "TestDescription", 5.95,
				"/Users/Max/Desktop/w360/360-21.jpg", cat2);
		createProduct(4, "Radieschen", "TestDescription", 0.95,
				"/Users/Max/Desktop/w360/360-05.jpg", cat1);
		createProduct(5, "Dunkle Trauben", "TestDescription", 3.95,
				"/Users/Max/Desktop/w360/360-02.jpg", cat2);
		createProduct(6, "Helle Trauben", "TestDescription", 3.95,
				"/Users/Max/Desktop/w360/360-01.jpg", cat2);
		createProduct(7, "Milch", "TestDescription", 1.95,
				"/Users/Max/Desktop/w360/360-07.jpg", cat3);
		createProduct(8, "Champignos", "TestDescription", 1.95,
				"/Users/Max/Desktop/w360/360-08.jpg", cat1);
		createProduct(9, "Roggenmischbrot", "TestDescription", 1.95,
				"/Users/Max/Desktop/w360/360-11.jpg", cat4);
		createProduct(10, "Salami", "TestDescription", 2.95,
				"/Users/Max/Desktop/w360/360-27.jpg", cat5);

		createRecipe(11, "Orientalische Zucchini-Zwiebel-Pfanne mit Bulgur",
				"Sehr lecker", 5.00, "/Users/Max/Desktop/w360/bulgur.jpg");
		createRecipe(12, "Afrikanischer Süßkartoffeleintopf", "Sehr lecker",
				5.00, "/Users/Max/Desktop/w360/eintopf.jpg");
		createRecipe(13, "Jägerrouladen", "Sehr lecker", 5.00,
				"/Users/Max/Desktop/w360/rol.jpg");
		createRecipe(14, "Kürbisstrudel mit Pute", "Sehr lecker", 5.00,
				"/Users/Max/Desktop/w360/strudel.jpg");
	}

	private void createRecipe(long id, String sName, String sDesc,
			Double dPrice, String sImage) {
		Recipe recipe = new Recipe();
		recipe.setProdName(sName);
		recipe.setProdDescription(sDesc);
		recipe.setPictureByPath(sImage);
		recipe.setPrice(dPrice);
		recipe.setProdId(id);
		em.persist(recipe);
		em.flush();
	}

	private void createProduct(long id, String sName, String sDesc,
			Double dPrice, String sImage, ProductCategory cat) {
		Product prd = new Product(id, sName, sDesc, dPrice, sImage, cat);
		em.persist(prd);
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

		List<Product> products = em.createNamedQuery("AllProducts",
				Product.class).getResultList();
		return products;
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
