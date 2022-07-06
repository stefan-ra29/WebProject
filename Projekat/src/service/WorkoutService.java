package service;

import beans.Coach;
import beans.Workout;
import beans.WorkoutType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import repository.CoachRepository;
import repository.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;

public class WorkoutService {
    WorkoutRepository workoutRepository = new WorkoutRepository();
    CoachRepository coachRepository = new CoachRepository();
    Gson gson = new Gson();
    public String getWorkoutTypes() {

       List<WorkoutType> allTypes = new ArrayList<>();

        allTypes.add(new WorkoutType("Grupni trening"));
        allTypes.add(new WorkoutType("Personalni trening"));
        allTypes.add(new WorkoutType("Kardio trening"));
        allTypes.add(new WorkoutType("Trening za jacanje misica"));
        allTypes.add(new WorkoutType("Trening fleksibilnosti"));
        return gson.toJson(allTypes);
    }
    public boolean createWorkout(Workout workout){
        if(!isWorkoutNameUnique(workout.getName()) || !validateWorkoutInput(workout))
            return false;

        workout.setId();
        workoutRepository.addOne(workout);
        return true;
    }
    public boolean isWorkoutNameUnique(String name){

        for(Workout w : workoutRepository.getAll()){
            if(w.getName().equals(name))
                return false;
        }
        return true;
    }
    public boolean validateWorkoutInput(Workout workout){

        if(workout.getName().trim() == "" || workout.getPicture().trim() == "" || (workout.getDuration() < 0 && workout.getDuration() >120)
            || !doesWorkoutTypeExist(workout.getWorkoutType().getType()) ||
                (workout.getSupplement() != 0 && workout.getSupplement() != 500 && workout.getSupplement() != 1000 &&
                        workout.getSupplement() != 1500 && workout.getSupplement() != 2000) || !doesCoachExist(workout.getCoachID()))
            return false;
        return true;
    }
    public boolean doesWorkoutTypeExist(String type){
        ArrayList<WorkoutType> workoutTypes = gson.fromJson(getWorkoutTypes(), new TypeToken<ArrayList<WorkoutType>>(){}.getType());
        Boolean exists = false;
        for(WorkoutType w : workoutTypes) {
            if (w.getType().equals(type)) {
                exists = true;
                return exists;
            }
        }
        return exists;
    }
    public boolean doesCoachExist(String coachID){
        if(coachID == "" || coachID == null)
            return true;
        else{
            for(Coach c : coachRepository.getAll()){
                if(c.getUsername().equals(coachID))
                    return true;
            }
        }
        return false;
    }
    public String getWorkoutsByFacility(String facilityID){
        ArrayList<Workout> facilityWorkouts = new ArrayList<>();

        for(Workout w : workoutRepository.getAll()){
            if(w.getSportFacilityID().equals(facilityID))
                facilityWorkouts.add(w);
        }
        return gson.toJson(facilityWorkouts);
    }
}
