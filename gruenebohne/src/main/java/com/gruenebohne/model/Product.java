package com.gruenebohne.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "T_PRODUCT")
@NamedQueries({
	@NamedQuery(name="AllProducts",query="select p from Product p"),
	@NamedQuery(name="GetProduct", query="select p from Product p where p.prodId= :prodId")
})
@SequenceGenerator(name = "sequence", initialValue = 0, allocationSize = 1000)
public class Product {
	
	@Id
	@Column(name = "PROD_ID", nullable = false)
	private long prodId;

	@Column(name = "PRODUCT_NAME", nullable = false)
	private String prodName;

	@Column(name = "PRODUCT_DESC")
	private String prodDescription;

	@Column(name = "REGULAR_PRICE")
	private double price;
	
	@Column(name="PICTUREURL")
	private String pictureURL;
	
	@ManyToOne
	private ProductCategory category;
	
	@ManyToOne
	private Recipe recipe;
	
//	@JoinColumn(name="CATEGORY_ID")
//	private int category;

	@Column(name = "LAST_UPDATED_TIME")
	private Date updatedTime;

//	@ManyToMany(mappedBy = "productList", fetch = FetchType.EAGER)
//	private List<Order> orderList;

	
	public Product(){
		
	}
	
	public Product(long id, String prodName, String prodDescription, double price, String pictureURL,ProductCategory cat){
		this.prodId = id;
		this.prodName = prodName;
		this.prodDescription = prodDescription;
		this.price = price;
		this.pictureURL=pictureURL;
		this.category = cat;
	}
	
	public long getProdId() {
		return prodId;
	}

	public void setProdId(long prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdDescription() {
		return prodDescription;
	}

	public void setProdDescription(String prodDescription) {
		this.prodDescription = prodDescription;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	
}