package com.gruenebohne.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("R")
@NamedQueries({
	@NamedQuery(name="AllRecipes",query="select r from Recipe r"),
	@NamedQuery(name="GetRecipe", query="select r from Recipe r where r.prodId= :prodId")
})
public class Recipe extends ProductBase{

	public Recipe(){}

	public Recipe(long id, String prodName, double price, Product[] products){
		super(id, prodName, price);
		setProducts(Arrays.asList(products));
	}

	@ManyToMany
	@JoinTable(name = "RCP_PRD")
	private Collection<Product> products;
	
	@Column(length=10000)
	private String recipeInfo;

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}

	@Override
	protected String getDirName() {
		return "recipes";
	}

	public String getRecipeInfo() {
		return recipeInfo;
	}

	public void setRecipeInfo(String recipeInfo) {
		this.recipeInfo = recipeInfo;
	}
	


}
