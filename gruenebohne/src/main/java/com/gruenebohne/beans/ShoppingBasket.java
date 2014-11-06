package com.gruenebohne.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import com.gruenebohne.model.Product;

@ManagedBean(name = "currentBasket")
@SessionScoped
public class ShoppingBasket {
	
	@ManagedProperty(value="#{product}")
	private com.gruenebohne.beans.Product currentProduct;
	
	private double totalPrice=0;
	
	private List<com.gruenebohne.beans.Product> basket = new ArrayList<com.gruenebohne.beans.Product>();
	
	public void addToBasket(ActionEvent event){
		basket.add(currentProduct);	
		refreshPrice();
	}

	public com.gruenebohne.beans.Product getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(com.gruenebohne.beans.Product currentProduct) {
		this.currentProduct = currentProduct;
	}
	
	public void add(AjaxBehaviorEvent event){
		String productName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("product");
		
		for(com.gruenebohne.beans.Product product:basket){
			if(product.getCurrentProduct().getProdName().equals(productName)){
				product.setProductAmount(product.getProductAmount()+1);
			}
		}
	}
	
	public void sub(AjaxBehaviorEvent event){
		String productName = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("product");
		
		for(com.gruenebohne.beans.Product product:basket){
			if(product.getCurrentProduct().getProdName().equals(productName)){
				product.setProductAmount(product.getProductAmount()-1);
			}
		}
	}
	
	public void refreshPrice(){
		for(com.gruenebohne.beans.Product product : basket){
			totalPrice =+ product.getCurrentProduct().getPrice()*product.getProductAmount();
			product.calculateSubTotal();
		}
	}
	
	
	public void proccedCheckout(ActionEvent event){
		System.out.println("CHECKOUT");
	}

	public List<com.gruenebohne.beans.Product> getBasket() {
		return basket;
	}

	public void setBasket(List<com.gruenebohne.beans.Product> basket) {
		this.basket = basket;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public int getSize(){
		return basket.size();
	}
	
	 
	
}
