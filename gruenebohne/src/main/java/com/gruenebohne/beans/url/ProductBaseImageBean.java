package com.gruenebohne.beans.url;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.gruenebohne.EJB.ProductBaseEJB;

@ManagedBean(name = "image")
@RequestScoped
public class ProductBaseImageBean extends ImageBean{

	@EJB
	private ProductBaseEJB productbasejb;

	@Override
	protected byte[] getPicture(int nID) {
		return productbasejb.getProduct(getProdID()).getPicture();
	}
}