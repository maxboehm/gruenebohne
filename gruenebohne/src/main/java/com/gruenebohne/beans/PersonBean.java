package com.gruenebohne.beans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.gruenebohne.DTO.PersonEJB;
import com.gruenebohne.model.Person;

/**
 * Bean encapsulating all operations for a person.
 */
@ManagedBean(name="person")
@SessionScoped
public class PersonBean {
	
	
	@EJB
	private PersonEJB personejb;
    
    public void test(ActionEvent event){
    	System.out.println("test");
    	Person person = new Person();
    	person.setFirstName("FUCK THIS");
    	person.setLastName("SHIT2");
    	
    	personejb.addPerson(person);
    }
}
