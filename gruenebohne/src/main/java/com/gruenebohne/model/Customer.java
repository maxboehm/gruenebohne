package com.gruenebohne.model;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Class holding information on a person.
 */
@Entity
@Table(name = "T_CUSTOMERS")
@NamedQueries({ 
	@NamedQuery(name = "AllCustomer", query = "select c from Customer c"),
	@NamedQuery(name = "GetCustomer", query="select c from Customer c where c.eMail = :custEmail and c.passWord = :custPassword")
	})
@SequenceGenerator(name = "sequence", initialValue = 0, allocationSize = 1000)
public class Customer {

	@GeneratedValue(generator = "sequence")
	@Id
	@Column(name="CUSTOMER_ID", nullable=false)
	private long id;

	@Column(name="FIRSTNAME")
	private String firstName;
	@Column(name="LASTNAME")
	private String lastName;
	@Column(name="EMAIL")
	private String eMail;
	@Column(name="PASSWORD")
	private String passWord;
	
	@Version
	@Column(name="LASTUPDATED")
	private Timestamp updateTime;
	
	@OneToMany(mappedBy="customer", targetEntity=Order.class,fetch=FetchType.EAGER)
	private Collection<Order> orders;
	
	@OneToMany(mappedBy="customer", targetEntity=Address.class, fetch=FetchType.EAGER)
	private Collection<Address> address;

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String newFirstName) {
		this.firstName = newFirstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String newLastName) {
		this.lastName = newLastName;
	}
}