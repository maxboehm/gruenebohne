package com.gruenebohne.model;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ProductBase")
@Inheritance
@DiscriminatorColumn(name="PRD_TYPE")
@SequenceGenerator(name = "sequence", initialValue = 0, allocationSize = 1000)
@NamedQueries({
	@NamedQuery(name="GetProductBase", query="select p from ProductBase p where p.prodId= :prodId")
})
public abstract class ProductBase {

	@Id
	@Column(name = "PROD_ID", nullable = false)
	@GeneratedValue
	private long prodId;

	@Column(name = "PRODUCT_NAME", nullable = false)
	private String prodName;

	@Column(name = "PRODUCT_DESC")
	private String prodDescription;

	@Column(name = "REGULAR_PRICE")
	private double price;

	@Column//(nullable = false)
	@Lob
	private byte[] picture;

	public byte[] getPicture() {
		return picture;
	}


	public void setPictureByPath (String ImageName)  {
		try {
			setPicture(Files.readAllBytes(Paths.get(ImageName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
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


}