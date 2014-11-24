package com.gruenebohne.model;

import java.sql.Date;
import java.util.Collection;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@Column private Date updatedTime;

	@ManyToMany(mappedBy="products")
	@JoinTable(name = "RCP_PRD")
	private Collection<Recipe> recipe;

	@ManyToMany(mappedBy="products")
	@JoinTable(name = "PRD_PRDCR")
	private Collection<Producer> producer;

	public Product(){}

	public Product(long id, String prodName, double price, ProductCategory cat, String unit){
		super(id, prodName, price, unit);
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


	public Collection<Recipe> getRecipe() {
		System.out.println("Number of recipes: "+recipe.size());
		return recipe;
	}

	public void setRecipe(Collection<Recipe> recipe) {
		this.recipe = recipe;
	}



	public Collection<Producer> getProducer() {
		return producer;
	}

	public void setProducer(Collection<Producer> producer) {
		this.producer = producer;
	}

	@Override
	protected String getDirName() {
		return "products";
	}


}