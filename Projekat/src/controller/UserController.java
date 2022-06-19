package controller;

import com.google.gson.Gson;
import service.UserService;

import static spark.Spark.post;

public class UserController {
    private static Gson g = new Gson();
    private static UserService userService = new UserService();

    public static void login(){
        post("rest/users/login", (req, res) -> {
            res.type("application/json");
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            return userService.login(username, password);
        });
    }

    public static void testjwt(){
        post("rest/testjwt", (req, res) -> {
            res.type("application/json");
            String jwt = req.queryParams("jwt");
            return userService.getUsernameFromJWT(jwt);
        });
    }
}
