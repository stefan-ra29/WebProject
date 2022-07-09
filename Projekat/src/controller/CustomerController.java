package controller;

import beans.Customer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.CustomerDTO;
import service.CustomerService;

import java.util.ArrayList;

import static spark.Spark.get;
import static spark.Spark.post;

public class CustomerController {
    private static Gson gson = new Gson();
    private static CustomerService customerService = new CustomerService();

    public static void registerCustomer(){
        post("rest/customers/register/", (req, res) -> {
            res.type("application/json");
            //Gson greg = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            CustomerDTO customerDTO = gson.fromJson(req.body(), CustomerDTO.class);
            return customerService.registerCustomer(customerDTO);
        });
    }

    public static void getAll(){
        get("rest/customers/get_all", (req, res) -> {
            res.type("application/json");

            return customerService.getAll();
        });
    }
    public static void sortCustomers(){
        post("rest/customers/sort", (req, res) ->{

            ArrayList<Customer> users = gson.fromJson(req.body(), new TypeToken<ArrayList<Customer>>(){}.getType());
            String sortBy = req.queryParams("sortBy");
            return customerService.sortUsers(users, sortBy);
        });
    }
    public static void filterCustomers(){
        post("rest/customers/filter", (req, res) ->{

            ArrayList<Customer> users = gson.fromJson(req.body(), new TypeToken<ArrayList<Customer>>(){}.getType());
            String filter = req.queryParams("filterBy");
            return customerService.filterCustomers(users, filter);
        });
    }
}
