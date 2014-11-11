package com.gruenebohne.EJB;

import java.util.List;

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

	public List<Recipe> getAllRecipes() {
		return em.createNamedQuery("AllRecipes", Recipe.class).getResultList();
	}

	public Recipe getRecipe(int prodId){

		List <Recipe> recipe = em.createNamedQuery("GetRecipe", Recipe.class).setParameter("prodId", prodId).getResultList();

		if(recipe.isEmpty()){
			return null;
		}
		return recipe.get(0);
	}


}
