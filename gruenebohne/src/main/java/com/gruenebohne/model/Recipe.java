package com.gruenebohne.model;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("R")
@NamedQueries({
	@NamedQuery(name="AllRecipes",query="select r from Recipe r"),
	@NamedQuery(name="GetRecipe", query="select r from Recipe r where r.prodId= :prodId")
})
public class Recipe extends ProductBase{

	public Recipe(){}

	public Recipe(long id, String prodName, double price){
		super(id, prodName, price);
	}

	@OneToMany(mappedBy="recipe")
	private Collection<Product> products;

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


}
