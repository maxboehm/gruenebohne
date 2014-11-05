package com.gruenebohne.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.gruenebohne.EJB.CustomerEJB;
import com.gruenebohne.EJB.ProductEJB;
import com.gruenebohne.model.Customer;

/**
 * Bean encapsulating all operations for a person.
 */
@ManagedBean(name = "login")
@SessionScoped
public class Login {

	private String password;
	private String eMail;
	private boolean isLoggedin=false;
	private Customer currentCustomer;

	@EJB
	private CustomerEJB customerejb;
	
	@EJB
	private ProductEJB prodcutejb;

	public boolean getisLoggedin() {
		return isLoggedin;
	}

	public void setisLoggedin(boolean isLoggedin) {
		this.isLoggedin = isLoggedin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public void performLogin(ActionEvent event) {
		currentCustomer = customerejb.loginCustomer(this.eMail, this.password);
		if(currentCustomer!=null){
			this.isLoggedin=true;
		}
	}

	public void createNewCustomer(ActionEvent event) {
		Customer newCustomer = new Customer();
		newCustomer.seteMail(this.eMail);
		newCustomer.setPassWord(this.password);
		newCustomer.setFirstName("TESTFIRSTNAME");
		newCustomer.setLastName("TESTLASTNAME");
		customerejb.registerCustomer(newCustomer);
		this.isLoggedin=true;
	}
}
