package controller;

import beans.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import service.UserService;

import java.util.ArrayList;

import static spark.Spark.*;

public class UserController {
    private static Gson gson = new Gson();
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
        put("rest/users/update/", (req, res) ->{

            User changedUser = gson.fromJson(req.body(), User.class);

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
    public static void searchUsers(){
        post("rest/users/search", (req, res) ->{

            ArrayList<User> users = gson.fromJson(req.body(), new TypeToken<ArrayList<User>>(){}.getType());
            String firstNameSearch = req.queryParams("firstNameSearch");
            String lastNameSearch = req.queryParams("lastNameSearch");
            String usernameSearch = req.queryParams("usernameSearch");

            return userService.searchUsers(users, firstNameSearch, lastNameSearch, usernameSearch);
        });
    }
    public static void sortUsers(){
        post("rest/users/sort", (req, res) ->{

            ArrayList<User> users = gson.fromJson(req.body(), new TypeToken<ArrayList<User>>(){}.getType());
            String sortBy = req.queryParams("sortBy");
            return userService.sortUsers(users, sortBy);
        });
    }
}
