package com.gruenebohne.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_PRODUCTCATEGORY")
@NamedQueries({
	@NamedQuery(name="AllCategories",query="select c from ProductCategory c")
})
@SequenceGenerator(name = "sequence", initialValue = 0, allocationSize = 1000)
public class ProductCategory {
	
	@Id
	@Column(name="CATEGORY_ID")
	private long id;
	
	@Column(name="CATEGORY")
	private String category;
	
	@OneToMany(mappedBy="category")
	private Collection<Product> product;
	
	public ProductCategory(){
		
	}
	
	public ProductCategory(long id, String category){
		this.id = id;
		this.category = category;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Collection<Product> getProduct() {
		return product;
	}

	public void setProduct(Collection<Product> product) {
		this.product = product;
	}
	
	
}
