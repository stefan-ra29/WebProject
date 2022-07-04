package service;

import beans.Manager;
import com.google.gson.Gson;
import repository.ManagerRepository;

import java.util.ArrayList;

public class ManagerService {
    private ManagerRepository managerRepository = new ManagerRepository();
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
}
