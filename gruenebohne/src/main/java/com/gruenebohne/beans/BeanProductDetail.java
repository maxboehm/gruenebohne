package com.gruenebohne.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.model.Product;

@ManagedBean(name="productdetail")
@RequestScoped
public class BeanProductDetail {

	@EJB
	private ProductEJB productejb;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private Product product;

	public void loadProduct(){
		product = productejb.getProduct(getId());
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
