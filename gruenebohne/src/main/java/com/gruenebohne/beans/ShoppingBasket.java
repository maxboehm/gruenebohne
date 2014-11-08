package com.gruenebohne.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.gruenebohne.EJB.BasketEJB;
import com.gruenebohne.model.Record;

@ManagedBean(name = "currentBasket")
@SessionScoped
public class ShoppingBasket {


	private Record basket = null;

	@EJB private BasketEJB ejbBasket;

	@PostConstruct
	private void init() {
		basket = ejbBasket.getNewBasket();
	}

	//	@ManagedProperty(value="#{product}")
	//	private com.gruenebohne.beans.Product currentProduct;




}
