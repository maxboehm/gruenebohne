package com.gruenebohne.beans.request;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.gruenebohne.EJB.ProducerEJB;
import com.gruenebohne.EJB.RecipeEJB;
import com.gruenebohne.model.Producer;
import com.gruenebohne.model.Recipe;

@ManagedBean(name="fake")
@RequestScoped
public class BeanFake {

	@EJB
	private ProducerEJB producerEJB;

	@EJB
	private RecipeEJB recipeEJB;

	public List<Producer> getProducer() {
		return producerEJB.getAllProducer();
	}
	public List<Recipe> getRecipes() {
		return recipeEJB.getAllRecipes();
	}


}
