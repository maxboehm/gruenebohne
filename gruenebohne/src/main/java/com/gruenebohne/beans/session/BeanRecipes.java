package com.gruenebohne.beans.session;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.gruenebohne.EJB.RecipeEJB;
import com.gruenebohne.model.Recipe;


@ManagedBean(name="recipes")
@SessionScoped
public class BeanRecipes {

	@EJB
	private RecipeEJB recipeejb;

	private List<Recipe> recipes;

	@PostConstruct
	public void init(){
		recipes = recipeejb.getAllRecipes();
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> products) {
		this.recipes = products;
	}

	public Recipe getOneRecipe(){
		return getRecipes().get(0);
	}

}