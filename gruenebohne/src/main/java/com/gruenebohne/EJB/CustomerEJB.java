package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Customer;

@Stateless
@LocalBean
public class CustomerEJB {

	@PersistenceContext
	private EntityManager em;


	public List<Customer> getAllCustomers() {
		return em.createNamedQuery("AllCustomer", Customer.class).getResultList();
	}

	public Customer loginCustomer(String eMail, String password){

		List<Customer> customer = em.createNamedQuery("GetCustomer", Customer.class).setParameter("custEmail",eMail).setParameter("custPassword", password).getResultList();
		if (customer.isEmpty()){
			return null;
		}
		return customer.get(0);
	}

	public void registerCustomer(Customer customer) {
		em.persist(customer);
		em.flush();
	}
}
