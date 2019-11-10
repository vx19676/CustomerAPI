package com.example.CustomerAPI.service;

import com.example.CustomerAPI.dao.CustomerDao;
import com.example.CustomerAPI.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerService(@Qualifier("customerDao") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public int addCustomer(Customer customer){
        return  customerDao.insertCustomer(customer);
    }

    public Collection<Customer> getAllCustomers(){
        return customerDao.getAllCustmers();
    }

    public int deleteCustomer(String id){
        return customerDao.deleteCustomer(id);
    }

    public Optional<Customer> getCustomerById(String id){
        return customerDao.getCustomerById(id);
    }

    public int updateCustomerById(String id, Customer newCustomer){
        return customerDao.updateCustomerById(id, newCustomer);
    }

    public int updateCustomerByName(String id, Customer newCustomer){
        return customerDao.updateCustomerById(id, newCustomer);
    }

    public Optional<List<Customer>> getCustomerByName(String name){
        return customerDao.getCustomerByName(name);
    }

    public Optional<List<Customer>> getCustomerByCity(String city){
        return customerDao.getCustomerByCity(city);
    }

    public Optional<List<Customer>> getCustomerByAgeRange(int fromAge, int toAge){
        return customerDao.getCustomerByAgeRange(fromAge, toAge);
    }


}
