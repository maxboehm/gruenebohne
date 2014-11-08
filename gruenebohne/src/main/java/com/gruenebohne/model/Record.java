package com.gruenebohne.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_RECORD")
@SequenceGenerator(name = "sequence", initialValue = 0, allocationSize = 1000)
public class Record {

	@Id
	@GeneratedValue(generator="sequence")
	@Column(name="RECORD_ID", nullable=false)
	private long id;

	@ManyToOne(optional=false)
	@JoinColumn(name="CUSTOMER_ID",referencedColumnName="CUSTOMER_ID")
	private Customer customer;

	@Column(name="TOTALPRICE", precision=2)
	private double totalPrice;

	@OneToMany
	private Set<RecordItem> setRecordItems;

	public Set<RecordItem> getSetRecordItems() {
		return setRecordItems;
	}

	public void setSetRecordItems(Set<RecordItem> setRecordItems) {
		this.setRecordItems = setRecordItems;
	}

	public void addRecordItem(RecordItem item){
		setRecordItems.add(item);
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


}
