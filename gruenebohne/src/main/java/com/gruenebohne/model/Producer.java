package com.gruenebohne.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Entity implementation class for Entity: Producer
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="AllProducer",query="select p from Producer p"),
	@NamedQuery(name="GetProducer", query="select p from Producer p where p.id= :id")

})
public class Producer {

	// #################################################################
	// members
	// #################################################################

	@Id
	@GeneratedValue
	private long id;

	@Column private String Name;
	@Column private String Description;
	@Column
	@Lob
	private byte[] picture;

	@ManyToMany
	@JoinTable(name = "PRD_PRDCR")
	private Collection<Product> products;

	// #################################################################
	// constructor
	// #################################################################
	public Producer(){}
	public Producer(long nID, String sName, Product[] products){
		setId(nID);
		setName(sName);
		setProducts(Arrays.asList(products));
	}

	// #################################################################
	// getter-setter
	// #################################################################

	public String getName() {
		return this.Name;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String Name) {
		this.Name = Name;
	}
	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}



}
