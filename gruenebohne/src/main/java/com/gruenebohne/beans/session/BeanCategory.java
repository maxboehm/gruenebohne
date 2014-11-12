package com.gruenebohne.beans.session;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.EJB.CategoryEJB;
import com.gruenebohne.model.ProductCategory;


@ManagedBean(name="category")
@SessionScoped
public class BeanCategory {

	@EJB
	private CategoryEJB catEJB;

	@PersistenceContext
	private EntityManager em;

	private List<ProductCategory> cat;

	public List<ProductCategory> getCat() {
		if(cat==null)cat = catEJB.getAllCat();

		return cat;
	}

	public void setCat(List<ProductCategory> cat) {
		this.cat = cat;
	}

}