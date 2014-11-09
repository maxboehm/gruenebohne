package com.gruenebohne.beans.request;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.beans.session.BeanBasket;
import com.gruenebohne.model.Product;

@ManagedBean(name = "basketmodify")
@SessionScoped
public class BeanBasketModify {


	@ManagedProperty(value = "#{basket}")
	private BeanBasket basket;

	@EJB
	private ProductEJB ejbProduct;

	public void performAdd() {
		HttpServletRequest request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());
		System.out.println(request.getParameter("prodId"));
		System.out.println(request.getParameter("quantity"));

		Product product = ejbProduct.getProduct(Integer.parseInt(request.getParameter("prodId")));
		System.out.println("test");
	}

	public BeanBasket getBasket() {
		return basket;
	}

	public void setBasket(BeanBasket basket) {
		this.basket = basket;
	}




}
