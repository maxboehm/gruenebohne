package com.gruenebohne.EJB;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gruenebohne.model.Address;
import com.gruenebohne.model.Customer;
import com.gruenebohne.model.Producer;
import com.gruenebohne.model.Product;
import com.gruenebohne.model.ProductBase;
import com.gruenebohne.model.Record;
import com.gruenebohne.model.RecordItem;

@Stateless
@LocalBean
public class BasketEJB {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private CustomerEJB customerejb;

	@EJB
	private ProducerEJB producerejb;

	public Record getNewBasket() {
		return new Record();
	}

	/**
	 * Create order and send confirmation email subsequently
	 * 
	 * @param updatedCustomer
	 * @param newOrder
	 * @param address
	 * @throws Exception
	 */
	public void createOrder(Customer updatedCustomer, Record newOrder,
			Address address) throws Exception {
		// Get records
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", newOrder.getId()).getResultList();
		// set customer
		newOrder.setCustomer(updatedCustomer);
		// set the price
		newOrder.setTotalPrice(getTotalPrice(newOrder));

		if (!result.isEmpty()) {
			// save order
			Record order = em.merge(newOrder);
			em.flush();

			if (order != null) {
				List<Record> temp = em
						.createNamedQuery("getRecord", Record.class)
						.setParameter("basketId", order.getId())
						.getResultList();
				// set confirmation email
				sendConfirmationEmail(temp.get(0));
			}
		}
	}

	/**
	 * @param basket
	 * @param product
	 * @param nQuantity
	 */
	public void addProductToBasket(Record basket, ProductBase product,
			int nQuantity) {
		// Ermittle den Warenkorb
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();

		// Warenkorb noch nicht in Datenbank
		if (result.isEmpty()) {
			hlpAdd(basket, product, nQuantity);
			em.persist(basket);
			em.flush();

			// Warenkorb in Datenbank
		} else {
			hlpAdd(basket, product, nQuantity);
			em.merge(basket);
			em.flush();
		}
	}

	/**
	 * @param basket
	 * @param product
	 * @param nQuantity
	 */
	private void hlpAdd(Record basket, ProductBase product, int nQuantity) {
		// Search if this product is already in the basket
		for (RecordItem itemExistent : basket.getSetRecordItems())
			if (itemExistent.getProductBase().getProdId() == product
					.getProdId()) {
				// if it is already there, just modify the quantity
				itemExistent
						.setQuantity(itemExistent.getQuantity() + nQuantity);
				return;
			}

		// if it's not there, create a new record
		RecordItem item = new RecordItem();
		item.setProductBase(product);
		item.setQuantity(nQuantity);
		item.setRecord(basket);
		basket.getSetRecordItems().add(item);

	}

	/**
	 * @param basket
	 * @param product
	 */
	public void removeProductFromBasket(Record basket, ProductBase product) {
		// Retrieve all records
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();

		RecordItem deletItem = null;
		for (RecordItem item : result.get(0).getSetRecordItems()) {
			// search for the record which should get deleted
			if (item.getProductBase().getProdId() == product.getProdId())
				deletItem = item;
		}
		result.get(0).getSetRecordItems().remove(deletItem);
		em.flush();

	}

	/**
	 * @param basket
	 * @return
	 */
	public Record refreshBasket(Record basket) {
		// reload basket
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();
		return result.get(0);
	}

	public int getBasketSize(Record basket) {
		int size = 0;
		// load basket
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();
		if (!result.isEmpty())
			size = result.get(0).getSetRecordItems().size();

		return size;
	}

	public double getTotalPrice(Record basket) throws Exception {
		double price = 0;
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();
		if (!result.isEmpty())
			// get price through iteration and summing up
			for (RecordItem item : result.get(0).getSetRecordItems())
				price += item.getProductBase().getPrice() * item.getQuantity();

		return price;
	}

	public void editQuantity(Record basket, int productId, int quantity) {
		List<Record> result = em.createNamedQuery("getRecord", Record.class)
				.setParameter("basketId", basket.getId()).getResultList();

		for (RecordItem item : result.get(0).getSetRecordItems())
			if (item.getProductBase().getProdId() == productId)
				item.setQuantity(quantity);

		em.flush();
	}

	/**
	 * Confirmation email by order
	 * 
	 * @param order
	 */
	private void sendConfirmationEmail(Record order) {
		// configure host
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						// set password and user
						return new PasswordAuthentication(
								"gruenebohne.store@gmail.com", "tobias123");
					}
				});

		try {
			// create a new message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("gruenebohne.store@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(order.getCustomer().geteMail()));
			message.setSubject("Gruenebohne Bestellung");
			StringBuilder build = new StringBuilder();
			ArrayList<RecordItem> list = new ArrayList<RecordItem>(
					order.getSetRecordItems());

			String deliveryCost = "";
			Double totalCost = new BigDecimal(order.getTotalPrice()).setScale(
					2, RoundingMode.HALF_UP).doubleValue();
			if (order.getTotalPrice() < 20) {
				deliveryCost = "5,95€";
				totalCost = totalCost + 5.95d;
			} else {
				deliveryCost = "Kostenloser Versand";
			}

			for (int i = 0; i < list.size(); i++) {
				BigDecimal price = new BigDecimal(list.get(i).getProductBase()
						.getPrice()).setScale(2, RoundingMode.HALF_UP);
				build.append("<tr><td style=\"text-align:center\">" + (i + 1)
						+ "</td>" + "<td style=\"text-align:center\">"
						+ list.get(i).getProductBase().getProdName() + "</td>"
						+ "<td style=\"text-align:center\">"
						+ list.get(i).getQuantity() + "</td>"
						+ "<td style=\"text-align:center\">" + price + "€"
						+ "</td>" + "<td style=\"text-align:center\">"
						+ price.doubleValue() * list.get(i).getQuantity() + "€"
						+ "</td></tr>");
			}
			message.setContent(
					"Hallo "
							+ order.getCustomer().getFirstName()
							+ " "
							+ order.getCustomer().getLastName()
							+ ","
							+ "<br> <br> <br> Vielen Dank fuer deine Bestellung im gruenbohne Demoshop (Bestellungsnummer: "
							+ order.getId()
							+ ") am "
							+ DateFormat.getDateInstance(DateFormat.SHORT)
									.format(GregorianCalendar.getInstance()
											.getTime())
							+ " um "
							+ DateFormat.getTimeInstance(DateFormat.SHORT)
									.format(GregorianCalendar.getInstance()
											.getTime())
							+ ".<br>Informationen zu deiner Bestellung:"
							+ "<br>"
							+ "<br><br>"
							+ "<table style=\"width:80%\">"
							+ "<tbody>"
							+ "<tr><th>Position</th><th>Artikelname</th><th>Menge</th><th>Preis</th><th>Summe</th></tr>"
							+ build.toString()
							+ "</tbody>"
							+ "</table>"
							+ "<br><br>"
							+ "Versandkosten: "
							+ deliveryCost
							+ "<br>"
							+ "Gesamtkosten: "
							+ totalCost
							+ "€ <br>"
							+ "Gewählte Zahlungsart: RECHNUNG <br>"
							+ "<br>"
							+ "Wir ziehen den Betrag in den nächsten Tagen von deinem Konto ein. Der Versand"
							+ "erfolgt an die gespeicherte Adresse. "
							+ "Für Rückfragen stehen wir dir jederzeit gerne zur Verfügung.<br><br><br>"
							+ "Wir wünschen dir noch einen schönen Tag <br>"
							+ "Die gruenebohne :)", "text/html; charset=utf-8");

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("gruenebohne.store@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("gruenebohne.store@gmail.com"));
			message.setSubject("Neue Bestellunng");
			StringBuilder build = new StringBuilder();
			ArrayList<RecordItem> list = new ArrayList<RecordItem>(
					order.getSetRecordItems());

			String deliveryCost = "";
			Double totalCost = new BigDecimal(order.getTotalPrice()).setScale(
					2, RoundingMode.HALF_UP).doubleValue();
			if (order.getTotalPrice() < 20) {
				deliveryCost = "5,95€";
				totalCost = totalCost + 5.95d;
			} else {
				deliveryCost = "Kostenloser Versand";
			}

			for (int i = 0; i < list.size(); i++) {
				List<Producer> producer = producerejb.getAllProducer();
				int id = list.get(i).getId();
				String itemProducer = "";

				for (Producer currentProducer : producer) {
					ArrayList<Product> producerProducts = new ArrayList(
							currentProducer.getProducts());
					for (Product prod : producerProducts) {
						if (id == prod.getProdId()) {
							itemProducer = currentProducer.getName();
							break;
						}
					}
				}
				if (itemProducer == "") {
					itemProducer = producer.get((int) (3 * Math.random()))
							.getName();
				}
				BigDecimal price = new BigDecimal(list.get(i).getProductBase()
						.getPrice()).setScale(2, RoundingMode.HALF_UP);
				build.append("<tr><td style=\"text-align:center\">" + (i + 1)
						+ "</td><td style=\"text-align:center\">"
						+ list.get(i).getId() + "</td>"
						+ "<td style=\"text-align:center\">"
						+ list.get(i).getProductBase().getProdName() + "</td> "
						+ "<td style=\"text-align:center\">" + itemProducer
						+ "</td>" + "<td style=\"text-align:center\">"
						+ list.get(i).getQuantity() + "</td>"
						+ "<td style=\"text-align:center\">" + price + "€"
						+ "</td>" + "<td style=\"text-align:center\">"
						+ price.doubleValue() * list.get(i).getQuantity() + "€"
						+ "</td>");
			}

			ArrayList<Address> adresse = new ArrayList<Address>(order
					.getCustomer().getAddress());
			message.setContent(
					"Neue Bestellung von: "
							+ order.getCustomer().getFirstName()
							+ " "
							+ order.getCustomer().getLastName()
							+ " am "
							+ DateFormat.getDateInstance(DateFormat.SHORT)
									.format(GregorianCalendar.getInstance()
											.getTime())
							+ " um "
							+ DateFormat.getTimeInstance(DateFormat.SHORT)
									.format(GregorianCalendar.getInstance()
											.getTime())
							+ "<br>"
							+ "BestellID: "
							+ order.getId()
							+ "<br>"
							+ "<br>"
							+ "<table style=\"width:80%\">"
							+ "<tbody>"
							+ "<tr><th>Position</th><th>ArtikelID</th><th>Artikelname</th><th>Produzent</th><th>Menge</th><th>Preis</th><th>Summe</th></tr>"
							+ build.toString()
							+ "</tbody>"
							+ "</table>"
							+ "<br><br>"
							+ "Kundenname: "
							+ order.getCustomer().getFirstName()
							+ " "
							+ order.getCustomer().getLastName()
							+ " <br>"
							+ "Straße: "
							+ adresse.get(0).getStreetAndNumner()
							+ " <br>"
							+ "Postleitzahl: "
							+ adresse.get(0).getPostalCode()
							+ " <br>"
							+ "Stadt: "
							+ adresse.get(0).getCity()
							+ " <br>"
							+ " <br>"
							+ "<br><br>"
							+ "Versandkosten: "
							+ deliveryCost
							+ "<br>"
							+ "Gesamtkosten: " + totalCost + "€ <br>"
							+ "Gewählte Zahlungsart: RECHNUNG <br>" + "<br>",
					"text/html; charset=utf-8");

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
