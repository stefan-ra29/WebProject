package service;

import beans.Customer;
import beans.Role;
import dto.CustomerDTO;
import repository.AdministratorRepository;
import repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepository();
    private AdministratorRepository administratorRepository = new AdministratorRepository();

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
        customer.setRole(Role.Customer);
        customer.setGender(customerDTO.getGender());

        return customerRepository.addOne(customer);

//        User admin = new User();
//        admin.setUsername("admin");
//        admin.setPassword("123admin456");
//        admin.setRole(Role.Administrator);
//
//        return administratorRepository.addOne(admin);
    }
}
