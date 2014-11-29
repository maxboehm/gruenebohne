package com.gruenebohne.beans.session;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import com.gruenebohne.EJB.BasketEJB;
import com.gruenebohne.EJB.CustomerEJB;
import com.gruenebohne.EJB.ProductBaseEJB;
import com.gruenebohne.EJB.RecipeEJB;
import com.gruenebohne.model.Address;
import com.gruenebohne.model.Customer;
import com.gruenebohne.model.ProductBase;
import com.gruenebohne.model.Recipe;
import com.gruenebohne.model.Record;
import com.gruenebohne.model.RecordItem;

@ManagedBean(name = "basket")
@SessionScoped
public class BeanBasket {

	private Record currentBasket = null;

	private int basketSize;
	private String totalPrice;

	@EJB private BasketEJB ejbBasket;
	@EJB private ProductBaseEJB ejbProductBase;
	@EJB private CustomerEJB ejbCustomer;
	@EJB private RecipeEJB ejbRecipe;

	@ManagedProperty(value="#{usersession}")
	private BeanSession session;

	@PostConstruct
	private void init() {
		// create an empty basket after bean got constructed
		currentBasket = ejbBasket.getNewBasket();
	}

	/**
	 * Create a new, empty basket
	 */
	public void resetBasket(){
		System.out.println("new basket");
		// create new basket
		currentBasket = ejbBasket.getNewBasket();
	}

	/**
	 * create an order
	 * @param updatedCustomer
	 * @param oldCustomer
	 * @param comment
	 * @param address
	 * @throws Exception
	 */
	public void createOrder(Customer updatedCustomer, Customer oldCustomer ,String comment, Address address) throws Exception{
		// update customer
		ejbCustomer.updateCustomer(updatedCustomer, address, oldCustomer.geteMail(), oldCustomer.getPassWord());
		// finalize basket
		this.currentBasket.setFinalized(true);
		this.currentBasket.setComment(comment);
		// create an order from the basket
		ejbBasket.createOrder(updatedCustomer, this.currentBasket, address);

	}

	/**
	 * Add an item to the basket
	 * @param event
	 */
	public void performAdd(ActionEvent event) {
		addProductToBasket();
	}

	/**
	 * @param event
	 */
	public void performAddAjax(AjaxBehaviorEvent event) {
		addProductToBasket();
	}

	private void addProductToBasket(){
		// determine id and quantity
		String prodId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("prodId");
		String quantity = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("quantity");

		// get the product
		ProductBase product = ejbProductBase.getProduct(Integer.parseInt(prodId));

		// add the product by quantity to the basket
		ejbBasket.addProductToBasket(this.currentBasket, product, Integer.parseInt(quantity));

		// refresh the basket
		currentBasket = ejbBasket.refreshBasket(this.currentBasket);
		toString();
	}

	public void performDelete(ActionEvent event){
		removeProductFromBasket();
	}

	public void performDeleteAjax(AjaxBehaviorEvent event){
		removeProductFromBasket();
	}

	/**
	 * Remove a product from the basket
	 */
	private void removeProductFromBasket(){
		// determine product-id
		String prodId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("prodId");
		// determine product
		ProductBase product = ejbProductBase.getProduct(Integer.parseInt(prodId));
		// remove from basket
		ejbBasket.removeProductFromBasket(this.currentBasket, product);
		// refresh basket
		currentBasket = ejbBasket.refreshBasket(this.currentBasket);
		toString();
	}

	private void setQuantity(){
		// get request-data
		String productId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productId");
		String quantity = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("productQuantity");

		// bigger than zero?
		if(Integer.parseInt(quantity)>0)
			// set quantity
			ejbBasket.editQuantity(this.currentBasket, Integer.parseInt(productId), Integer.parseInt(quantity));

		// refresh basket
		currentBasket = ejbBasket.refreshBasket(this.currentBasket);
		toString();
	}

	public void setQuantity(ActionEvent event){
		setQuantity();
	}

	public void setQuantityAjax(AjaxBehaviorEvent event){
		setQuantity();
	}

	public String getTotalPrice() throws Exception{
		// get total price
		double dtotalPrice= ejbBasket.getTotalPrice(this.currentBasket);
		// format the price
		totalPrice = new DecimalFormat("0.00").format(dtotalPrice).replace(",", ".");
		return totalPrice;
	}

	public String getTotalPriceWithDeliveryCost() throws Exception{
		// get total price
		double dtotalPrice= ejbBasket.getTotalPrice(this.currentBasket);
		// less than 20?
		if(dtotalPrice<20)
			// add deliver cots
			dtotalPrice = dtotalPrice+5.95d;

		// format price
		totalPrice = new DecimalFormat("0.00").format(dtotalPrice).replace(",", ".");
		return totalPrice;
	}

	public boolean getDeliveryCost() throws Exception{
		double dtotalPrice= ejbBasket.getTotalPrice(this.currentBasket);
		return dtotalPrice>=20 ? true:false;
	}

	/**
	 * Get the recipes we show to the customer
	 * @return
	 */
	public List<Recipe> getRecipes(){
		ArrayList<Recipe> recipes = new ArrayList<Recipe>(ejbRecipe.getAllRecipes());
		Collections.shuffle(recipes);
		return recipes.subList(0, 3);
	}

	/**
	 * determine the number of products in the basket
	 * @return
	 */
	public int getBasketSize(){
		basketSize = ejbBasket.getBasketSize(this.currentBasket);
		return basketSize;
	}

	public Record getCurrentBasket() {
		return currentBasket;
	}

	public void setCurrentBasket(Record currentBasket) {
		this.currentBasket = currentBasket;
	}

	@Override
	public String toString() {
		System.out.println("AKTUELLER WARENKORB BEGINN");
		for (RecordItem item : this.currentBasket.getSetRecordItems()) {
			System.out.println(item.getProductBase().getProdName() + " "
					+ item.getQuantity());
		}
		System.out.println("AKTUELLER WARENKORB ENDE");
		return null;
	}

	public BeanSession getSession() {
		return session;
	}

	public void setSession(BeanSession session) {
		this.session = session;
	}



}
