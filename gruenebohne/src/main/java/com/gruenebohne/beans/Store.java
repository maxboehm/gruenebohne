package com.gruenebohne.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.model.Product;


@ManagedBean(name="store")
@SessionScoped
public class Store {

	@EJB
	private ProductEJB productejb;
	
	private List<Product> products;
	
	@ManagedProperty(value="#{product}")
	private com.gruenebohne.beans.Product currentProduct;
	
	@PostConstruct
	public void init(){
		
		products = productejb.getAllProductsWithCategory();
		for(Product p:products){
			System.out.println(p.getPictureURL());
		}
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProduct(List<Product> products) {
		this.products = products;
	}
	
	public void setCurrentProduct(){
		
		String prodId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("selectedProduct");
		if(prodId!=null){
			Product product = productejb.getProduct(Integer.parseInt(prodId));
			currentProduct.setCurrentProduct(product);
		}
	}

	public com.gruenebohne.beans.Product getcurrentProduct() {
		return currentProduct;
	}

	public void setcurrentProduct(com.gruenebohne.beans.Product product) {
		this.currentProduct = product;
	}
	
	
	
	
}
