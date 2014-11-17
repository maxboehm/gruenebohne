package com.gruenebohne.beans.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import com.gruenebohne.beans.session.BeanBasket;
import com.gruenebohne.beans.session.BeanSession;
import com.gruenebohne.model.Address;
import com.gruenebohne.model.AddressTyp;
import com.gruenebohne.model.Customer;

@ManagedBean(name="orderDetails")
@RequestScoped
public class CreateOrder{
	
	
	@ManagedProperty(value="#{usersession}")
	private BeanSession session;
	
	@ManagedProperty(value="#{basket}")
	private BeanBasket basket;
	
	private String firstname;
	private String lastname;
	private String firma;
	private String streetAndNumber;
	private String postalCode;
	private String city;
	private String email;
	private String phonenNumber;
	private String comment;
	
	
	
	public BeanSession getSession() {
		return session;
	}
	public void setSession(BeanSession session) {
		this.session = session;
	}
	public String getFirstname() {
		return session.getCustomer().getFirstName();
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return session.getCustomer().getLastName();
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirma() {
		ArrayList<Address> addresses = new ArrayList<Address>(session.getCustomer().getAddress());
		if(!addresses.isEmpty()){
			return addresses.get(0).getFirma();
		}
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getStreetAndNumber() {
		ArrayList<Address> addresses = new ArrayList<Address>(session.getCustomer().getAddress());
		if(!addresses.isEmpty()){
			return addresses.get(0).getStreetAndNumner();
		}
		return streetAndNumber;
	}
	public void setStreetAndNumber(String streetAndNumber) {
		this.streetAndNumber = streetAndNumber;
	}
	public String getPostalCode() {
		ArrayList<Address> addresses = new ArrayList<Address>(session.getCustomer().getAddress());
		if(!addresses.isEmpty() && addresses.get(0).getPostalCode()!=0){
			return String.valueOf(addresses.get(0).getPostalCode());
		}
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		ArrayList<Address> addresses = new ArrayList<Address>(session.getCustomer().getAddress());
		if(!addresses.isEmpty()){
			return addresses.get(0).getCity();
		}
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmail() {
		return session.getCustomer().geteMail();
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenNumber() {
		ArrayList<Address> addresses = new ArrayList<Address>(session.getCustomer().getAddress());
		if(!addresses.isEmpty()){
			return addresses.get(0).getPhoneNumber();
		}
		return phonenNumber;
	}
	public void setPhonenNumber(String phonenNumber) {
		this.phonenNumber = phonenNumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public BeanBasket getBasket() {
		return basket;
	}
	public void setBasket(BeanBasket basket) {
		this.basket = basket;
	}
	public void createOrder(ActionEvent event) throws Exception{
		
		Customer oldCustomer = session.getCustomer();
		Customer updatedCustomer = session.getCustomer();
		
		Address address = new Address();
		AddressTyp addressTyp = new AddressTyp();
		addressTyp.setAlias("Privat");
		
		address.setAddressTyp(addressTyp);
		address.setCity(getCity());
		address.setFirma(getFirma());
		address.setPhoneNumber(getPhonenNumber());
		address.setPostalCode(Double.parseDouble(getPostalCode()));
		address.setStreetAndNumner(getStreetAndNumber());
		
		updatedCustomer.seteMail(getEmail());
		updatedCustomer.setFirstName(getFirstname());
		updatedCustomer.setLastName(getLastname());
		
		basket.createOrder(updatedCustomer, oldCustomer,getComment(), address);
	}
	

}
