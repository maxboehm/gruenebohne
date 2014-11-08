package com.gruenebohne.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AddressTyp {

	// #################################################################
	// member
	// #################################################################

	@Id
	@GeneratedValue
	@Column(name="addresstyp_id")
	private int id;

	@Column private String alias;

	// #################################################################
	// getter-setter
	// #################################################################


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
