package com.gruenebohne.beans.session;

import java.text.DecimalFormat;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.gruenebohne.EJB.BasketEJB;
import com.gruenebohne.EJB.CustomerEJB;
import com.gruenebohne.EJB.ProductBaseEJB;
import com.gruenebohne.model.Address;
import com.gruenebohne.model.Customer;
import com.gruenebohne.model.Product;
import com.gruenebohne.model.ProductBase;
import com.gruenebohne.model.Record;
import com.gruenebohne.model.RecordItem;

@ManagedBean(name = "basket")
@SessionScoped
public class BeanBasket {

	private Record currentBasket = null;

	private int basketSize;
	private String totalPrice;

	@EJB private BasketEJB ejbBasket;

	@EJB private ProductBaseEJB ejbProductBase;
	
	@EJB private CustomerEJB ejbCustomer;

	@ManagedProperty(value="#{usersession}")
	private BeanSession session;
		
	@PostConstruct
	private void init() {
		currentBasket = ejbBasket.getNewBasket();
	}

	//	public Record getBasket() {
	//		return basket;
	//	}

	public void resetBasket(){
		System.out.println("new basket");
		currentBasket = ejbBasket.getNewBasket();
	}
	
	public void createOrder(Customer updatedCustomer, Customer oldCustomer ,String comment, Address address) throws Exception{
		ejbCustomer.updateCustomer(updatedCustomer, address, oldCustomer.geteMail(), oldCustomer.getPassWord());
		this.currentBasket.setFinalized(true);
		this.currentBasket.setComment(comment);
		ejbBasket.createOrder(updatedCustomer, this.currentBasket, address);
		
	}

	public void performAdd(ActionEvent event) {
		String prodId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("prodId");
		String quantity = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("quantity");
		ProductBase product = ejbProductBase.getProduct(Integer.parseInt(prodId));

		ejbBasket.addProductToBasket(this.currentBasket, product, Integer.parseInt(quantity));

		currentBasket = ejbBasket.refreshBasket(this.currentBasket);
		toString();
	}

	public void performDelete(ActionEvent event){
		String prodId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("prodId");
		ProductBase product = ejbProductBase.getProduct(Integer.parseInt(prodId));

		ejbBasket.removeProductFromBasket(this.currentBasket, product);

		currentBasket = ejbBasket.refreshBasket(this.currentBasket);
		toString();
	}
	
	public void setQuantity(ActionEvent event){
		String productId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
		String quantity = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productQuantity");
		
		if(Integer.parseInt(quantity)>0){
			ejbBasket.editQuantity(this.currentBasket, Integer.parseInt(productId), Integer.parseInt(quantity));
		}
		
		currentBasket = ejbBasket.refreshBasket(this.currentBasket);
		toString();
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
	
	@Override
	public String toString() {
		System.out.println("AKTUELLER WARENKORB BEGINN");
		for (RecordItem item : this.currentBasket.getSetRecordItems()) {
			System.out.println(item.getProductBase().getProdName() + " "
					+ item.getQuantity());
		}
		System.out.println("AKTUELLER WARENKORB ENDE");
		return null;
	}

	public BeanSession getSession() {
		return session;
	}

	public void setSession(BeanSession session) {
		this.session = session;
	}
	
	
	
}
