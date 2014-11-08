package com.gruenebohne.beans.session;

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
@ManagedBean(name="usersession")
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

	public void performLogin(String sEmail, String sPassword) {
		customer = customerejb.loginCustomer(sEmail, sPassword);
	}

	public void logout(ActionEvent event){
		customer = null;
	}


}
