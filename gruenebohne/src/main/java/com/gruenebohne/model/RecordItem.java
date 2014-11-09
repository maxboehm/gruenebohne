package com.gruenebohne.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_RECORDITEM")
public class RecordItem {


	@Id
	@GeneratedValue
	@Column(name="ITEM_ID")
	private int id;

	@ManyToOne
	@JoinColumn(name="PROD_ID")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name="RECORD_ID")
	private Record record;

	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
	


}
