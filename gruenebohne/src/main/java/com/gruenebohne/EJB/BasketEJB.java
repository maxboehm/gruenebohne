package com.gruenebohne.EJB;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.ProductBase;
import com.gruenebohne.model.Record;
import com.gruenebohne.model.RecordItem;

@Stateless
@LocalBean
public class BasketEJB {

	@PersistenceContext
	private EntityManager em;

	public Record getNewBasket() {
		Record record = new Record();
		return record;
	}

	/**
	 * @param basket
	 * @param product
	 * @param nQuantity
	 */
	public void addProductToBasket(Record basket, ProductBase product, int nQuantity) {
		// Ermittle den Warenkorb
		List<Record> result = em.createNamedQuery("getRecord", Record.class).setParameter("basketId", basket.getId()).getResultList();

		// Warenkorb noch nicht in Datenbank
		if (result.isEmpty()) {
			hlpAdd(basket, product, nQuantity);
			em.persist(basket);
			em.flush();

			// Warenkorb in Datenbank
		} else {
			System.out.println("AKTUELLER WARENKORB");
			for (RecordItem item : result.get(0).getSetRecordItems()) {
				System.out.println("POSITION:");
				System.out.println(item.getProductBase().getProdName() + " "
						+ item.getQuantity());
			}
			System.out.println("ENDE");
			hlpAdd(basket, product, nQuantity);
			em.merge(basket);
			em.flush();
		}
	}

	private void hlpAdd(Record basket, ProductBase product, int nQuantity){
		for(RecordItem itemExistent:basket.getSetRecordItems())
			if(itemExistent.getProductBase().getProdId()==product.getProdId()){
				itemExistent.setQuantity(itemExistent.getQuantity()+nQuantity);
				return;
			}

		RecordItem item = new RecordItem();
		item.setProductBase(product);
		item.setQuantity(nQuantity);
		item.setRecord(basket);
		basket.getSetRecordItems().add(item);



	}

	public void removeProductFromBasket(Record basket, ProductBase product) {

		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();

		RecordItem deletItem = null;
		for (RecordItem item : result.get(0).getSetRecordItems()) {
			if (item.getProductBase().getProdId() == product.getProdId()) {
				deletItem = item;
			}
		}
		result.get(0).getSetRecordItems().remove(deletItem);
		em.flush();

	}

	public Record refreshBasket(Record basket) {
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();

		return result.get(0);
	}

	public int getBasketSize(Record basket) {
		int size = 0;
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();
		if (!result.isEmpty()) {
			size = result.get(0).getSetRecordItems().size();
		}
		return size;
	}

	public double getTotalPrice(Record basket) throws Exception {
		double price = 0;
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();
		if (!result.isEmpty()) {
			for (RecordItem item : result.get(0).getSetRecordItems()) {
				System.out.println(item.getQuantity());
				System.out.println(item.getProductBase());
				System.out.println(item.getProductBase().getPrice());
				price += item.getProductBase().getPrice() * item.getQuantity();
			}
		}
		return price;
	}
}
