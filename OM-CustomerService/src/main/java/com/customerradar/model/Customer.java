package com.customerradar.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.customerradar.service.View;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;


@Data
@Entity()
@Table(name="customer", catalog="testDB" /* ,uniqueConstraints= {
		@UniqueConstraint(columnNames="CUSTOMER_NAME"),
		@UniqueConstraint(columnNames="TELEPHONE")
		}*/)
public class Customer implements Serializable {

	private int id;
	
	@Column(name="NAME",nullable = false, length = 30)
	private String name;		     
    
	@Column(name="TELEPHONE",nullable = false, length = 10)
	private String telephone;
	
	//@JsonBackReference
	private Address address; 
    
	public Customer(){}
	
    public Customer(String name, String telephone) {
		this.name = name;
		this.telephone = telephone;
	}
	
	public Customer(String name, String telephone, Address address) {
		super();
		this.name = name;
		this.telephone = telephone;
		this.address = address;
	}

	@Id
    @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="customer_address_id")	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
		
}