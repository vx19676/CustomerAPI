package com.example.CustomerAPI.api;

import com.example.CustomerAPI.model.Customer;
import com.example.CustomerAPI.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> addCustomer(@Valid @NotNull @RequestBody Customer customer){
        if(customerService.addCustomer(customer) == 0)
            return new ResponseEntity("Customer with the same driver license number is already exists!", HttpStatus.BAD_REQUEST);

        return  new ResponseEntity("Customer had bean successfully added!", HttpStatus.OK);
    }

    @GetMapping
    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @DeleteMapping(path="{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id) {

        if (customerService.deleteCustomer(id) == 0)
            return new ResponseEntity("Customer does not not exists", HttpStatus.BAD_REQUEST);

        return  new ResponseEntity("Customer had bean successfully deleted!", HttpStatus.OK);
    }

    @GetMapping(path="{id}")
    public ResponseEntity<Customer> getCustomerById(@Valid @PathVariable("id") String id){

        Optional<Customer> customer = customerService.getCustomerById(id);
        if(customer == null)
            return new ResponseEntity("Customer does not not exists", HttpStatus.BAD_REQUEST);

        return new ResponseEntity(customer.get(), HttpStatus.OK);
    }

    @PutMapping(path="{id}")
    public ResponseEntity<String> updateCustomerById(@Valid @PathVariable("id") String id, @Valid @NotNull @RequestBody Customer newCustomer){

        if(customerService.updateCustomerById(id, newCustomer) == 0)
            return  new ResponseEntity("Customer does not not exists", HttpStatus.BAD_REQUEST);

        return new ResponseEntity("Customer had bean successfully updatetd!", HttpStatus.OK);
    }

    @GetMapping(path="/names/{name}")
    public Optional<List<Customer>> getCustomerByName(@Valid @PathVariable("name") String name){
       return  customerService.getCustomerByName(name);
    }

    @GetMapping(path="/cities/{city}")
    public Optional<List<Customer>> getCustomerByCity(@PathVariable("city") String city){
        return  customerService.getCustomerByCity(city);
    }

    @GetMapping(path="/age")
    public ResponseEntity<List<Customer>> getCustomerByAge(@RequestParam("from") int from, @RequestParam("to") int to ){
        if(from > to || from < 0 ||to < 0)
            return  new ResponseEntity("Dates range is wrong.", HttpStatus.BAD_REQUEST);

       return  new ResponseEntity(customerService.getCustomerByAgeRange(from, to).get(), HttpStatus.OK);
    }


}
