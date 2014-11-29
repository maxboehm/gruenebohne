package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Producer;

@Stateless
@LocalBean
public class ProducerEJB {

	@PersistenceContext
	private EntityManager em;

	public List<Producer> getAllProducer() {
		return em.createNamedQuery("AllProducer", Producer.class).getResultList();
	}

	public Producer getProducer(int id){
		// get producer by id
		List <Producer> producer = em.createNamedQuery("GetProducer", Producer.class).setParameter("id", id).getResultList();

		// no one there? return null
		if(producer.isEmpty()) return null;

		// Otherwise return producer
		return producer.get(0);
	}

}
