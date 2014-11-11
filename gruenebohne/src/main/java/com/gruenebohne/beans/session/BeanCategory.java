package com.gruenebohne.beans.session;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.gruenebohne.EJB.CategoryEJB;
import com.gruenebohne.model.ProductCategory;


@ManagedBean(name="category")
@SessionScoped
public class BeanCategory {

	@EJB
	private CategoryEJB catEJB;

	private List<ProductCategory> cat;

	@PostConstruct
	public void init(){
		cat = catEJB.getAllCat();
	}

	public List<ProductCategory> getCat() {
		return cat;
	}

	public void setCat(List<ProductCategory> cat) {
		this.cat = cat;
	}

}