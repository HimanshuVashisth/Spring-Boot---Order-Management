package com.customerradar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
@Entity
@Table(name="address", catalog="testDB")
public class Address implements Serializable {
			
	/*@GeneratedValue(generator="generator")
	@GenericGenerator(name = "generator", strategy = "foreign",parameters = @Parameter(name = "property", value = "address"))*/
	private Integer id;	
	
	@Column(name = "STREET", nullable = false, length = 40)
	private String street;
	
	@Column(name = "CITY", nullable = false, length = 30)
	private String city;	
	
	@Column(name = "STATE", nullable = false, length = 30)
	private String state;	
	
	@Column(name = "COUNTRY", nullable = false, length = 30)
	private String country;	
		
	@JsonBackReference
	private Customer customer;
	
	public Address() {}

	public Address(String street, String city, String state, String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.country = country;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	@OneToOne(mappedBy="address")
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
