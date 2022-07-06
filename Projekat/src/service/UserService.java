package service;

import beans.Customer;
import beans.Manager;
import beans.User;
import com.google.gson.Gson;
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
import java.util.Date;

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

}
