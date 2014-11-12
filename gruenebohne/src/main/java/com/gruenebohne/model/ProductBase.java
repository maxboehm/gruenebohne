package com.gruenebohne.model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ProductBase")
@Inheritance
@DiscriminatorColumn(name="PRD_TYPE")
@NamedQueries({
	@NamedQuery(name="GetProductBase", query="select p from ProductBase p where p.prodId= :prodId")
})
public abstract class ProductBase {

	protected abstract String getDirName();

	public ProductBase(){}
	public ProductBase(long id, String prodName, double price){
		setProdId(id);
		setProdName(prodName);
		setPrice(price);

		// Get the description and image statically
		retrieveDataFromFiles();
	}



	@Id
	@Column(nullable = false)
	@GeneratedValue
	private long prodId;

	@Column(nullable = false)
	private String prodName;

	@Column(length=10000)
	private String prodDescription;

	@Column
	private double price;

	@Column
	@Lob
	private byte[] picture;

	public byte[] getPicture() {
		return picture;
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




	@SuppressWarnings("resource")
	public void retrieveDataFromFiles(){
		String sPath = "resources/database/"+getDirName()+"/"+getProdId()+"/";
		try {
			java.util.Scanner scanner = null;
			try {
				InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(sPath+"description.html");
				scanner = new java.util.Scanner(is).useDelimiter("\\A");
				String sDesc = scanner.hasNext() ? scanner.next() : "";
				this.setProdDescription(sDesc);
			} finally {
				if(scanner!=null) scanner.close();
			}



			InputStream is = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(sPath+"img.jpg");
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

			setPicture(buffer.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}