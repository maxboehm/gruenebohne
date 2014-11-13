package com.gruenebohne.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="T_RECORD")
@NamedQueries({
	@NamedQuery(name="getRecord", query="SELECT r from Record r WHERE r.id=:basketId"),
	@NamedQuery(name="getOrders", query="SELECT o from Record o WHERE o.isFinalized=:isFinalized")
})
public class Record {

	@Id
	@GeneratedValue
	@Column
	private long id;

	@ManyToOne
	@JoinColumn(nullable=true)
	private Customer customer;

	@Column(precision=2)
	private double totalPrice;

	@OneToMany(mappedBy="record",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Collection<RecordItem> setRecordItems = new ArrayList<RecordItem>();

	@Column
	private String comment;
	
	@Column
	private boolean isFinalized;

	public Collection<RecordItem> getSetRecordItems() {
		return setRecordItems;
	}

	public void setSetRecordItems(Collection<RecordItem> setRecordItems) {
		this.setRecordItems = setRecordItems;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isFinalized() {
		return isFinalized;
	}

	public void setFinalized(boolean isFinalized) {
		this.isFinalized = isFinalized;
	}
	
	

}
