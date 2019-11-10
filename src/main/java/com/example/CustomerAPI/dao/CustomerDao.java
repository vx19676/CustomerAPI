package com.example.CustomerAPI.dao;

import com.example.CustomerAPI.model.Customer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    int insertCustomer(Customer customer);

    Collection<Customer> getAllCustmers();

    int deleteCustomer(String id);

    Optional<Customer> getCustomerById(String id);

    int updateCustomerById(String id, Customer newCustomer);

    Optional<List<Customer>>getCustomerByName(String name);

    Optional<List<Customer>>getCustomerByCity(String city);

    Optional<List<Customer>>getCustomerByAgeRange(int fromAge, int toAge);
}
