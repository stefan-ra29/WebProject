package service;

import beans.Coach;
import beans.Role;
import com.google.gson.Gson;
import dto.CustomerDTO;
import repository.CoachRepository;

import java.util.ArrayList;

public class CoachService {
    private CoachRepository coachRepository = new CoachRepository();
    Gson gson = new Gson();

    public boolean registerCoach(CustomerDTO customerDTO){
        Coach coach = new Coach();
        coach.setUsername(customerDTO.getUsername());
        coach.setPassword(customerDTO.getPassword());
        coach.setFirstName(customerDTO.getFirstName());
        coach.setLastName(customerDTO.getLastName());
        coach.setDob(customerDTO.getDob());
        coach.setRole(Role.Coach);
        coach.setGender(customerDTO.getGender());
        coach.setEmail(customerDTO.getEmail());

        return coachRepository.addOne(coach);
    }
    public String getAll(){

        return gson.toJson(coachRepository.getAll());
    }
    public String getCoach(String id){

        return gson.toJson(coachRepository.getOne(id));
    }

    public void addWorkoutToCoachesWorkoutHistory(String coachId, String wourkoutHistoryId){
        Coach coach = coachRepository.getOne(coachId);

        if(coach.getWorkoutHistory() != null)
        {
            ArrayList<String> newWorkoutHistory = coach.getWorkoutHistory();
            newWorkoutHistory.add(wourkoutHistoryId);
            coach.setWorkoutHistory(newWorkoutHistory);
        }
        else
        {
            ArrayList<String> newWorkoutHistory = new ArrayList<String>();
            newWorkoutHistory.add(wourkoutHistoryId);
            coach.setWorkoutHistory(newWorkoutHistory);
        }

        coachRepository.update(coachId, coach);
    }
}
