package service;

import beans.Workout;
import beans.WorkoutType;
import com.google.gson.Gson;
import repository.WorkoutRepository;

import java.util.ArrayList;
import java.util.List;

public class WorkoutService {
    WorkoutRepository workoutRepository = new WorkoutRepository();
    Gson gson = new Gson();
    public String GetWorkoutTypes() {

       List<WorkoutType> allTypes = new ArrayList<>();

        allTypes.add(new WorkoutType("Grupni trening"));
        allTypes.add(new WorkoutType("Personalni trening"));
        allTypes.add(new WorkoutType("Kardio trening"));
        allTypes.add(new WorkoutType("Trening za jacanje misica"));
        allTypes.add(new WorkoutType("Trening fleksibilnosti"));
        return gson.toJson(allTypes);
    }
    public boolean CreateWorkout(Workout workout){
        if(!IsWorkoutNameUnique(workout.getName()))
            return false;
        workout.setId();
        workoutRepository.addOne(workout);
        return true;
    }
    public boolean IsWorkoutNameUnique(String name){

        for(Workout w : workoutRepository.getAll()){
            if(w.getName().equals(name))
                return false;
        }
        return true;
    }
}
