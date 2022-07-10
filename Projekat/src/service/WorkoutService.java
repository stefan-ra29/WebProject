package service;

import beans.Coach;
import beans.ScheduledPersonalWorkout;
import beans.Workout;
import beans.WorkoutType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import comparators.PriceComparator;
import repository.CoachRepository;
import repository.ScheduledPersonalWorkoutRepository;
import repository.WorkoutRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutService {
    WorkoutRepository workoutRepository = new WorkoutRepository();
    CoachRepository coachRepository = new CoachRepository();
    ScheduledPersonalWorkoutRepository scheduledPersonalWorkoutRepository = new ScheduledPersonalWorkoutRepository();
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
        if(!isWorkoutNameUnique(workout) || !validateWorkoutInput(workout))
            return false;

        workout.setId();
        workoutRepository.addOne(workout);
        return true;
    }
    public boolean isWorkoutNameUnique(Workout workout){

        for(Workout w : workoutRepository.getAll()){
            if(w.getName().equals(workout.getName()) && !w.getId().equals(workout.getId()))
                return false;
        }
        return true;
    }
    public boolean validateWorkoutInput(Workout workout){

        if(workout.getName().trim() == "" || workout.getPicture().trim() == "" || (workout.getDuration() < 0 || workout.getDuration() >120)
            || !doesWorkoutTypeExist(workout.getWorkoutType().getType()) ||
                (workout.getSupplement() < 0 || workout.getSupplement() > 2000) || !doesCoachExist(workout.getCoachID()))
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
        if(coachID.equals("") || coachID == null)
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

    public String getCoachesNamesFromWorkoutList(ArrayList<Workout> workouts){
        ArrayList<String> coaches = new ArrayList<>();

        for(Workout w : workouts){
            if(w.getCoachID().equals("") || w.getCoachID() == null)
                coaches.add("");
            else {
                Coach coach = coachRepository.getOne(w.getCoachID());
                coaches.add(coach.getFirstName() + " " + coach.getLastName());
            }
        }
        return gson.toJson(coaches);
    }

    public String getWorkoutByID(String id){
       return gson.toJson(workoutRepository.getOne(id));
    }
    public Boolean changeWorkout(Workout workout){

        if(!isWorkoutNameUnique(workout) || !validateWorkoutInput(workout))
            return false;

        workoutRepository.update(workout.getId(), workout);
        return true;
    }

    public String searchWorkouts(ArrayList<Workout> workouts, String criteria, String minPrice, String maxPrice){

        ArrayList<Workout> filteredWorkouts = new ArrayList<Workout>();

        for( Workout w : workouts){
            switch (criteria) {
                case "withoutSupplement":
                    if(w.getSupplement() == 0)
                        filteredWorkouts.add(w);
                    break;
                case "withSupplement":
                    if(Integer.valueOf(minPrice) > Integer.valueOf(maxPrice)){
                        return gson.toJson(filteredWorkouts);
                    }
                    if(w.getSupplement() >= Integer.valueOf(minPrice) && w.getSupplement() <= Integer.valueOf(maxPrice) )
                        filteredWorkouts.add(w);
                    break;
            }
        }
        return gson.toJson(filteredWorkouts);
    }
    public String sortWorkouts(String sortBy, ArrayList<Workout> workouts){

        switch (sortBy) {
            case "price_increasing":
                Collections.sort(workouts, new PriceComparator());
                break;

            case "price_decreasing":
                Collections.sort(workouts, new PriceComparator().reversed());
                break;
        }
        return gson.toJson(workouts);
    }
    public String filterWorkouts(String filterBy, ArrayList<Workout> workouts){

        ArrayList<Workout> filteredWorkouts = new ArrayList<Workout>();

        for(Workout w : workouts){
            if(w.getWorkoutType().getType().equals(filterBy))
                filteredWorkouts.add(w);
        }
        return gson.toJson(filteredWorkouts);
    }

    public void scheduleWorkout(String customerId, String workoutId, LocalDateTime scheduledTime){

        Workout workout = workoutRepository.getOne(workoutId);

        ScheduledPersonalWorkout scheduledWorkout = new ScheduledPersonalWorkout(workoutId, customerId, workout.getCoachID(), LocalDateTime.now(), scheduledTime);

        scheduledPersonalWorkoutRepository.addOne(scheduledWorkout);
    }
}
