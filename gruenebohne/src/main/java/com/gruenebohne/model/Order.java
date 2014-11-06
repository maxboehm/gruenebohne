package com.gruenebohne.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="T_ORDER")
@SequenceGenerator(name = "sequence", initialValue = 0, allocationSize = 1000)
public class Order {

	@Id
	@GeneratedValue(generator="sequence")
	@Column(name="ORDER_ID", nullable=false)
	private long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="CUSTOMER_ID",referencedColumnName="CUSTOMER_ID")
	private Customer customer;
	
	@Column(name="TOTALPRICE", precision=2)
	private double totalPrice;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="ORDER_DETAIL",
			joinColumns=
	            @JoinColumn(name="ORDER_ID", referencedColumnName="ORDER_ID"),
	        inverseJoinColumns=
	            @JoinColumn(name="PRODUCT_ID", referencedColumnName="PRODUCT_ID")
	)
	private List<Product> productList;

	@OneToOne
	private Invoice invoice;
	
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

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	
}
