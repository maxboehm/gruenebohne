package com.gruenebohne.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean(name="product")
@SessionScoped
public class Product {

	private int productAmount=1;
	private double subTotal=0;
	
	private com.gruenebohne.model.Product currentProduct;
	
	public void sub(AjaxBehaviorEvent event){
		if(productAmount>0){
			productAmount--;
			subTotal = currentProduct.getPrice()*productAmount;
		}
	}
	
	public void add(AjaxBehaviorEvent event){
		productAmount++;
		subTotal = currentProduct.getPrice()*productAmount;
	}
	
	public void calculateSubTotal(){
		subTotal = currentProduct.getPrice()*productAmount;
	}
	
	public int getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}
	
	public com.gruenebohne.model.Product getCurrentProduct() {
		return currentProduct;
	}
	public void setCurrentProduct(com.gruenebohne.model.Product product) {
		this.currentProduct = product;
		productAmount=1;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	
	
	
}
