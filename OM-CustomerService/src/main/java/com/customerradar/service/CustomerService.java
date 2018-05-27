package com.customerradar.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customerradar.model.Address;
import com.customerradar.model.Customer;
import com.customerradar.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	   
    private static List<Customer> customers;
	
    static{
    	customers= populateDummyCustomers();
    }
    
	@Autowired
	CustomerRepository customerRepository;
	
	/**
	 * This method should list all customers
	 * @return
	 */
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub		
		return customerRepository.findAll();
	}
	
	
	public Customer getCustomerByCustomerId(int id) {
		return customerRepository.findOne(id);		
	}
	
	
	/**
	 * This method should return only one customer data based on customerName
	 * @param customerName
	 * @return
	 */
	public Customer getCustomerByCustomerName(String customerName) {						
		 return customerRepository.findCustomerByName(customerName);
	}
	
	/**
	 * This method should return only one customer data based on telephone
	 * @param telephone
	 * @return
	 */
	
	public Customer getCustomerByTelephone(String telephone) {
		return customerRepository.findCustomerByTelephone(telephone);	
	}
	
	/**
	 * This method should add customer details
	 * @param cust
	 * @return 
	 */
	public void addCustomer(Customer cust) {	
		List<Customer> custList = new ArrayList<>();
		
		custList.add(cust);
		
		if(cust != null) {
			if(cust.getName() != null && cust.getTelephone() != null) {
				// Fetch values from incoming object
				String name = cust.getName();
				String tel = cust.getTelephone();

				// Set values in Model object
				cust.setName(name);				
				cust.setTelephone(tel);
				
				if(cust.getAddress() != null) {
					Address add = cust.getAddress();
					String stAdd = add.getStreet();
					String city = add.getCity();
					String state = add.getState();
					String country = add.getCountry();
					
					add.setStreet(stAdd);
					add.setCity(city);
					add.setState(state);
					add.setCountry(country);
					
					add.setCustomer(cust);
					
					cust.setAddress(add);
				}												
				
				// Save Customer model object through repo. 
				customerRepository.save(cust);			
			} 
		}		
		
	}

	/**
	 * This method deletes customer record by Id
	 * @param id
	 */
	public void deleteCustomerById(Integer id) {
		// TODO Auto-generated method stub
		customerRepository.delete(this.getCustomerByCustomerId(id));
	}
	
	/**
	 * This method deletes customer record by Customer name
	 * @param strName
	 */
	public void deleteCustomerByName(String strName) {
		// TODO Auto-generated method stub
		customerRepository.delete(this.getCustomerByCustomerName(strName));
	}
	
	/**
	 * This method updates Customer Address details using customerName  
	 * 
	 * @param customerName
	 * @param custVO
	 * @return updatedCopyOfCustomerAddress data 
	 */
	public Customer updateCustomerAddressDetailsByCustName(Customer custVO) {
		// TODO Auto-generated method stub									
		customerRepository.save(custVO);											
		
		return custVO;
	}
	
	/**
	 * This method updates Customer Address details using telephone 
	 * 
	 * @param telephone
	 * @param custVO
	 * @return updatedCopyOfCustomerAddress data 
	 */
	public Customer updateCustomerAddressDetailsByTelephone(Customer custVO) {
		// TODO Auto-generated method stub											
		customerRepository.save(custVO);																
		
		return custVO;
	}
	
	
	public boolean isCustomerExist(Customer customer) {
        return this.getCustomerByCustomerName(customer.getName())!=null;
    }
	
	 private static List<Customer> populateDummyCustomers(){
	        List<Customer> customers = new ArrayList<Customer>();
	        customers.add(new Customer("Sam","3070000"));
	        customers.add(new Customer("Tom","4050000"));
	        customers.add(new Customer("Jerome","4530000"));
	        customers.add(new Customer("Silvia","5040000"));
	        return customers;
	    }
	
}
