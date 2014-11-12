package com.gruenebohne.model;

import java.sql.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("P")
@NamedQueries({
	@NamedQuery(name="AllProducts",query="select p from Product p"),
	@NamedQuery(name="GetProduct", query="select p from Product p where p.prodId= :prodId")
})
public class Product extends ProductBase{

	@ManyToOne private ProductCategory category;
	@ManyToOne private Recipe recipe;
	@Column private Date updatedTime;

	public Product(){}

	public Product(long id, String prodName, double price, ProductCategory cat){
		super(id, prodName, price);
		setCategory(cat);
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getSiegelCssClass(){
		String s= new Random().nextBoolean() ?	"js--cat-siegel-demeter "	: "" ;
		s += new Random().nextBoolean() ?		"js--cat-siegel-ecovin "	: "" ;
		s += new Random().nextBoolean() ?		"js--cat-siegel-eu " 		: "" ;
		s += new Random().nextBoolean() ?		"js--cat-siegel-deutsch " 	: "" ;
		return s.trim();
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

	@Override
	protected String getDirName() {
		return "products";
	}


}