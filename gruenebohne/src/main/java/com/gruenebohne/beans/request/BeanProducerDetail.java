package com.gruenebohne.beans.request;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.gruenebohne.EJB.ProducerEJB;
import com.gruenebohne.model.Producer;

@ManagedBean(name="produzentdetail")
@RequestScoped
public class BeanProducerDetail {

	@EJB
	private ProducerEJB producerEJB;

	private Producer producer;
	private int id;

	/**
	 * Set the producer via the id
	 */
	public void loadProducer(){
		producer = producerEJB.getProducer(getId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

}
