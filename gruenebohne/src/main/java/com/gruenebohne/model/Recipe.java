package com.gruenebohne.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_Recipe")
public class Recipe {

	@Id
	@GeneratedValue
	@Column(name="RECIPE_ID")
	private int id;

	@Column(nullable = false)
	@Lob
	private byte[] picture;

	@OneToMany(mappedBy="recipe")
	private Collection<Product> products;

	@Column private String name;
	@Column private String description;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}



}
