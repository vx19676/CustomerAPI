package com.example.CustomerAPI;

import com.example.CustomerAPI.api.CustomerController;
import com.example.CustomerAPI.dao.CustomerDao;
import com.example.CustomerAPI.dao.CustomerDataAccessService;
import com.example.CustomerAPI.model.Customer;
import com.example.CustomerAPI.service.CustomerService;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerApiApplicationTests {

	CustomerDao customerDao = new CustomerDataAccessService();
	CustomerService customerService = new CustomerService(customerDao);
	CustomerController controller = new CustomerController(customerService);

	@Test
	@Order(1)
	public void testAddCustomer() {

		//customers to add
		Customer customer1 = new Customer("111", "Beny Shalom", "Anesi 4", "Tel Aviv",
				"55675775", "345345345",  LocalDate.of(2000, 1,15));

		//check that there are not customers before addition
		Collection<Customer> customers = controller.getAllCustomers();
		Assertions.assertEquals(customers.size() ,0);

		//check after addition
		ResponseEntity<String> resp = controller.addCustomer(customer1);

		Assertions.assertNotEquals(resp, null);
		Assertions.assertEquals(resp.getStatusCode(), HttpStatus.OK);

		Assertions.assertEquals(controller.getAllCustomers().size() ,1);
	}

	@Test
	@Order(2)
	public void testGetCustomerById() {

		Customer customer2 = new Customer("222", "Avi Kohen", "Arlozorov 5", "Tel Aviv",
				"786868", "678678678",  LocalDate.of(2000, 3,10));

		controller.addCustomer(customer2);

		//check customer that not exists
		ResponseEntity<Customer> resp = controller.getCustomerById("999");

		Assertions.assertEquals(resp.getStatusCode(), HttpStatus.BAD_REQUEST);

		//check an existing customer
		resp = controller.getCustomerById("222");
		Assertions.assertNotEquals(resp, null);

		Customer addedCustomer = resp.getBody();
		Assertions.assertNotEquals(addedCustomer, null);
	}

	@Test
	@Order(3)
	public void testUpdateCustomerById(){

		Customer customerToChange = new Customer("333", "Karin Peles", "Hazmaut 7", "Haifa",
	                                			"12356789", "0540000000",  LocalDate.of(2000, 12,2));

		controller.addCustomer(customerToChange);

		Customer newCustomer = new Customer("222", "New Name", "New Address", "New City",
				"New Credit CardNumber", "New Phone",  LocalDate.of(2000, 1,15));

		ResponseEntity<String> resp = controller.updateCustomerById(customerToChange.getDriverLicense(), newCustomer);

		//test that the return customer is the customer that had bean added first
		Customer newCustomerResp = controller.getCustomerById(customerToChange.getDriverLicense()).getBody();
		Assertions.assertEquals(newCustomer.getCity(), newCustomerResp.getCity());
		Assertions.assertEquals(newCustomer.getDateOfBith(), newCustomerResp.getDateOfBith());
		Assertions.assertEquals(newCustomer.getName(), newCustomerResp.getName());
		Assertions.assertEquals(newCustomer.getAddress(), newCustomerResp.getAddress());
		Assertions.assertEquals(newCustomer.getCreaditCardNumber(), newCustomerResp.getCreaditCardNumber());
		Assertions.assertEquals(newCustomer.getPhoneNumber(), newCustomerResp.getPhoneNumber());

		//check that Driver License had not bean changed
		Assertions.assertEquals(newCustomerResp.getDriverLicense(), customerToChange.getDriverLicense());
		//Assertions.assertEquals(resp.getStatusCode(), HttpStatus.OK);
	}

	@Order(4)
	@Test void testUpdateNotExistedCustomerById(){

		Customer customerToChange = new Customer("444", "Baruh Bachrov", "Oren 12", "Harish",
		                            			"12356789", "0540000000",  LocalDate.of(2000, 1,15));

		controller.addCustomer(customerToChange);
		//update the customer that is not exists
		ResponseEntity<String> resp = controller.updateCustomerById("121434", customerToChange);
		Assertions.assertEquals(resp.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Order(5)
	@Test public void testGetCustomerByName(){

		Customer customer = new Customer("666", "Yaron Lapid", "Histadrut", "BeerSheva",
				"12356789", "0540000000",  LocalDate.of(2000, 1,15));

		controller.addCustomer(customer);
		Optional<List<Customer>> customers = controller.getCustomerByName("Yaron Lapid");
		Assertions.assertEquals(customers.get().size(),1);

		//check wrong name
		customers = controller.getCustomerByName("TTT");
	    Assertions.assertEquals(customers.get().size(),0);

		customers = controller.getCustomerByName("");
		Assertions.assertEquals(customers,Optional.empty());
	}

	@Order(6)
	@Test public void testGetCustomerByCity(){

		Customer customer = new Customer("555", "Avraam Shalom", "Anesi 4", "Holon",
				"12356789", "0540000000",  LocalDate.of(2000, 1,15));

		controller.addCustomer(customer);
		Optional<List<Customer>> customers = controller.getCustomerByCity("Holon");
		Assertions.assertEquals(customers.get().size(),1);

		//check wrong name
		customers = controller.getCustomerByCity("New York");
		Assertions.assertEquals(customers.get().size(),0);

		customers = controller.getCustomerByCity("");
		Assertions.assertEquals(customers,Optional.empty());
	}

	@Order(7)
	@Test public void testGetCustomerByAge(){

		Customer firstCustomer = new Customer("888", "Avraam Shalom", "Anesi 4", "Tel Aviv",
				"12356789", "0540000000",  LocalDate.of(2013, 1,15));

		Customer secondCustomer = new Customer("999", "Tzvika Pic", "Habarzel", "Haifa",
				"46345", "234234234",  LocalDate.of(2015, 1,15));

		controller.addCustomer(firstCustomer);
		controller.addCustomer(secondCustomer);

		//check the first customer
		ResponseEntity<List<Customer>> resp = controller.getCustomerByAge(firstCustomer.getAge() -1 ,firstCustomer.getAge()+1);
		List<Customer> customers = resp.getBody();
		Assertions.assertEquals(customers.size(),1);

		controller.addCustomer(secondCustomer);

		//check two customers
		resp = controller.getCustomerByAge(secondCustomer.getAge(), firstCustomer.getAge());
		customers = resp.getBody();
		Assertions.assertEquals(customers.size(),2);
	}

	@Order(8)
	@Test public void testDeleteDeleteCustomer(){

		//delete customer that does not exists
		ResponseEntity<String> resp = controller.deleteCustomer("112");

		Assertions.assertEquals(resp.getStatusCode(), HttpStatus.BAD_REQUEST);

		Customer firstCustomer = new Customer("112", "Avraam Shalom", "Anesi 4", "Tel Aviv",
				"12356789", "0540000000",  LocalDate.of(2013, 1,15));

		controller.addCustomer(firstCustomer);

		//delete an existing customer
		resp = controller.deleteCustomer("112");

		Assertions.assertEquals(resp.getStatusCode(), HttpStatus.OK);

	}


}
