package com.gruenebohne.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.eclipse.persistence.annotations.CascadeOnDelete;

@Entity
public class Address {

	// #################################################################
	// member
	// #################################################################

	@Id
	@GeneratedValue
	@Column
	private int id;

	@Column private String street;
	@Column private String city;
	@Column private String streetAndNumner;
	@Column private String firma;
	@Column private double postalCode;
	@Column private String phoneNumber;

	@ManyToOne(optional=false, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="addresstyp_id",referencedColumnName="addresstyp_id")
	private AddressTyp addressTyp;

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="CUSTOMER_ID",referencedColumnName="CUSTOMER_ID")
	private Customer customer;


	// #################################################################
	// getter-setter
	// #################################################################

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreetAndNumner() {
		return streetAndNumner;
	}

	public void setStreetAndNumner(String streetAndNumner) {
		this.streetAndNumner = streetAndNumner;
	}

	public String getFirma() {
		return firma;
	}

	public void setFirma(String firma) {
		this.firma = firma;
	}

	public double getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(double postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public AddressTyp getAddressTyp() {
		return addressTyp;
	}

	public void setAddressTyp(AddressTyp addressTyp) {
		this.addressTyp = addressTyp;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



}
