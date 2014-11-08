package com.gruenebohne.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Producer
 *
 */
@Entity

public class Producer {

	// #################################################################
	// members
	// #################################################################

	@Id
	@GeneratedValue
	@Column(name="producer_id")
	private int id;

	@Column private String Name;
	@Column private String Description;


	// #################################################################
	// getter-setter
	// #################################################################

	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}
	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}

}
