package com.gruenebohne.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_RECORD")
@NamedQueries({
	@NamedQuery(name="getRecord", query="SELECT r from Record r WHERE r.id=:basketId")
})
@SequenceGenerator(name = "sequence", initialValue = 0, allocationSize = 1000)
public class Record {

	@Id
	@GeneratedValue(generator="sequence")
	@Column(name="RECORD_ID", nullable=false)
	private long id;

	@ManyToOne
	private Customer customer;

	@Column(name="TOTALPRICE", precision=2)
	private double totalPrice;

	@OneToMany(mappedBy="record",cascade=CascadeType.ALL)
	private Collection<RecordItem> setRecordItems = new ArrayList<RecordItem>();

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


}
