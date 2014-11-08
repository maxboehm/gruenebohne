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
@ManagedBean(name="session")
@SessionScoped
public class BeanSession {

	private Customer customer;

	@EJB
	private CustomerEJB customerejb;

	@EJB
	private ProductEJB prodcutejb;

	public boolean getisLoggedin() {
		return customer!=null;
	}

	public void performLogin(ActionEvent event) {
		//		customer = customerejb.loginCustomer(this.eMail, this.password);
	}

	public void createNewCustomer(ActionEvent event) {
		customer = new Customer();
		//		newCustomer.seteMail(this.eMail);
		//		newCustomer.setPassWord(this.password);
		customer.setFirstName("TESTFIRSTNAME");
		customer.setLastName("TESTLASTNAME");
		customerejb.registerCustomer(customer);
	}
}
