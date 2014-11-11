package com.gruenebohne.model;

import java.util.Collection;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_Recipe")
@DiscriminatorValue("R")
@NamedQueries({
	@NamedQuery(name="AllRecipes",query="select r from Recipe r"),
	@NamedQuery(name="GetRecipe", query="select r from Recipe r where r.prodId= :prodId")
})
public class Recipe extends ProductBase{

	@OneToMany(mappedBy="recipe")
	private Collection<Product> products;

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}


}
