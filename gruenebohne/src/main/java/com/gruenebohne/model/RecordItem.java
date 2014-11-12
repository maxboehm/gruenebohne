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
	@Column
	private int id;

	@ManyToOne
	@JoinColumn
	private ProductBase product;

	@ManyToOne
	@JoinColumn
	private Record record;

	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProductBase getProduct() {
		return product;
	}

	public void setProduct(ProductBase product) {
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
