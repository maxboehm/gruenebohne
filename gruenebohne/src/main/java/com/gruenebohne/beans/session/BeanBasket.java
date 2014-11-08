package com.gruenebohne.beans.session;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.gruenebohne.EJB.BasketEJB;
import com.gruenebohne.model.Record;

@ManagedBean(name = "basket")
@SessionScoped
public class BeanBasket {


	private Record basket = null;

	@EJB private BasketEJB ejbBasket;

	@PostConstruct
	private void init() {
		basket = ejbBasket.getNewBasket();
	}

	//	public Record getBasket() {
	//		return basket;
	//	}

	public void performAdd() {
		HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
		System.out.println(request.getParameter("prodId"));
		System.out.println(request.getParameter("quantity"));
	}

	public void setBasket(Record basket) {
		this.basket = basket;
	}

	public double getTotalPrice(){
		return 0d;
	}
	public int getSize(){
		return 0;
	}
	public Object getBasket(){
		return null;
	}


}
