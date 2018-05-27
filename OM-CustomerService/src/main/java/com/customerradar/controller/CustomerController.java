package com.customerradar.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.customerradar.model.Customer;
import com.customerradar.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	CustomerService customerService; 
	
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }
	
	@GetMapping("/all")
	public ResponseEntity<List<Customer>> getAllCustomerDetails() {
		List<Customer> customerList = customerService.getAllCustomers();
		
		if(customerList.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
		
        return new ResponseEntity<List<Customer>>(customerList, HttpStatus.OK);	
	}
	
	@RequestMapping(value = "/id={custId}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerDetailsById(@PathVariable("custId") final int custId) {
		logger.info("Finding customer details by Id : "+custId);
		Customer vo = customerService.getCustomerByCustomerId(custId);
		 
		if(vo == null) {
			logger.info("Customer with id " + custId + " not found");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Customer>(vo, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/name={custName}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerDetailsByName(@PathVariable("custName") final String custName) {
		logger.info("Finding customer details by Name : "+custName);
		Customer vo = customerService.getCustomerByCustomerName(custName);
		 
		if(vo == null) {
			logger.info("Customer with name " + custName + " not found");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);	
		}
			
		return new ResponseEntity<Customer>(vo, HttpStatus.OK);
	}
	
		
	@RequestMapping(value = "/telephone={telephone}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerDetailsByTelephone(@PathVariable("telephone") final String telephone) {
		logger.info("Finding customer details by Telephone : "+telephone);
		Customer vo = customerService.getCustomerByTelephone(telephone);
		 
		if(vo == null) {
			logger.info("Customer with Telephone " + telephone + " not found");
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);	
		}
			
		return new ResponseEntity<Customer>(vo, HttpStatus.OK);	
	}
	
	@PostMapping("/add")
	public ResponseEntity<Void> addCustomerDetails(@RequestBody Customer customer, UriComponentsBuilder ucBuilder) {					
		logger.info("Creating Customer " + customer.getName());
		
		if (customerService.isCustomerExist(customer)) {
			logger.info("A Customer with name " + customer.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        
        customerService.addCustomer(customer);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(customer.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/update/name={customerName}")
	public ResponseEntity<Customer> updateCustomerDetailsByCustName(@PathVariable("customerName") final String customerName, 															
			 														@RequestBody Customer customer) {
		logger.info("Updating customer address details for customer name : "+customerName);		
		Customer currentCustomer = customerService.getCustomerByCustomerName(customerName);
		
        if (currentCustomer==null) {
        	logger.info("Customer with name " + customerName + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
                        
        currentCustomer.setName(customer.getName());
        currentCustomer.setTelephone(customer.getTelephone());
        currentCustomer.setAddress(customer.getAddress());  
        currentCustomer.getAddress().setId(customer.getId());
        
        customerService.updateCustomerAddressDetailsByCustName(currentCustomer);
        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);
		
	}
			
	@PutMapping("/update/phone={telephone}")
	public ResponseEntity<Customer> updateCustomerDetailsByTelephone(@PathVariable("telephone") final String telephone, 
																		@RequestBody Customer customer) {
		logger.info("Updating customer address details for customer Phone : "+telephone);		
		Customer currentCustomer = customerService.getCustomerByTelephone(telephone);
		
        if (currentCustomer==null) {
        	logger.info("Customer with phone " + telephone + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
         
        //currentCustomer.setId(customer.getId());
        currentCustomer.setName(customer.getName());
        currentCustomer.setTelephone(customer.getTelephone());        
        currentCustomer.setAddress(customer.getAddress());   
        currentCustomer.getAddress().setId(customer.getId());
       
        
        customerService.updateCustomerAddressDetailsByTelephone(currentCustomer);
        return new ResponseEntity<Customer>(currentCustomer, HttpStatus.OK);
	}
		
	@DeleteMapping("/delete/custId={custId}")
	public ResponseEntity<Customer> deleteCustomerDetails(@PathVariable("custId") final Integer id) {
			customerService.deleteCustomerById(id);
		return ResponseEntity.ok().build();
	}	
		
	@DeleteMapping("/delete/name={custName}")
	public ResponseEntity<Customer> deleteCustomerDetails(@PathVariable("custName") final String custName) {
			customerService.deleteCustomerByName(custName);
		return ResponseEntity.ok().build();
	}

}