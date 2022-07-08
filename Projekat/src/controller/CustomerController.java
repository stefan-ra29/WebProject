package controller;

import com.google.gson.Gson;
import dto.CustomerDTO;
import service.CustomerService;

import static spark.Spark.get;
import static spark.Spark.post;

public class CustomerController {
    private static Gson g = new Gson();
    private static CustomerService customerService = new CustomerService();

    public static void registerCustomer(){
        post("rest/customers/register/", (req, res) -> {
            res.type("application/json");
            //Gson greg = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
            CustomerDTO customerDTO = g.fromJson(req.body(), CustomerDTO.class);
            return customerService.registerCustomer(customerDTO);
        });
    }

    public static void getAll(){
        get("rest/customers/get_all", (req, res) -> {
            res.type("application/json");

            return customerService.getAll();
        });
    }
}
