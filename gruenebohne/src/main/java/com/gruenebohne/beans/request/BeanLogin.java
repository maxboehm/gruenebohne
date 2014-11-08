package com.gruenebohne.beans.request;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import com.gruenebohne.beans.session.BeanSession;

/**
 * Bean encapsulating all operations for a person.
 */
@ManagedBean(name="login")
@RequestScoped
public class BeanLogin {

	private String email;
	private String password;

	@ManagedProperty(value = "#{usersession}")
	private BeanSession sessionBean;

	public void performLogin(ActionEvent event) {
		sessionBean.performLogin(email, password);
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


}
