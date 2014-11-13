package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.beans.session.BeanSession;
import com.gruenebohne.model.Address;
import com.gruenebohne.model.Customer;

@Stateless
@LocalBean
public class CustomerEJB {

	@PersistenceContext
	private EntityManager em;

	public Customer loginCustomer(String eMail, String password) {

		List<Customer> customer = em
				.createNamedQuery("GetCustomer", Customer.class)
				.setParameter("custEmail", eMail)
				.setParameter("custPassword", password).getResultList();
		if (customer.isEmpty()) {
			return null;
		}
		return customer.get(0);
	}

	public void registerCustomer(Customer customer) {
		em.persist(customer);
		em.flush();
	}

	public void updateCustomer(Customer updatedCustomer, Address address, String eMail, String password) {
		List<Customer> oldCustomer = em
				.createNamedQuery("GetCustomer", Customer.class)
				.setParameter("custEmail", eMail)
				.setParameter("custPassword", password)
				.getResultList();

		address.setCustomer(oldCustomer.get(0));
		em.persist(address);
		em.flush();

		oldCustomer.get(0).getAddress().clear();
		oldCustomer.get(0).getAddress().add(address);

		em.merge(oldCustomer.get(0));
		em.flush();

	}
	
	public Customer refreshCustomer(Customer customer){
		List<Customer> customerExist = em.createNamedQuery("GetCustomer").setParameter("custEmail", customer.geteMail()).setParameter("custPassword", customer.getPassWord()).getResultList();
		if(!customerExist.isEmpty()){
			return customerExist.get(0);
		}
		return customer;
	}
}
