package com.gruenebohne.EJB;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.ejb.Singleton;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Customer;
import com.gruenebohne.model.Producer;
import com.gruenebohne.model.Product;
import com.gruenebohne.model.ProductCategory;
import com.gruenebohne.model.Recipe;
import com.gruenebohne.model.Record;
import com.gruenebohne.model.RecordItem;

@Singleton
@ApplicationScoped
public class StartupBean {

	boolean bInit = false;
	public void startup(){
		if(!bInit){
			//cleanDatabase();
			initializeData();
			bInit = true;
			System.out.println("DATA INITIALIZED");
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
		ProductCategory cat3 = createCategory(3, "Eiweißprodukte");
		ProductCategory cat4 = createCategory(4, "Backwaren");
		ProductCategory cat5 = createCategory(5, "Fleischprodukte");

		Product p1  = createProduct(1,  2.95, cat1, "Tomaten");
		Product p2  = createProduct(2,  2.85, cat1, "Kartoffeln");
		Product p3  = createProduct(3,  5.95, cat1, "Karotten");
		Product p4  = createProduct(4,  0.95, cat1, "Radieschen");
		Product p5  = createProduct(5,  3.95, cat2, "Dunkle Trauben");
		Product p6  = createProduct(6,  4.95, cat2, "Helle Trauben");
		Product p7  = createProduct(7,  1.95, cat3, "Milch");
		Product p8  = createProduct(8,  0.95, cat1, "Champignons");
		Product p9  = createProduct(9,  4.42, cat4, "Roggenmischbrot");
		Product p10 = createProduct(10, 1.86, cat5, "Salami");
		Product p11 = createProduct(11, 1.99, cat3, "Eier");
		Product p12 = createProduct(12, 0.99, cat1, "Zwiebeln");
		Product p13 = createProduct(13, 2.99, cat5, "Schinkenspeck");
		Product p14 = createProduct(14, 0.89, cat1, "Knoblauch");
		Product p15 = createProduct(15, 3.59, cat5, "Roulade vom Rind");
		Product p16 = createProduct(16, 0.69, cat1, "Zucchini");
		Product p17 = createProduct(17, 4.39, cat5, "Kasseler");
		Product p18 = createProduct(18, 1.79, cat2, "Äpfel");
		Product p19 = createProduct(19, 0.49, cat4, "Roggenbrötchen");
		Product p20 = createProduct(20, 12.00, cat5, "Rinderhüftsteak");
		Product p21 = createProduct(21, 4.67, cat3, "Gouda");
		Product p22 = createProduct(22, 1.20, cat2, "Nudeln-Penne");


		createRecipe(26, 5.89, "Kartoffelgratin", p2, p7, p14, p21);
		createRecipe(27, 1.99, "Fettarme Pommes", p2);
		createRecipe(23, 3.33, "Ofenkartoffeln mit frischen Kräutern", p2);
		createRecipe(24, 4.99, "Afrikanischer Süßkartoffeleintopf", p2, p12, p14, p1);
		createRecipe(25, 7.77, "Kasselerbraten", p17, p12, p2);
		createRecipe(28, 7.99, "Rinderrouladen klassisch", p15, p12, p13, p2);
		createRecipe(29, 2.99, "Italienischer Nudelsalat mit Rucola und getrockneten Tomaten", p1, p22, p14, p12);
		createRecipe(30, 3.49, "Zucchinifächer mit Feta", p1, p12, p16);
		createRecipe(31, 3.99, "Schwäbischer Zwiebelkuchen", p11, p12);

		createProducer(1, "Hans-Dampf", p1, p2, p3);
		createProducer(2, "Hans-Peter", p4, p5, p6);
		createProducer(3, "Hans-Johann", p1, p4, p5, p6);
		
//		ArrayList<Customer> customer = createCustomer();
//		ArrayList<RecordItem> items = createRecordItems();
//	
//		createRecords(customer, items);
	}
	
	private ArrayList<RecordItem> createRecordItems(){
		ArrayList<RecordItem> items = new ArrayList<RecordItem>();
		
		for(Product p:em.createNamedQuery("AllProducts", Product.class).getResultList()){
			RecordItem item = new RecordItem();
			item.setProductBase(p);
			item.setQuantity((int)Math.random()*10);
			em.persist(item);
			em.flush();
			items.add(item);
		}
		return items;
		
	}
	private ArrayList<Customer> createCustomer(){
		
		ArrayList<Customer> temp = new ArrayList<Customer>();
		
		for (int i = 0; i < 10; i++) {
		Customer customer = new Customer();
		customer.seteMail("test@test.com");
		customer.setFirstName("Generated");
		customer.setLastName("Generated");
		customer.setPassWord("test"+i);
		em.persist(customer);
		em.flush();
		temp.add(customer);
		}
		return temp;
	}
	
	private void createRecords(ArrayList<Customer> customer, ArrayList<RecordItem> items){
		
		for (int i = 0; i < 10; i++) {
			Record record = new Record();
			record.setCustomer(customer.get((int)Math.random()*10));
			
			ArrayList<RecordItem> temp = new ArrayList<RecordItem>();
			for (int j = 0; j < (int)Math.random()*21; j++) {
				temp.add(items.get((int)Math.random()*21));
				items.get((int)Math.random()*21).setRecord(record);
				em.merge(items.get((int)Math.random()*21));
				em.flush();
			}
			record.setSetRecordItems(temp);
			
			em.persist(record);
			em.flush();
		}
	}


	private Producer createProducer(long id, String sName, Product ... products) {
		Producer r = new Producer(id, sName, products);
		r.setDescription(getDescriptionText("producer", r.getId(), "description.html"));
		r.setShortDescription(getDescriptionText("producer", r.getId(), "short-description.html"));
		r.setPicture(getPicture("producer", r.getId()));
		em.persist(r);
		em.flush();

		for(Product p:products){
			p.getProducer().add(r);
			em.persist(p);
			em.flush();
		}

		return r;
	}

	private Recipe createRecipe(long id, Double dPrice, String sName, Product ... products) {
		Recipe r = new Recipe(id, sName, dPrice, products);
		r.setProdDescription(getDescriptionText("recipes", r.getProdId(), "description.html"));
		r.setPicture(getPicture("recipes", r.getProdId()));
		em.persist(r);
		em.flush();

		for(Product p:products){
			p.getRecipe().add(r);
			em.persist(p);
			em.flush();
		}

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
