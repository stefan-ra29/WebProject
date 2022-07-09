package service;

import beans.Customer;
import beans.Manager;
import beans.User;
import com.google.gson.Gson;
import comparators.FirstNameComparator;
import comparators.LastNameComparator;
import comparators.UsernameComparator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import repository.AdministratorRepository;
import repository.CoachRepository;
import repository.CustomerRepository;
import repository.ManagerRepository;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserService {

    private CustomerRepository customerRepository = new CustomerRepository();
    private AdministratorRepository administratorRepository = new AdministratorRepository();
    private ManagerRepository managerRepository = new ManagerRepository();
    private CoachRepository coachRepository = new CoachRepository();
    private static Gson gson = new Gson();

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String login(String username, String password){
        String jws = null;
        ArrayList<String> response = new ArrayList<String>();
        User user = this.FindbyId(username);

        if(user == null || !user.getPassword().equals(password)){
            jws = "0";
            response.add(jws);
        }
        else{
            jws = Jwts.builder().setSubject(username)
                    .setExpiration(new Date(new Date().getTime() + 600000 * 10L)).setIssuedAt(new Date()).signWith(key).compact();
            response.add(jws);
            response.add(user.getRole().toString());
        }

        return gson.toJson(response);
    }

    public User FindbyId(String username){
        User retVal;
        retVal = administratorRepository.getOne(username);
        if (retVal != null) {
            return retVal;
        }
        retVal = managerRepository.getOne(username);
        if (retVal != null) {
            return retVal;
        }
        retVal = coachRepository.getOne(username);
        if (retVal != null) {
            return retVal;
        }
        retVal = customerRepository.getOne(username);
        return retVal;
    }

    public String getUserFromJWT(String token, String isUserManager) {
        String username = getUsernameFromJWT(token);
        if(isUserManager.equals("true")) {
            Manager manager = managerRepository.getOne(username);
            String managerGson = gson.toJson(manager);
            return managerGson;
        }
        else{
            User user = FindbyId(username);
            String userGson = gson.toJson(user);
            return userGson;
        }
    }

    public static String getUsernameFromJWT(String token) {
        if (token == null)
            return "";
        String jwt = token;
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
        return claims.getBody().getSubject();
    }
    public void Update(User user) {
        switch (user.getRole()) {
            case  Administrator:
                administratorRepository.update(user.getUsername(), user);
                break;
            case Customer:
                customerRepository.update(user.getUsername(), (Customer) user);
                break;
            case Manager:
                managerRepository.update(user.getUsername(), (Manager) user);
            default:
                break;
        }
    }
    public String getCoachesAndManagers() {
        ArrayList<User> users = new ArrayList<User>();

        users = (ArrayList<User>) (List<? extends User>)coachRepository.getAll();
        users.addAll((ArrayList<User>) (List<? extends User>)managerRepository.getAll());

        return gson.toJson(users);
    }
    public String searchUsers(ArrayList<User> users, String firstNameSearch, String lastNameSearch, String usernameSearch) {

        ArrayList<User> filteredList = new ArrayList<User>();
        ArrayList<User> listForRemoving = new ArrayList<User>();

        if (!firstNameSearch.equals("")) {
            for (User user : users) {
                if (user.getFirstName().toLowerCase().contains(firstNameSearch.toLowerCase().trim()))
                    filteredList.add(user);
            }
            //ako nikog nije ubacio za name, znaci ne postuje niko taj kriterijum, vrati praznu listu
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }

        //ako ne postoji firstname, a postoji last
        if (firstNameSearch.equals("") && !lastNameSearch.equals("")) {
            for (User user : users) {
                if (user.getLastName().toLowerCase().contains(lastNameSearch.toLowerCase().trim()))
                    filteredList.add(user);
            }
            //ako nikog nije ubacio za lastName, znaci ne postuje niko taj kriterijum, vrati praznu listu
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }
        //ako firstname postoji i postoji i lastName
        else if(!firstNameSearch.equals("") && !lastNameSearch.equals("")){
            for (User user : users) {
                if (!user.getLastName().toLowerCase().contains(lastNameSearch.toLowerCase().trim()))
                    listForRemoving.add(user);
            }
            filteredList.removeAll(listForRemoving);
            listForRemoving.clear();
            //ako je lista sada prazna, znaci da lastName nije ispostovan iako je firstname bio i vrati prazno
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }

        //ako ne postoji first i lastname, a postoji username
        if (firstNameSearch.equals("") && lastNameSearch.equals("") && !usernameSearch.equals("")) {
            for (User user : users) {
                if (user.getUsername().toLowerCase().contains(usernameSearch.toLowerCase().trim()))
                    filteredList.add(user);
            }
            //ako nikog nije ubacio za locatoin, znaci ne postuje niko taj kriterijum, vrati praznu listu
            if(filteredList.size() == 0)
                return gson.toJson(filteredList);
        }
        //ako firstName ILI lastname postoji i postoji i username
        else if((!firstNameSearch.equals("") || !lastNameSearch.equals("")) && !usernameSearch.equals("")) {
            for (User user : users) {
                if (!user.getUsername().toLowerCase().contains(usernameSearch.toLowerCase().trim()))
                    listForRemoving.add(user);
            }
            filteredList.removeAll(listForRemoving);
            listForRemoving.clear();
            //svakako prodje i posalje praznu listu
        }
        return gson.toJson(filteredList);
    }
    public String sortUsers(ArrayList<User> usersList, String sortBy ){

        switch (sortBy) {
            case "firstName_increasing":
                Collections.sort(usersList, new FirstNameComparator());
                break;
            case "firstName_decreasing":
                Collections.sort(usersList, new FirstNameComparator().reversed());
                break;
            case "lastName_increasing":
                Collections.sort(usersList, new LastNameComparator());
                break;
            case "lastName_decreasing":
                Collections.sort(usersList, new LastNameComparator().reversed());
                break;
            case "username_increasing":
                Collections.sort(usersList, new UsernameComparator());
                break;
            case "username_decreasing":
                Collections.sort(usersList, new UsernameComparator().reversed());
                break;
        }
        return gson.toJson(usersList);
    }
}
