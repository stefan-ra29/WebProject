package service;

import beans.Customer;
import dto.CustomerDTO;
import repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepository();

    public List<Customer> getAll(){
        return customerRepository.getAll();
    }

    public boolean registerCustomer(CustomerDTO customerDTO){

        Customer customer = new Customer();
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setDob(customerDTO.getDob());
        customer.setGender(customerDTO.getGender());

        return customerRepository.addOne(customer);
    }
}
