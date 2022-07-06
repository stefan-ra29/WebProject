package controller;

import com.google.gson.Gson;
import dto.CustomerDTO;
import service.ManagerService;

import static spark.Spark.post;

public class ManagerController {
    private static Gson g = new Gson();
    private static ManagerService  managerService = new ManagerService();

    public static void registerManager(){
        post("rest/managers/register/", (req, res) -> {
            res.type("application/json");

            CustomerDTO customerDTO = g.fromJson(req.body(), CustomerDTO.class);
            return managerService.registerManager(customerDTO);
        });
    }
}
