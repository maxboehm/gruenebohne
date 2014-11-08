package com.gruenebohne.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.model.Product;

@ManagedBean(name="productdetail")
@RequestScoped
public class BeanProductDetail {

	@EJB
	private ProductEJB productejb;

	private Product product;

	public void loadProduct(){
		String id = ((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest())).getParameter("id");
		System.out.println(id);
		product = productejb.getProduct(Integer.valueOf(id));
	}

}
