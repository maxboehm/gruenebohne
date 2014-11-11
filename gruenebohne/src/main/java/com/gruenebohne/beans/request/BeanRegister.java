package com.gruenebohne.beans.request;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import com.gruenebohne.EJB.CustomerEJB;
import com.gruenebohne.beans.session.BeanSession;
import com.gruenebohne.model.Customer;

/**
 * Bean encapsulating all operations for a person.
 */
@ManagedBean(name="register")
@RequestScoped
public class BeanRegister {

	private String email;
	private String password;
	private String firstname;
	private String lastname;

	@ManagedProperty(value = "#{usersession}")
	private BeanSession sessionBean;

	@EJB private CustomerEJB customerejb;

	public void createNewCustomer(ActionEvent event) {
		System.out.println("CREATE NEW CUSTOMER");
		Customer customer = new Customer();
		customer.seteMail(getEmail());
		customer.setPassWord(getPassword());
		customer.setFirstName(getFirstname());
		customer.setLastName(getLastname());
		customerejb.registerCustomer(customer);

		sessionBean.performLogin(getEmail(), getPassword());
	}



	public BeanSession getSessionBean() {
		return sessionBean;
	}



	public void setSessionBean(BeanSession sessionBean) {
		this.sessionBean = sessionBean;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}




}
