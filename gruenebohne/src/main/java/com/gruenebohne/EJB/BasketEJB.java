package com.gruenebohne.EJB;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Product;
import com.gruenebohne.model.Record;
import com.gruenebohne.model.RecordItem;

@Stateless
@LocalBean
public class BasketEJB {

	@PersistenceContext
	private EntityManager em;

	public Record getNewBasket(){
		Record record = new Record();
		return record;
	}

	public void addProductToBasket(Record basket, Product product, int nQuantity){
		// Ist das Item bereits im Warenkorb?
		for(RecordItem item:basket.getSetRecordItems()){
			// Suche es
			if(item.getProduct().equals(product)){
				// Erhöhe die Anzahl
				item.setQuantity(item.getQuantity()+nQuantity);
				// Keine weiter Aktion nötig
				return;
			}
		}

		// Das Produkt ist noch nicht im Warenkorb?
		RecordItem item = new RecordItem();
		item.setProduct(product);
		item.setQuantity(nQuantity);

		// Dem Warenkorb hinzufügen
		basket.addRecordItem(item);
	}
}
