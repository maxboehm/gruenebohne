package com.gruenebohne.beans.request;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.EJB.RecipeEJB;
import com.gruenebohne.model.Product;

@ManagedBean(name="productdetail")
@RequestScoped
public class BeanProductDetail {

	@EJB
	private ProductEJB productejb;

	@EJB
	private RecipeEJB recipeejb;

	private int id;
	private Product product;

	/**
	 * load the product via the id
	 */
	public void loadProduct(){
		product = productejb.getProduct(getId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
