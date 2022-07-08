package controller;

import beans.User;
import com.google.gson.Gson;
import service.UserService;

import static spark.Spark.get;
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

    public static void getLoggedUser(){
        get("rest/users/getLoggedUser", (req, res) ->{
            res.type("application/json");

            String jwt = req.queryParams("jwt");
            String isUserManager = req.queryParams("isUserManager");

            return userService.getUserFromJWT(jwt, isUserManager);
        });
    }

    public static void updateUserInfo(){
        post("rest/users/update/", (req, res) ->{

            User changedUser = g.fromJson(req.body(), User.class);

            String username = changedUser.getUsername();
            User user = userService.FindbyId(username);

            user.setFirstName(changedUser.getFirstName());
            user.setLastName(changedUser.getLastName());
            user.setEmail(changedUser.getEmail());
            user.setPassword(changedUser.getPassword());
            user.setGender(changedUser.getGender());
            user.setDob(changedUser.getDob());

            userService.Update(user);

            return true;
        });
    }

    public static void getCoachesAndManagers(){
        get("rest/users/get_coaches_and_managers", (req, res) ->{

            return userService.getCoachesAndManagers();
        });
    }
}
