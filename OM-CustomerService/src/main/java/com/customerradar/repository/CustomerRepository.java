package com.customerradar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.customerradar.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	Customer findCustomerByName(String customerName);
	Customer findCustomerByTelephone(String telephone);

}
