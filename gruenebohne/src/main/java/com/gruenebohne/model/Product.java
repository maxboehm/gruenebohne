package com.gruenebohne.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@DiscriminatorValue("P")
@NamedQueries({
	@NamedQuery(name="AllProducts",query="select p from Product p"),
	@NamedQuery(name="GetProduct", query="select p from Product p where p.prodId= :prodId")
})
@SequenceGenerator(name = "sequence", initialValue = 0, allocationSize = 1000)
public class Product extends ProductBase{

	@ManyToOne private ProductCategory category;
	@ManyToOne private Recipe recipe;
	@Column(name = "LAST_UPDATED_TIME") private Date updatedTime;

	public Product(){}

	public Product(long id, String prodName, String prodDescription, double price, String pictureURL,ProductCategory cat){
		setProdId(id);
		setProdName(prodName);
		setProdDescription(prodDescription);
		setPrice(price);
		setPictureByPath(pictureURL);
		this.category = cat;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory category) {
		this.category = category;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}


}