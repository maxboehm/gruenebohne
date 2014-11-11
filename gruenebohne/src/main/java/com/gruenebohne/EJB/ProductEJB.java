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
		for(Product p:em.createNamedQuery("AllProducts", Product.class).getResultList())
			em.remove(p);

		em.flush();

		for(ProductCategory cat:em.createNamedQuery("AllCategories", ProductCategory.class).getResultList())
			em.remove(cat);

		em.flush();

		for(Recipe rec:em.createNamedQuery("AllRecipes", Recipe.class).getResultList())
			em.remove(rec);

		em.flush();


		ProductCategory cat1 = createCategory(1, "Gemüse");
		ProductCategory cat2 = createCategory(2, "Obst");
		ProductCategory cat3 = createCategory(3, "Milchprodukte");
		ProductCategory cat4 = createCategory(4, "Roggenmischbrot");
		ProductCategory cat5 = createCategory(5, "Fleischprodukte");

		createProduct("Tomate","TestDescription", 2.95,"/Users/Max/Desktop/w360/360-03.jpg", cat1);
		createProduct("Kartoffeln","TestDescription", 2.85,"/Users/Max/Desktop/w360/360-20.jpg", cat2);
		createProduct("Karotten","TestDescription", 5.95,"/Users/Max/Desktop/w360/360-21.jpg", cat2);
		createProduct("Radieschen","TestDescription", 0.95,"/Users/Max/Desktop/w360/360-05.jpg", cat1);
		createProduct("Dunkle Trauben","TestDescription", 3.95,"/Users/Max/Desktop/w360/360-02.jpg", cat2);
		createProduct("Helle Trauben","TestDescription", 3.95,"/Users/Max/Desktop/w360/360-01.jpg", cat2);
		createProduct("Milch","TestDescription", 1.95,"/Users/Max/Desktop/w360/360-07.jpg", cat3);
		createProduct("Champignos","TestDescription", 1.95,"/Users/Max/Desktop/w360/360-08.jpg", cat1);
		createProduct("Roggenmischbrot","TestDescription", 1.95,"/Users/Max/Desktop/w360/360-11.jpg", cat4);
		createProduct("Salami","TestDescription", 2.95,"/Users/Max/Desktop/w360/360-27.jpg", cat5);


		createRecipe("Orientalische Zucchini-Zwiebel-Pfanne mit Bulgur", "Sehr lecker", 5.00, "/Users/Max/Desktop/w360/bulgur.jpg");
		createRecipe("Afrikanischer Süßkartoffeleintopf", "Sehr lecker", 5.00, "/Users/Max/Desktop/w360/eintopf.jpg");
		createRecipe("Jägerrouladen", "Sehr lecker", 5.00, "/Users/Max/Desktop/w360/rol.jpg");
		createRecipe("Kürbisstrudel mit Pute", "Sehr lecker", 5.00, "/Users/Max/Desktop/w360/strudel.jpg");
	}

	private void createRecipe(String sName, String sDesc, Double dPrice, String sImage){
		Recipe recipe = new Recipe();
		recipe.setProdName(sName);
		recipe.setProdDescription(sDesc);
		recipe.setPictureByPath(sImage);
		recipe.setPrice(dPrice);
		em.persist(recipe);
		em.flush();
	}

	private void createProduct(String sName, String sDesc, Double dPrice, String sImage, ProductCategory cat){
		Product prd = new Product(sName,sDesc, dPrice,sImage, cat);
		em.persist(prd);
		em.flush();
	}

	private ProductCategory createCategory(int nID, String sName){
		ProductCategory cat = new ProductCategory(nID, sName);
		em.persist(cat);
		em.flush();
		return cat;
	}

	public List<Product> getAllProductsWithCategory() {

		//		if(em.createNamedQuery("AllProducts", Product.class).getResultList().isEmpty() &&
		//				em.createNamedQuery("AllCategories", ProductCategory.class).getResultList().isEmpty()){
		initializeData();
		//		}

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
