package service;

import beans.Manager;
import beans.Role;
import com.google.gson.Gson;
import dto.CustomerDTO;
import repository.ManagerRepository;

import java.util.ArrayList;

public class ManagerService {
    private ManagerRepository managerRepository = new ManagerRepository();
    private static UserService userService = new UserService();
    Gson gson = new Gson();

    public String getAvailableManagersUsernames(){
        ArrayList<String> usernames = new ArrayList<String>();
        for(Manager manager : managerRepository.getAll())
        {
            if(manager.getSportFacilityId() == null)
                usernames.add(manager.getUsername());
        }

        return gson.toJson(usernames);
    }

    public boolean registerManager(CustomerDTO customerDTO){

        Manager manager = new Manager();
        manager.setUsername(customerDTO.getUsername());
        manager.setPassword(customerDTO.getPassword());
        manager.setFirstName(customerDTO.getFirstName());
        manager.setLastName(customerDTO.getLastName());
        manager.setDob(customerDTO.getDob());
        manager.setRole(Role.Manager);
        manager.setGender(customerDTO.getGender());
        manager.setEmail(customerDTO.getEmail());

        return managerRepository.addOne(manager);
    }

    public void setFacilityToManager(String managerUsername, String facilityId) {
        Manager manager = managerRepository.getOne(managerUsername);
        manager.setSportFacilityId(facilityId);
        managerRepository.update(managerUsername, manager);
    }
}
