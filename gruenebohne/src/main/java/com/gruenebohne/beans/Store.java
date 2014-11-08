package com.gruenebohne.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.model.Product;


@ManagedBean(name="store")
@SessionScoped
public class Store {

	@EJB
	private ProductEJB productejb;

	private List<Product> products;

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





}
