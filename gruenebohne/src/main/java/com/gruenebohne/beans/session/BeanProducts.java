package com.gruenebohne.beans.session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.model.Product;


@ManagedBean(name="products")
@SessionScoped
public class BeanProducts {

	@EJB
	private ProductEJB productejb;

	private List<Product> products;

	@PostConstruct
	public void init(){
		products = productejb.getAllProductsWithCategory();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProduct(List<Product> products) {
		this.products = products;
	}

	/**
	 * Get random products
	 * @return
	 */
	public List<Product> get3RandomProducts(){
		List<Product> listBest = new ArrayList<Product>(getProducts());
		List<Product> listNew = new ArrayList<Product>();
		Collections.shuffle(listBest);
		for (int i = 0; i < listBest.size(); i++)
			if(i<3)
				listNew.add(listBest.get(i));
		return listNew;
	}

}