package com.customerradar.test;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.customerradar.CustomerServiceAppApplication;
import com.customerradar.controller.CustomerController;
import com.customerradar.model.Address;
import com.customerradar.model.Customer;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=CustomerServiceAppApplication.class)
public class CustomerServiceAppApplicationTests {

	private static final String REST_SERVICE_URI = "http://localhost:8761/api/customer-Service";	
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceAppApplicationTests.class);
	private static List<Customer> customers;
	
	@BeforeSuite
	public void init() {
		customers = new ArrayList<Customer>();			
        customers.add(new Customer("Himanshu","0225216620",new Address("85 Beach Road","Auckland Central","Auckland","NZ")));
        customers.add(new Customer("ABC","0222216432",new Address("87 Vincent Road","Auckland Central","Auckland","NZ")));
        customers.add(new Customer("Sam","0223070000",new Address("185 Victoria Street","Auckland Central","Auckland","NZ")));
        customers.add(new Customer("Tom","0276450000",new Address("60 Beresford Square","Auckland Central","Auckland","NZ")));        	                
    }
	
	/* POST */
	@BeforeTest
	public void createCustomer() {
    	logger.info("Testing create Customer API----------");
        RestTemplate restTemplate = new RestTemplate();
        CustomerServiceAppApplicationTests.customers.stream()         											
        											.forEach(customer -> 
        											restTemplate.postForLocation(REST_SERVICE_URI+"/customer/add", customer, Customer.class));               
        
        logger.info("Customer records created successfully.");
    }
	
	
	@Test
    public void getAllCustomers(){
    	logger.info("Testing Customers API-----------");
          
    	RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
         
        ResponseEntity<Customer> result = restTemplate.exchange(REST_SERVICE_URI+"/customer/", HttpMethod.GET, null, Customer.class);
          
        if(result!=null){
        	Customer cust = result.getBody();
        	logger.info("Customer : id="+cust.getId()+", Name="+cust.getName()+", Phone="+cust.getTelephone());                       
        }else{
        	logger.info("No Customer exist----------");
        }
    }
         
    @Test
	public void getCustomerByNameTest() {						
		logger.info("Testing getCustomerByName API----------");
        RestTemplate restTemplate = new RestTemplate();
        Customer custFound = restTemplate.getForObject(REST_SERVICE_URI+"/customer/name=Himanshu", Customer.class);       				
		
        assertEquals("Himanshu",custFound.getName());
	}
	
	@Test
	public void getCustomerByTelephoneTest() {					
		logger.info("Testing getCustomerByPhone API----------");
        RestTemplate restTemplate = new RestTemplate();                
        Customer custFound = restTemplate.getForObject(REST_SERVICE_URI+"/customer/telephone=0225216620", Customer.class);						
		
        assertEquals("0225216620",custFound.getTelephone());		
	}
	
	// PUT 
	@Test
    public void updateCustomer() {
    	logger.info("Testing update Customer API----------");
        RestTemplate restTemplate = new RestTemplate();      
        
        Customer custFound = restTemplate.getForObject(REST_SERVICE_URI+"/customer/name=Himanshu", Customer.class);  
        
        if(custFound != null) {
        	custFound.getAddress().setStreet("1100 Parnell Road");
        	custFound.getAddress().setCity("AKL CBD");
        	custFound.getAddress().setState("AKL");
        	custFound.getAddress().setCountry("NZ");
        }
        
        restTemplate.put(REST_SERVICE_URI+"/customer/update/name=Himanshu", custFound);  
        
    }
    
    
	@Test
	public void deleteCustomerByNameTest() {	
		logger.info("Testing delete CustomerByName API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/customer/delete/name=Sam");        
	}
	
	@Test
	public void deleteCustomerByIdTest() {	
		logger.info("Testing delete CustomerById API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/customer/delete/custId=5");			
	}

}
