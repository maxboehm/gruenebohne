package com.gruenebohne.beans.session;

import java.text.DecimalFormat;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.gruenebohne.EJB.BasketEJB;
import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.model.Product;
import com.gruenebohne.model.Record;

@ManagedBean(name = "basket")
@SessionScoped
public class BeanBasket {

	private Record currentBasket = null;

	private int basketSize;
	private String totalPrice;

	@EJB private BasketEJB ejbBasket;

	@EJB private ProductEJB ejbProduct;

	@PostConstruct
	private void init() {
		currentBasket = ejbBasket.getNewBasket();
	}

	//	public Record getBasket() {
	//		return basket;
	//	}


	public void performAdd(ActionEvent event) {
		String prodId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("prodId");
		String quantity = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("quantity");
		Product product = ejbProduct.getProduct(Integer.parseInt(prodId));

		ejbBasket.addProductToBasket(this.currentBasket, product, Integer.parseInt(quantity));

		currentBasket = ejbBasket.refreshBasket(this.currentBasket);
	}

	public void performDelete(ActionEvent event){
		String prodId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("prodId");
		Product product = ejbProduct.getProduct(Integer.parseInt(prodId));

		ejbBasket.removeProductFromBasket(this.currentBasket, product);

		currentBasket = ejbBasket.refreshBasket(this.currentBasket);
	}



	public void setBasket(Record basket) {
		this.currentBasket = basket;
	}

	public String getTotalPrice() throws Exception{
		double dtotalPrice= ejbBasket.getTotalPrice(this.currentBasket);
		totalPrice = new DecimalFormat("0.00").format(dtotalPrice).replace(",", ".");
		return totalPrice;
	}
	public int getBasketSize(){
		basketSize = ejbBasket.getBasketSize(this.currentBasket);
		return basketSize;
	}

	public Record getCurrentBasket() {
		return currentBasket;
	}

	public void setCurrentBasket(Record currentBasket) {
		this.currentBasket = currentBasket;
	}

}
