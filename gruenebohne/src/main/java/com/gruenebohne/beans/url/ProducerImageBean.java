package com.gruenebohne.beans.url;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.gruenebohne.EJB.ProducerEJB;

@ManagedBean(name = "producerimage")
@RequestScoped
public class ProducerImageBean extends ImageBean{

	@EJB
	private ProducerEJB producerEJB;

	@Override
	protected byte[] getPicture(int nID) {
		return producerEJB.getProducer(nID).getPicture();
	}
}