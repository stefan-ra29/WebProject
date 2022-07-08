package service;

import beans.Customer;
import beans.Role;
import com.google.gson.Gson;
import dto.CustomerDTO;
import repository.AdministratorRepository;
import repository.CustomerRepository;

import java.util.ArrayList;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepository();
    private AdministratorRepository administratorRepository = new AdministratorRepository();
    private static Gson gson = new Gson();
    public String getAll(){
        return gson.toJson(customerRepository.getAll());
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
        customer.setEmail(customerDTO.getEmail());

        return customerRepository.addOne(customer);
    }

    public void logWorkoutToCustomer(String customerId, String facilityId){
        Customer customer = customerRepository.getOne(customerId);

        boolean flag = false;
        if(customer.getVisitedFacilitiesIds() != null)
        {
            for(String facilityID : customer.getVisitedFacilitiesIds()){
                if(facilityID.equals(facilityId)){
                    flag = true;
                    break;
                }
            }
            if(flag == false) {
                ArrayList<String> newVisitedFacilitiesIds = customer.getVisitedFacilitiesIds();
                newVisitedFacilitiesIds.add(facilityId);
                customer.setVisitedFacilities(newVisitedFacilitiesIds);
            }

            customerRepository.update(customerId, customer);
        }
        else
        {
            ArrayList<String> newVisitedFacilitiesIds = new ArrayList<String>();
            newVisitedFacilitiesIds.add(facilityId);
            customer.setVisitedFacilities(newVisitedFacilitiesIds);
            customerRepository.update(customerId, customer);
        }

    }
}
