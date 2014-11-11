package com.gruenebohne.beans.request;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.gruenebohne.EJB.RecipeEJB;
import com.gruenebohne.model.Recipe;

@ManagedBean(name="recipedetail")
@RequestScoped
public class BeanRecipeDetail {

	@EJB
	private RecipeEJB recipeEJB;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private Recipe recipe;

	public void loadRecipe(){
		recipe = recipeEJB.getRecipe(getId());
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
