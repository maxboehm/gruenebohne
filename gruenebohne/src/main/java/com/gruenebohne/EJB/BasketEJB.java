package com.gruenebohne.EJB;

import java.util.List;
import java.util.Set;

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

	public Record getNewBasket() {
		Record record = new Record();
		return record;
	}

	public void addProductToBasket(Record basket, Product product, int nQuantity) {

		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();

		if (!result.isEmpty()) {
			System.out.println("AKTUELLER WARENKORB");
			for (RecordItem item : result.get(0).getSetRecordItems()) {
				System.out.println("POSITION:");
				System.out.println(item.getProduct().getProdName() + " "
						+ item.getQuantity());
			}
			System.out.println("ENDE");

			RecordItem item = new RecordItem();
			item.setProduct(product);
			item.setQuantity(nQuantity);
			item.setRecord(basket);
			basket.getSetRecordItems().add(item);
			em.merge(basket);
			em.flush();
		} else {
			RecordItem item = new RecordItem();
			item.setProduct(product);
			item.setQuantity(nQuantity);
			item.setRecord(basket);
			basket.getSetRecordItems().add(item);
			em.persist(basket);
			em.flush();
		}

	}

	public void removeProductFromBasket(Record basket, Product product) {

		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();

		RecordItem deletItem = null;
		for (RecordItem item : result.get(0).getSetRecordItems()) {
			if (item.getProduct().getProdId() == product.getProdId()) {
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

	public double getTotalPrice(Record basket) {
		double price = 0;
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();
		if (!result.isEmpty()) {
			for (RecordItem item : result.get(0).getSetRecordItems()) {
				price += item.getProduct().getPrice() * item.getQuantity();
			}
		}
		return price;
	}
}
