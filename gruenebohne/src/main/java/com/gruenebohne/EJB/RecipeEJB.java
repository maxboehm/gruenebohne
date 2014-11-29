package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Recipe;

@Stateless
@LocalBean
public class RecipeEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB StartupBean beanStartup;

	public List<Recipe> getAllRecipes() {
		beanStartup.startup();
		// return all recipes
		return em.createNamedQuery("AllRecipes", Recipe.class).getResultList();
	}

	public Recipe getRecipe(int prodId){
		// get recipe by id
		List <Recipe> recipe = em.createNamedQuery("GetRecipe", Recipe.class).setParameter("prodId", prodId).getResultList();

		// no recipe? return null
		if(recipe.isEmpty())
			return null;

		// return recipe
		return recipe.get(0);
	}


}
