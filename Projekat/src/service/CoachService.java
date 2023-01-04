package service;

import beans.Coach;
import beans.Role;
import beans.Workout;
import com.google.gson.Gson;
import dto.CustomerDTO;
import repository.CoachRepository;
import repository.WorkoutRepository;

import java.util.ArrayList;

public class CoachService {
    private CoachRepository coachRepository = new CoachRepository();
    private WorkoutRepository workoutRepository = new WorkoutRepository();
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
    public String getCoachesFromFacility(String facilityId){
        ArrayList<Coach> coaches = new ArrayList<Coach>();

        for(Workout w : workoutRepository.getAll())
        {
            if(w.getSportFacilityID().equals(facilityId) && !w.getCoachID().equals("")) {
                boolean exists = false;
                if (coaches.size() != 0){
                    for(Coach c: coaches){
                        if(c.getUsername().equals(coachRepository.getOne(w.getCoachID()).getUsername()))
                            exists = true;
                    }
                }
                if(!exists)
                    coaches.add(coachRepository.getOne(w.getCoachID()));
            }
        }

        return gson.toJson(coaches);
    }
}
