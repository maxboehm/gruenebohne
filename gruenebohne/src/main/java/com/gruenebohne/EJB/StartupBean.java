package com.gruenebohne.EJB;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.ejb.Singleton;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Producer;
import com.gruenebohne.model.Product;
import com.gruenebohne.model.ProductCategory;
import com.gruenebohne.model.Recipe;

@Singleton
@ApplicationScoped
public class StartupBean {

	boolean bInit = false;
	public void startup(){
		if(!bInit){
			cleanDatabase();
			initializeData();
			bInit = true;
		}
	}

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

		for(Producer prd:em.createNamedQuery("AllProducer", Producer.class).getResultList())
			em.remove(prd);


		em.flush();
	}

	private void initializeData() {
		ProductCategory cat1 = createCategory(1, "Gemüse");
		ProductCategory cat2 = createCategory(2, "Obst");
		ProductCategory cat3 = createCategory(3, "Milchprodukte");
		ProductCategory cat4 = createCategory(4, "Roggenmischbrot");
		ProductCategory cat5 = createCategory(5, "Fleischprodukte");

		Product p1  = createProduct(1,  2.95, cat1, "Tomate");
		Product p2  = createProduct(2,  2.85, cat2, "Kartoffeln");
		Product p3  = createProduct(3,  5.95, cat2, "Karotten");
		Product p4  = createProduct(4,  0.95, cat1, "Radieschen");
		Product p5  = createProduct(5,  3.95, cat2, "Dunkle Trauben");
		Product p6  = createProduct(6,  4.95, cat2, "Helle Trauben");
		Product p7  = createProduct(7,  1.95, cat3, "Milch");
		Product p8  = createProduct(8,  0.95, cat1, "Champignos");
		Product p9  = createProduct(9,  4.42, cat4, "Roggenmischbrot");
		Product p10 = createProduct(10, 1.86, cat5, "Salami");

		createRecipe(11, 3.33, "Orientalische Zucchini-Zwiebel-Pfanne mit Bulgur", p1, p2, p3, p4);
		createRecipe(12, 4.99, "Afrikanischer Süßkartoffeleintopf", p5, p6, p7, p8, p9);
		createRecipe(13, 2.77, "Jägerrouladen", p9, p10, p1, p2, p3);
		createRecipe(14, 8.89, "Kürbisstrudel mit Pute", p1, p3, p5, p7, p9, p10);

		createProducer(1, "Hans-Dampf", p1, p2, p3);
		createProducer(2, "Hans-Peter", p4, p5, p6);
		createProducer(3, "Hans-Johann", p1, p4, p5, p6);
	}

	private Producer createProducer(long id, String sName, Product ... products) {
		Producer r = new Producer(id, sName, products);
		r.setDescription(getDescriptionText("producer", r.getId(), "description.html"));
		r.setShortDescription(getDescriptionText("producer", r.getId(), "short-description.html"));
		r.setPicture(getPicture("producer", r.getId()));
		em.persist(r);
		em.flush();
		return r;
	}

	private Recipe createRecipe(long id, Double dPrice, String sName, Product ... products) {
		Recipe r = new Recipe(id, sName, dPrice, products);
		r.setProdDescription(getDescriptionText("recipes", r.getProdId(), "description.html"));
		r.setPicture(getPicture("recipes", r.getProdId()));
		em.persist(r);
		em.flush();
		return r;
	}

	private Product createProduct(long id,Double dPrice, ProductCategory cat, String sName) {
		Product p = new Product(id, sName, dPrice, cat);
		p.setProdDescription(getDescriptionText("products", p.getProdId(), "description.html"));
		p.setPicture(getPicture("products", p.getProdId()));
		em.persist(p);
		em.flush();
		return p;
	}

	private ProductCategory createCategory(int nID, String sName) {
		ProductCategory cat = new ProductCategory(nID, sName);
		em.persist(cat);
		em.flush();
		return cat;
	}

	@SuppressWarnings("resource")
	public String getDescriptionText(String sDirName, long nID, String sFileName){
		// Create Path
		String sPath = "resources/database/"+sDirName+"/"+nID+"/";
		// instantiate scanner
		java.util.Scanner scanner = null;
		try {
			// get resource stream
			InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(sPath+sFileName);
			scanner = new java.util.Scanner(is).useDelimiter("\\A");
			return scanner.hasNext() ? scanner.next() : "";
		} finally {
			if(scanner!=null) scanner.close();
		}
	}

	public byte[] getPicture(String sDirName, long nID){
		// Create Path
		String sPath = "resources/database/"+sDirName+"/"+nID+"/";
		try {
			InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(sPath+"img.jpg");
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

			return buffer.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

}
