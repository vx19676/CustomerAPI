package com.example.CustomerAPI.dao;

import com.example.CustomerAPI.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository("customerDao")
public class CustomerDataAccessService implements CustomerDao{

    private static HashMap<String, Customer> DB= new HashMap<String, Customer>();

    @Override
    public int insertCustomer(Customer customer) {

        //check if already exists - if exists return 0;
        if(DB.containsKey(customer.getDriverLicense()))
            return 0;

        //if not exists, add a new customer and return 1
        DB.put(customer.getDriverLicense(), customer);
        return  1;
    }

    @Override
    public Collection<Customer> getAllCustmers() {
        return DB.values();
    }

    @Override
    public int deleteCustomer(String id) {
        //customer is not found
        if(!DB.containsKey(id))
            return 0;

        DB.remove(id);
        return 1;
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {
        //customer not found
        if(DB.get(id) == null)
            return null;

        return Optional.of(DB.get(id));

    }

    @Override
    public int updateCustomerById(String id, Customer newCustomer) {
        if(DB.get(id) == null)
            return 0;

        //this initialisation is for case that newCustomer driver license is not the same with id
        //we can't update customer id(driver license)
        Customer custometForUpdate= new Customer(id,
                newCustomer.getName(),
                newCustomer.getAddress(),
                newCustomer.getCity(),
                newCustomer.getCreaditCardNumber(),
                newCustomer.getPhoneNumber(),
                newCustomer.getDateOfBith());

        DB.put(id, custometForUpdate);
        return  1;
    }

    @Override
    public Optional<List<Customer>> getCustomerByName(String name) {

        if(name.isEmpty() || name.isEmpty())
            return Optional.empty();

        //this is the simple search implementation
        //return  Optional.of(DB.values().stream().filter(c -> c.getName().contains(name)).collect(Collectors.toList()));

        //here is the full text search implementation - look for all name parts
        return  Optional.of(DB.values().stream().filter(c -> {
            String[] parts = name.toLowerCase().split(" ");
            for(String part : parts){
                if(c.getName().toLowerCase().contains(part))
                    return  true;
            }
            return  false;
        }
       ).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Customer>> getCustomerByCity(String city) {

        if(city.isEmpty() || city.isEmpty())
            return Optional.empty();

        return  Optional.of(DB.values().stream().filter(c -> c.getCity().equals(city)).collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Customer>> getCustomerByAgeRange(int fromAge, int toAge) {
        return Optional.of(DB.values().stream().filter(c -> {
            int age = c.getAge();
            if(age >= fromAge && age <= toAge)
                return true;
            return  false;
        }).collect(Collectors.toList()));
    }


}
