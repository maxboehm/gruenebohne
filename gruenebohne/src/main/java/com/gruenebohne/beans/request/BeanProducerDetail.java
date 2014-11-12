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

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private Producer producer;

	public void loadProducer(){
		producer = null;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

}
