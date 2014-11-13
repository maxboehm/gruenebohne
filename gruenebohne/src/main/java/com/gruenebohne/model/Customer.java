package com.gruenebohne.model;

import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.eclipse.persistence.annotations.ClassExtractor;

/**
 * Class holding information on a person.
 */
@Entity
@Table(name = "T_CUSTOMERS")
@NamedQueries({
	@NamedQuery(name = "AllCustomer", query = "select c from Customer c"),
	@NamedQuery(name = "GetCustomer", query="select c from Customer c where c.eMail = :custEmail and c.passWord = :custPassword")
})
public class Customer {

	// #################################################################
	// members
	// #################################################################

	@Id
	@Column(name="CUSTOMER_ID", nullable=false)
	@GeneratedValue
	private long id;

	@Column private String firstName;
	@Column private String lastName;
	@Column private String eMail;
	@Column private String passWord;
	

	@Version
	@Column(name="LASTUPDATED")
	private Timestamp updateTime;

	@OneToMany(mappedBy="customer", targetEntity=Record.class,fetch=FetchType.EAGER)
	private Collection<Record> orders;

	@OneToMany(mappedBy="customer", targetEntity=Address.class, fetch=FetchType.EAGER)
	private Collection<Address> address;


	// #################################################################
	// getter-setter
	// #################################################################
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Collection<Record> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Record> orders) {
		this.orders = orders;
	}

	public Collection<Address> getAddress() {
		return address;
	}

	public void setAddress(Collection<Address> address) {
		this.address = address;
	}
	
}